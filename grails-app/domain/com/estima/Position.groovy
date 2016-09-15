package com.estima

import java.sql.Timestamp

class Position {

    Building building
    DictionaryItem dealer
    Timestamp dateCreated
    String contactName
    String type
    String spec
    String grossPrice
    String total
    String status
    Timestamp dateShipped

    static belongsTo = [building: Building]

    static constraints = {
        type nullable: true
        spec nullable: true
        grossPrice nullable: true
        total nullable: true
        status nullable: true
        dateShipped nullable: true
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'position_id_sequence']
    }
}
