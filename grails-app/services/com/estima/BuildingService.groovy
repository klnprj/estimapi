package com.estima

import grails.databinding.SimpleMapDataBindingSource
import grails.transaction.Transactional
import grails.util.Environment
import groovy.sql.Sql
import org.hibernate.criterion.CriteriaSpecification

@Transactional
class BuildingService {

    def grailsWebDataBinder
    def sessionFactory

    List listBuildings(BuildingSearchCriteria criteria, BuildingSearchFilter filter) {

        List result = Building.createCriteria().list([sort: criteria.sort, order: criteria.order, max: criteria.max, offset: criteria.offset, fetch: [positions: 'subselect']], {
            if (filter.q != null) {
                or {
                    ilike('name', "%${filter.q}%")
                    ilike('address', "%${filter.q}%")
                }
            }

            if (filter.authorsIds) {
                or {
                    filter.authorsIds.each{ id ->
                        eq('author.id', id)
                    }
                }
            }

            if (filter.statuses) {
                or {
                    filter.statuses.each{ status ->
                        eq('status', status)
                    }
                }
            }

            if (filter.lastUpdatedFrom) {
                ge('latestPositionDateUpdated', filter.lastUpdatedFrom)
            }

            if (filter.dealersIds) {
                resultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                positions {
                    or {
                        filter.dealersIds.each{ id ->
                            eq('dealer.id', id)
                        }
                    }
                }
            }
        })

        return result
    }

    List listByLocation(BuildingLocationCriteria locationCriteria) {
        def session = sessionFactory.currentSession
        def query = session.createSQLQuery("select b.* from building b WHERE ST_DWithin(st_geogfromtext(b.location), Geography(ST_MakePoint(:lon, :lat)), :radius)")

        if (Environment.current == Environment.DEVELOPMENT) {
            return Building.list()
        }

        query.addEntity(com.estima.Building)
        query.setInteger("radius", locationCriteria.radius)
        query.setDouble("lon", (double) locationCriteria.latLng.lng)
        query.setDouble("lat", (double) locationCriteria.latLng.lat)
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