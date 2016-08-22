package com.estima

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import org.springframework.http.HttpStatus

@Secured(["isAuthenticated()"])
class DealerController extends RestfulController<Dealer>{

    def dealerService

    static responseFormats = ['json']

    DealerController() {
        super(Dealer)
    }

    def index() {
        def dealers = dealerService.list()

        respond dealers
    }

    def save(String name) {
        def dealer = dealerService.create(name)

        if (dealer.hasErrors()) {
            respond dealer.errors, [status: HttpStatus.METHOD_NOT_ALLOWED]
            return
        }

        respond dealer
    }
}