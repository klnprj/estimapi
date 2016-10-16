package com.estima

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import org.springframework.http.HttpStatus

@Secured(["isAuthenticated()"])
class UserController extends RestfulController<User>{

    def springSecurityService
    def userService

    static responseFormats = ['json']

    UserController() {
        super(User)
    }

    def index() {
        def users = userService.list()

        respond users
    }

    def save(String name) {
        def user = userService.create(name)

        if (user.hasErrors()) {
            respond user.errors, [status: HttpStatus.METHOD_NOT_ALLOWED]
            return
        }

        respond user
    }

    def profile() {
        def currentUser = springSecurityService.getCurrentUser()

        respond currentUser
    }
}
