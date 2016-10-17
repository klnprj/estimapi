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

    static belongsTo = [author: User]

    static hasMany = [positions: Position, contacts: Contact]

    static constraints = {
        client nullable: true
        project nullable: true
        description nullable: true
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'building_id_sequence']
        positions lazy: false
    }

    Timestamp getEarliestPositionDateCreated() {
        return positions?.min{ it.dateCreated.time }?.dateCreated
    }

    Timestamp getLatestPositionDateUpdated() {
        return positions?.max{ it.lastUpdated.time }?.lastUpdated
    }

    List getPositionsDealers() {
        return positions*.dealer?.unique{a, b -> a.id <=> b.id};
    }
}
