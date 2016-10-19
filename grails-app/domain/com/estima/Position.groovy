package com.estima

import java.sql.Timestamp

class Position {

    Building building
    DictionaryItem dealer
    Timestamp dateCreated
    Timestamp lastUpdated
    String contactName
    String type
    String spec
    String grossPrice
    String total
    String status
    Timestamp dateShipped
    Integer dealerPrice
    Integer quantity

    static belongsTo = [building: Building]

    static constraints = {
        dateCreated nullable: true
        lastUpdated nullable: true
        type nullable: true
        spec nullable: true, maxSize: 512
        grossPrice nullable: true
        total nullable: true
        status nullable: true
        dateShipped nullable: true
        dealerPrice nullable: true
        quantity nullable: true
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'position_id_sequence']
        dealer lazy: false
    }
}
