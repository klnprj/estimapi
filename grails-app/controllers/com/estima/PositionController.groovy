package com.estima

import com.estima.app.command.PositionCreateCommand
import com.estima.app.command.PositionUpdateCommand
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import org.springframework.http.HttpStatus

@Secured(["isAuthenticated()"])
class PositionController extends RestfulController<Position> {

    def positionService

    static responseFormats = ['json']

    PositionController() {
        super(Position)
    }

    def index(Long buildingId) {
        List result = positionService.list(buildingId)

        render view: '/position/index', model: [positionList: result]
    }

    def show() {
        Position item = queryForResource(params.long('id'))

        respond item
    }

    def save() {
        Map p = request.JSON as Map
        PositionCreateCommand cmd = new PositionCreateCommand()
        Position position

        bindData(cmd, p, [include: cmd.properties.keySet()])
        cmd.validate()

        if (cmd.hasErrors()) {
            log.debug('Wrong Position attributes')
            respond cmd.errors, [status: HttpStatus.METHOD_NOT_ALLOWED]
            return
        }

        position = positionService.create(cmd)

        if (position.hasErrors()) {
            log.debug('Wrong Position attributes')
            respond position.errors, [status: HttpStatus.METHOD_NOT_ALLOWED]
            return
        }

        respond position, [status: HttpStatus.CREATED]
    }

    def update(Long id) {
        Map p = request.JSON as Map
        PositionUpdateCommand cmd = new PositionUpdateCommand()

        p.dealerId = p.dealer.id
        bindData(cmd, p, [include: cmd.properties.keySet()])
        Position position

        cmd.validate()

        if (cmd.hasErrors()) {
            log.debug('Wrong Position attributes')
            respond cmd.errors, [status: HttpStatus.METHOD_NOT_ALLOWED]
            return
        }

        position = positionService.update(id, cmd)

        if (position.hasErrors()) {
            log.debug('Wrong Position attributes')
            respond position.errors, [status: HttpStatus.METHOD_NOT_ALLOWED]
            return
        }

        respond position, [status: HttpStatus.OK]
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


