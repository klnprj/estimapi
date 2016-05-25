package com.estima

import grails.transaction.Transactional

@Transactional
class DealerService {

    List list() {
        List result = Dealer.list()

        return result
    }

    Dealer create(String name) {
        Dealer dealer = new Dealer(name: name)

        dealer.save()

        if (dealer.hasErrors()) {
            transactionStatus.setRollbackOnly()
            log.debug 'Cannot save Dealer'
            return dealer
        }

        return dealer
    }
}
