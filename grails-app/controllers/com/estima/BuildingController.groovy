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

    def create() {
        def p = request.JSON
        def building = buildingService.create(p.name, p.address, p.client, p.manager, p.project)

        if (building.hasErrors()) {
            respond building.errors, [status: HttpStatus.METHOD_NOT_ALLOWED]
            return
        }

        respond building
    }

    def update() {
        def p = request.JSON
        Long id = params.long('id')
        def building = buildingService.update(id, p.name, p.address, p.client, p.manager, p.project)

        if (!building) {
            render status: HttpStatus.NOT_FOUND
            return
        }

        if (building.hasErrors()) {
            respond building.errors, [status: HttpStatus.METHOD_NOT_ALLOWED]
            return
        }

        respond building
    }
}
