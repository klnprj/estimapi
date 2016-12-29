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
        BuildingSearchCriteria criteria = new BuildingSearchCriteria(params.sort, params.order, params.int('max'),
                params.int('offset'))
        BuildingSearchFilter filter = new BuildingSearchFilter(params.q, params.list('authorId').collect{it.toLong()},
                params.list('status'))

        def buildingList = buildingService.listBuildings(criteria, filter)

        render view: '/building/index', model: [buildingList: buildingList, totalCount: buildingList.totalCount]
    }

    def locations() {
        LatLng latLng = new LatLng(params.latlng?.collect{ Double.parseDouble(it)})
        BuildingLocationCriteria locationCriteria = new BuildingLocationCriteria(latLng, params.int('radius'))
        BuildingSearchFilter filter = new BuildingSearchFilter(q: params.q,
                authorsIds: params.list('authorId').collect{it.toLong()}, statuses: params.list('status'))

        def buildingList = []

        if (!locationCriteria.latLng.isEmpty() && locationCriteria.radius) {
            buildingList = buildingService.listByLocation(locationCriteria)
        } else {
            buildingList = buildingService.listBuildings(new BuildingSearchCriteria(), filter)
        }

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
