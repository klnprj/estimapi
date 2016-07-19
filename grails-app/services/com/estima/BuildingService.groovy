package com.estima

import grails.databinding.SimpleMapDataBindingSource
import grails.transaction.Transactional
import groovy.sql.Sql

@Transactional
class BuildingService {

    def grailsWebDataBinder
    def dataSource
    def sessionFactory

    List list(List latlng = null, Integer radius = null) {
        List result
        Sql sql = new Sql(dataSource)
        double lon = latlng?.getAt(1)
        double lat = latlng?.getAt(0)

        if (latlng != null) {
            log.debug("Latlng: [$latlng]")
        }

        def session = sessionFactory.currentSession
        def query = session.createSQLQuery("select b.* from building b WHERE ST_DWithin(st_geogfromtext(b.location), Geography(ST_MakePoint(:lon, :lat)), :radius)")
        query.addEntity(com.estima.Building)
        query.setInteger("radius", radius)
        query.setDouble("lon", lon)
        query.setDouble("lat", lat)
        result = query.list()

        return result
    }

    Building create(String name, String address, String location, String client, String manager, String project) {
        Building building = new Building(name: name, address: address, location: location,
                client: client, manager: manager, project: project)

        building.save()

        if (building.hasErrors()) {
            transactionStatus.setRollbackOnly()
            log.debug 'Cannot save Building'
            return building
        }

        return building
    }

    Building update(Long id, String name, String address, String location, String client, String manager, String project) {
        Building building = Building.get(id)
        Map properties = [name: name, address: address, location: location, client: client, manager: manager, project: project]

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