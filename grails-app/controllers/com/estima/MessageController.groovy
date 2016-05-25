package com.estima

import grails.rest.RestfulController
import grails.validation.Validateable
import org.springframework.http.HttpStatus

import java.sql.Timestamp

class MessageController extends RestfulController<Message> {

    def messageService

    static responseFormats = ['json']

    MessageController() {
        super(Message)
    }

    def list() {
        List result = messageService.list()

        respond result
    }

    def create(MessageCreateCommand cmd) {
        Message message

        cmd.validate()

        if(cmd.hasErrors()) {
            log.debug 'Cannot create Message'
            respond cmd.errors, [status: HttpStatus.METHOD_NOT_ALLOWED]
            return
        }

        message = messageService.create(cmd.position?.id, cmd.author?.id, cmd.text)

        if (message.hasErrors()) {
            log.debug 'Cannot create Message'
            respond message.errors, [status: HttpStatus.FORBIDDEN]
            return
        }

        respond message, [status: HttpStatus.CREATED]
    }
}

class MessageCreateCommand implements Validateable {
    Position position
    User author
    String text
}
