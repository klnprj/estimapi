package com.estima

class User {

    String name

    static constraints = {
    }

    static mapping = {
        table 'JUSER'
        id generator: 'sequence', params: [sequence: 'user_id_sequence']
    }
}
