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

    def create(PositionCreateCommand cmd) {
        Position position

        cmd.validate()

        if (cmd.hasErrors()) {
            log.debug('Wrong Position attributes')
            respond cmd.errors, [status: HttpStatus.METHOD_NOT_ALLOWED]
            return
        }

        position = positionService.create(cmd.building?.id, cmd.dealer?.id, cmd.contactName, cmd.type, cmd.spec,
                cmd.grossPrice, cmd.total, cmd.status, cmd.dateShipped)

        if (position.hasErrors()) {
            log.debug('Wrong Position attributes')
            respond position.errors, [status: HttpStatus.METHOD_NOT_ALLOWED]
            return
        }

        respond position, [status: HttpStatus.CREATED]
    }
}

class PositionCreateCommand implements Validateable {
    Building building
    Dealer dealer
    String contactName
    String type
    String spec
    String grossPrice
    String total
    String status
    Timestamp dateShipped
}


