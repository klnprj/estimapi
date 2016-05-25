package com.estima

import java.sql.Timestamp

class Position {

    Building building
    Dealer dealer
    Timestamp dateCreated
    String contactName
    String type
    String spec
    String grossPrice
    String total
    String status
    Timestamp dateShipped

    static belongsTo = [building: Building, dealer: Dealer]

    static constraints = {
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'position_id_sequence']
    }
}
