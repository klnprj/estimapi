package com.estima

class Building {

    String name
    String address
    String location
    DictionaryItem client
    DictionaryItem project
    User author

    static belongsTo = [author: User]

    static constraints = {
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'building_id_sequence']
    }
}
