package com.estima

import grails.rest.RestfulController
import org.springframework.http.HttpStatus


class BuildingController extends RestfulController<Building> {

    def buildingService

    static responseFormats = ['json']

    BuildingController() {
        super(Building)
    }

    def index() {
        def buildings = buildingService.list()

        respond buildings
    }

    def create(String name, String address, String client, String manager, String project) {
        def building = buildingService.create(name, address, client, manager, project)

        if (building.hasErrors()) {
            respond building.errors, [status: HttpStatus.METHOD_NOT_ALLOWED]
            return
        }

        respond building
    }
}
