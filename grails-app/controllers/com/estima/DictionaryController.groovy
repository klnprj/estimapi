package com.estima

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

@Secured(["isAuthenticated()"])
class DictionaryController extends RestfulController<Dictionary> {

    static responseFormats = ['json']

    DictionaryController() {
        super(Dictionary)
    }

}
