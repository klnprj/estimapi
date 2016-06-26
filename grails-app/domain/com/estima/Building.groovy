package com.estima

class Building {

    String name
    String address
    String location
    String client
    String manager
    String project

    static constraints = {
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'building_id_sequence']
    }
}
