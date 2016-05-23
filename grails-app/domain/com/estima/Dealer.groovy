package com.estima

class Dealer {

    String name

    static constraints = {
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'dealer_id_sequence']
    }
}
