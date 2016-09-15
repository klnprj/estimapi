package com.estima

class Building {

    String name
    String address
    String location
    DictionaryItem client
    DictionaryItem project
    User author
    String description

    static belongsTo = [author: User]

    static constraints = {
        description nullable: true
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'building_id_sequence']
    }
}
