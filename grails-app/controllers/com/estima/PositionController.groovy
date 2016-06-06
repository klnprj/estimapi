package com.estima

import grails.rest.RestfulController
import grails.validation.Validateable
import org.springframework.http.HttpStatus

import java.sql.Timestamp

class PositionController extends RestfulController<Position> {

    def positionService

    static responseFormats = ['json']

    PositionController() {
        super(Position)
    }

    def index() {
        List result = positionService.list()

        respond result
    }

    def create() {
        Map p = request.JSON as Map
        Long buildingId = p.buildingId as Long
        Long dealerId = p.dealerId as Long
        String contactName = p.contactName
        String type = p.type
        String spec = p.spec
        String grossPrice = p.grossPrice
        String total = p.total
        String status = p.status
        String dateShipped = p.dateShipped
        PositionCreateCommand cmd = new PositionCreateCommand([buildingId: buildingId, dealerId: dealerId, contactName: contactName,
                type: type, spec: spec, grossPrice: grossPrice, total: total, status: status, dateShipped: dateShipped])
        Position position

        cmd.validate()

        if (cmd.hasErrors()) {
            log.debug('Wrong Position attributes')
            respond cmd.errors, [status: HttpStatus.METHOD_NOT_ALLOWED]
            return
        }

        position = positionService.create(cmd.buildingId, cmd.dealerId, cmd.contactName, cmd.type, cmd.spec,
                cmd.grossPrice, cmd.total, cmd.status, cmd.dateShipped)

        if (position.hasErrors()) {
            log.debug('Wrong Position attributes')
            respond position.errors, [status: HttpStatus.METHOD_NOT_ALLOWED]
            return
        }

        respond position, [status: HttpStatus.CREATED]
    }

    def delete() {
        boolean result = positionService.delete(params.long('id'))

        if (!result) {
            render status: HttpStatus.NOT_FOUND
            return
        }

        render status: HttpStatus.NO_CONTENT
    }
}

class PositionCreateCommand implements Validateable {
    Long buildingId
    Long dealerId
    String contactName
    String type
    String spec
    String grossPrice
    String total
    String status
    String dateShipped
}


