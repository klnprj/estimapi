package com.estima

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import org.springframework.http.HttpStatus

@Secured(["isAuthenticated()"])
class BuildingController extends RestfulController<Building> {

    def springSecurityService
    def buildingService

    static responseFormats = ['json']

    BuildingController() {
        super(Building)
    }

    def index() {
        def buildings
        params.latlng = params.latlng?.collect{ Double.parseDouble(it)}

        buildings = buildingService.list(params.latlng, params.int('radius'))

        respond buildings
    }

    def save() {
        def p = request.JSON
        def authorId = (Long) springSecurityService.getCurrentUserId()
        def building = buildingService.create(p.name, p.address, p.location, p.client, p.project, authorId)

        if (building.hasErrors()) {
            respond building.errors, [status: HttpStatus.METHOD_NOT_ALLOWED]
            return
        }

        respond building
    }

    def update() {
        def p = request.JSON
        Long id = params.long('id')
        def building = buildingService.update(id, p.name, p.address, p.location, p.client, p.project)

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
