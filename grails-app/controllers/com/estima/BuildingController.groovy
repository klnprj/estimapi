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
        params.latlng = params.latlng?.collect{ Double.parseDouble(it)}
        params.sort = params.sort ?: 'name'
        params.order = params.order ?: 'asc'
        params.max = Math.min(params.int('max') ?: 10, 100)
        params.offset = params.offset ?: 0

        def buildingList

        if (params.latlng != null) {
            buildingList = buildingService.listByLocation(params.latlng, params.int('radius'))
        } else {
            buildingList = Building.list(params)
//            buildingList = Building.createCriteria().list(params, {
//                createAlias 'positions', 'p', JoinType.LEFT_OUTER_JOIN
////                createAlias 'p.dealer', 'pd', JoinType.LEFT_OUTER_JOIN
//                resultTransformer Criteria.DISTINCT_ROOT_ENTITY
//            })//buildingService.list(params.sort, params.order)
        }

        render view: '/building/index', model: [buildingList: buildingList, totalCount: buildingList.totalCount]
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
        def building = buildingService.create(p.name, p.address, p.location, clientId, projectId, authorId, p.description)

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
        def building = buildingService.update(id, p.name, p.address, p.location, clientId, projectId, p.description)

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
