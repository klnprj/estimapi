package com.estima

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import grails.validation.Validateable
import org.springframework.http.HttpStatus

@Secured(["isAuthenticated()"])
class ContactController extends RestfulController<Contact> {

    def contactService

    static responseFormats = ['json']

    ContactController() {
        super(Contact)
    }

    def index(Long buildingId) {
        def result = Contact.findAllByBuilding(Building.load(buildingId))

        render view: '/contact/index', model: [contactList: result]
    }

    def save(Long buildingId) {
        def p = request.JSON
        def contactId = p.contactId
        def cmd = new ContactCreateCommand(building: Building.load(buildingId), contact: DictionaryItem.load(contactId), info: p.info)

        cmd.validate()

        if (cmd.hasErrors()) {
            log.debug('Wrong Contact attributes')
            respond cmd.errors, [status: HttpStatus.METHOD_NOT_ALLOWED]
            return
        }

        Contact contact = contactService.create(cmd.building.id, cmd.contact.id, cmd.info)

        if (contact.hasErrors()) {
            log.debug('Wrong Contact attributes')
            respond contact.errors, [status: HttpStatus.METHOD_NOT_ALLOWED]
            return
        }

        respond contact, [status: HttpStatus.CREATED]
    }

    def delete() {
        boolean result = contactService.delete(params.long('id'))

        if (!result) {
            render status: HttpStatus.NOT_FOUND
            return
        }

        render status: HttpStatus.NO_CONTENT
    }
}

class ContactCreateCommand implements Validateable {
    Building building
    DictionaryItem contact
    String info

    static constraints = {
        importFrom(Contact)
    }
}
