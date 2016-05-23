package com.estima

import grails.transaction.Transactional

@Transactional
class UserService {

    List list() {
        List result = User.list()

        return result
    }

    User create(String name) {
        User user = new User(name: name)

        user.save()

        if (user.hasErrors()) {
            transactionStatus.setRollbackOnly()
            log.debug 'Cannot save User'
            return user
        }

        return user
    }
}
