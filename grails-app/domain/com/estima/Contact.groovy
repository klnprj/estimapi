package com.estima

class Contact {

    DictionaryItem contact
    String info

    static belongsTo = [building: Building]

    static constraints = {
        info nullable: true, maxSize: 512
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'contact_id_sequence']
    }
}
