package com.estima

import grails.transaction.Transactional
import groovy.sql.Sql

@Transactional
class ContactService {

    def dataSource

    Contact create(Long buildingId, Long dictionaryItemId, String info) {
        Contact contact = new Contact(building: Building.load(buildingId), contact: DictionaryItem.load(dictionaryItemId), info: info)

        contact.save()

        if (contact.hasErrors()) {
            log.debug('Cannot save Contact')
            transactionStatus.setRollbackOnly()
            return contact
        }

        return contact
    }

    boolean delete(Long id) {
        def sql = new Sql(dataSource)
        Contact contact = Contact.get(id)

        if (!contact) {
            log.debug "Cannot find Contact with id: $id"
            return false
        }

        sql.execute([id: id], "DELETE FROM contact WHERE id=:id")

        return true
    }
}
