package com.estima

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import grails.validation.Validateable
import org.springframework.http.HttpStatus

import java.sql.Timestamp

@Secured(["isAuthenticated()"])
class MessageController extends RestfulController<Message> {

    def messageService

    static responseFormats = ['json']

    MessageController() {
        super(Message)
    }

    def index(Long positionId) {
        List result = messageService.list(positionId)

        respond result
    }

    def save() {
        def p = request.JSON
        MessageCreateCommand cmd = new MessageCreateCommand()
        Message message

        bindData cmd, [text: p.text, position: Position.load(p.positionId), author: User.load(p.authorId)]

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
