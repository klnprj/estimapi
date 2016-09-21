package com.estima

import java.sql.Timestamp

class Comment {

    Timestamp dateCreated
    User author
    String text

    static belongsTo = [building: Building]

    static constraints = {
        dateCreated nullable: true
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'comment_id_sequence']
    }
}
