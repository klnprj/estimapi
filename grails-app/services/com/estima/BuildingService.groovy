package com.estima

import grails.transaction.Transactional

@Transactional
class BuildingService {

    List list() {
        List result = Building.list()

        return result
    }

    Building create(String name, String address, String client, String manager, String project) {
        Building building = new Building(name: name, address: address, client: client, manager: manager, project: project)

        building.save()

        if (building.hasErrors()) {
            transactionStatus.setRollbackOnly()
            log.debug 'Cannot save Building'
            return building
        }

        return building
    }
}