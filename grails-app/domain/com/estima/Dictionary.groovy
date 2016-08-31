package com.estima

class Dictionary {

    String key
    String name

    static mapping = {
        id generator: 'sequence', params: [sequence: 'dictionary_id_sequence']
    }

    static constraints = {
    }
}
