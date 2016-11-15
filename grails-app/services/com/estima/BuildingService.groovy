package com.estima

import grails.databinding.SimpleMapDataBindingSource
import grails.transaction.Transactional
import grails.util.Environment
import groovy.sql.Sql

@Transactional
class BuildingService {

    def grailsWebDataBinder
    def sessionFactory

    List list(String sort, String order, def max, def offset, String q = null, List authorsIds, Map<String, String> filter = null) {
        List result = Building.createCriteria().list([sort: sort, order: order, max: max, offset: offset], {
            if (q != null) {
                or {
                    ilike('name', "%$q%")
                    ilike('address', "%$q%")
                }
            }

            if (authorsIds) {
                or {
                    authorsIds.each{ id ->
                        eq('author.id', id)
                    }
                }
            }
        })

        return result
    }

    List listByLocation(List latlng, Integer radius) {
        def session = sessionFactory.currentSession
        def query = session.createSQLQuery("select b.* from building b WHERE ST_DWithin(st_geogfromtext(b.location), Geography(ST_MakePoint(:lon, :lat)), :radius)")

        if (Environment.current == Environment.DEVELOPMENT || !latlng || !radius) {
            return Building.list()
        }

        query.addEntity(com.estima.Building)
        query.setInteger("radius", radius)
        query.setDouble("lon", (double) latlng?.getAt(1))
        query.setDouble("lat", (double) latlng?.getAt(0))
        return query.list()
    }

    Building create(String name, String address, String location, Long clientId, Long projectId, Long authorId,
                    String description, String status) {
        Building building = new Building(name: name, address: address, location: location,
                client: clientId != null ? DictionaryItem.load(clientId) : null,
                project: projectId != null ? DictionaryItem.load(projectId) : null,
                author: User.load(authorId), description: description, status: status)

        building.save()

        if (building.hasErrors()) {
            transactionStatus.setRollbackOnly()
            log.debug 'Cannot save Building'
            return building
        }

        return building
    }

    Building update(Long id, String name, String address, String location, Long clientId, Long projectId,
                    String description, String status) {
        Building building = Building.get(id)
        Map properties = [name: name, address: address, location: location,
                          client: clientId != null ? DictionaryItem.load(clientId) : null,
                          project: projectId != null ? DictionaryItem.load(projectId) : null,
                          description: description, status: status]

        if (!building) {
            log.debug "Cannot find Building with id: $id"
            return null
        }

        grailsWebDataBinder.bind building, properties as SimpleMapDataBindingSource

        building.save()

        if (building.hasErrors()) {
            building.discard()
            transactionStatus.setRollbackOnly()
            log.debug "Cannot update Building with id: $id"
            return building
        }

        return building
    }
}