package com.estima

class DictionaryItem {

    String title
    String contactName
    String contactPosition
    String phone

    static belongsTo = [dictionary: Dictionary]

    static mapping = {
        id generator: 'sequence', params: [sequence: 'dictionary_item_id_sequence']
    }

    static constraints = {
        title nullable: false
        contactName nullable: true
        contactPosition nullable: true
        phone nullable: true
    }
}
