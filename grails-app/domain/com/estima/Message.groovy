package com.estima

import java.sql.Timestamp

class Message {

    Position position
    User author
    String text
    Timestamp dateCreated

    static belongsTo = [position: Position, author: User]

    static constraints = {
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'message_id_sequence']
    }
}
