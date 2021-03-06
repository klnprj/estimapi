package com.estima

import java.sql.Timestamp

class Building {

    String name
    String address
    String location
    DictionaryItem client
    DictionaryItem project
    User author
    String description
    String status

    Timestamp latestPositionDateUpdated

    static belongsTo = [author: User]

    static hasMany = [positions: Position, contacts: Contact]

    static constraints = {
        client nullable: true
        project nullable: true
        description nullable: true
        status nullable: true
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'building_id_sequence']
        positions lazy: false
        latestPositionDateUpdated formula: "(SELECT MAX(p.lastupdated) FROM position p WHERE p.building_id=ID)"
    }

    Timestamp getEarliestPositionDateCreated() {
        return positions?.min{ it.dateCreated.time }?.dateCreated
    }

    List getPositionsDealers() {
        return positions*.dealer?.unique{a, b -> a.id <=> b.id};
    }
}
