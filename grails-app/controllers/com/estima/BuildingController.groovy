package com.estima

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import org.hibernate.Criteria
import org.hibernate.sql.JoinType
import org.springframework.http.HttpStatus

@Secured(["isAuthenticated()"])
class BuildingController extends RestfulController<Building> {

    def springSecurityService
    def buildingService

    static responseFormats = ['json']

    BuildingController() {
        super(Building)
    }

    protected Building queryForResource(Serializable id) {
        Building item = Building.findById(id, [fetch: [client: 'eager', project: 'eager']])

        return item
    }

    def index() {
        params.sort = params.sort ?: 'name'
        params.order = params.order ?: 'asc'
        params.max = Math.min(params.int('max') ?: 10, 100)
        params.offset = params.offset ?: 0
        List authorsIds = params.list('authorId').collect{ it.toLong() }

        def buildingList = buildingService.list(params.sort, params.order, params.max, params.offset, params.q?.trim(), authorsIds)

        render view: '/building/index', model: [buildingList: buildingList, totalCount: buildingList.totalCount]
    }

    def locations() {
        params.latlng = params.latlng?.collect{ Double.parseDouble(it)}

        def buildingList = buildingService.listByLocation(params.latlng, params.int('radius'))

        render view: '/building/list', model: [buildingList: buildingList]
    }

    def show() {
        Building item = queryForResource(params.long('id'))

        respond item
    }

    def save() {
        def p = request.JSON
        def authorId = (Long) springSecurityService.getCurrentUserId()
        Long clientId = p.client?.id
        Long projectId = p.project?.id
        def building = buildingService.create(p.name, p.address, p.location, clientId, projectId, authorId, p.description, p.status)

        if (building.hasErrors()) {
            respond building.errors, [status: HttpStatus.METHOD_NOT_ALLOWED]
            return
        }

        respond building
    }

    def update() {
        def p = request.JSON
        Long id = params.long('id')
        Long clientId = p.client?.id
        Long projectId = p.project?.id
        def building = buildingService.update(id, p.name, p.address, p.location, clientId, projectId, p.description, p.status)

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
