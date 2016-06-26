package com.estima

import grails.databinding.SimpleMapDataBindingSource
import grails.transaction.Transactional

@Transactional
class BuildingService {

    def grailsWebDataBinder

    List list() {
        List result = Building.list()

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