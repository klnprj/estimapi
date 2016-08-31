package com.estima

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

@Secured(["isAuthenticated()"])
class DictionaryItemController extends RestfulController<DictionaryItem> {

    static responseFormats = ['json']

    DictionaryItemController() {
        super(DictionaryItem)
    }

//    def index(Integer max, Integer offset) {
//        params.max = Math.min(max ?: 10, 100)
//        params.offset = offset ?: 0
//
//
//    }

    def countItems() {
        def count = DictionaryItem.countByDictionary(Dictionary.findByKey(params.key))

        render count
    }
}
