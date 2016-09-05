package com.estima

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import org.springframework.http.HttpStatus

@Secured(["isAuthenticated()"])
class DictionaryItemController extends RestfulController<DictionaryItem> {

    static responseFormats = ['json']

    DictionaryItemController() {
        super(DictionaryItem)
    }

    def index(String key, Integer max, Integer offset) {
        params.max = Math.min(max ?: 10, 100)
        params.offset = offset ?: 0

        def items = DictionaryItem.findAllByDictionary(Dictionary.findByKey(key), params)

        respond items
    }

    def countItems() {
        def count = DictionaryItem.countByDictionary(Dictionary.findByKey(params.key))

        render count
    }

    def save(String key) {
        def p = request.JSON.data
        def item

        p.dictionary = Dictionary.findByKey(key)
        item = new DictionaryItem(p as Map)

        item.validate()

        if (item.hasErrors()) {
            respond item.errors, [status: HttpStatus.BAD_REQUEST]
            return
        }

        DictionaryItem.withTransaction { status ->
            item.save()
        }

        if (item.hasErrors()) {
            respond item.errors, [status: HttpStatus.BAD_REQUEST]
            return
        }

        respond item
    }
}
