package com.estima

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import grails.validation.Validateable
import org.springframework.http.HttpStatus

@Secured(["isAuthenticated()"])
class CommentController extends RestfulController<Comment> {

    def springSecurityService
    def commentService

    static responseFormats = ['json']

    CommentController() {
        super(Comment)
    }

    def index(Long buildingId) {
        def comments = Comment.findAllByBuilding(Building.load(buildingId), [sort: 'dateCreated', order: 'desc'])

        respond comments
    }

    def save(Long buildingId) {
        def p = request.JSON
        def cmd = new CommentCreateCommand()
        def authorId = (Long) springSecurityService.getCurrentUserId()
        def comment

        bindData cmd, [building: Building.load(buildingId), author: User.load(authorId), text: p.text]

        cmd.validate()

        if (cmd.hasErrors()) {
            log.debug 'Cannot create Comment'
            respond cmd.errors, [status: HttpStatus.METHOD_NOT_ALLOWED]
            return
        }

        comment = commentService.create(cmd.building.id, cmd.author.id, cmd.text)

        if (comment.hasErrors()) {
            log.debug 'Cannot save Comment'
            respond comment.errors, [status: HttpStatus.FORBIDDEN]
            return
        }

        respond comment, [status: HttpStatus.CREATED]
    }
}

class CommentCreateCommand implements Validateable {
    Building building
    User author
    String text
}
