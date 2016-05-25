package com.estima

import grails.transaction.Transactional

import java.sql.Timestamp

@Transactional
class MessageService {

    List list() {
        List result = Message.list()

        return result
    }

    Message create(Long positionId, Long authorId, String text) {
        Message message = new Message(position: Position.load(positionId), author: User.load(authorId), text: text, dateCreated: new Timestamp(new Date().time))

        message.save()

        if (message.hasErrors()) {
            transactionStatus.setRollbackOnly()
            return message
        }

        return message
    }
}
