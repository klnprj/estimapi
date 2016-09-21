package com.estima

import grails.transaction.Transactional

class CommentService {

    @Transactional
    Comment create(Long buildingId, Long authorId, String text) {
        Comment comment = new Comment(building: Building.load(buildingId), author: User.load(authorId), text: text)

        comment.save()

        if (comment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            return comment
        }

        return comment
    }
}
