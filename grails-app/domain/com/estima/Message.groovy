package com.estima

class Message {

//    "id": 2,
//    "positionId": 1,
//    "authorId": 2,
//    "text": "Позвонил.",
//    "dateCreated": "2016-03-24T10:47:12+03:00"

    static constraints = {
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'message_id_sequence']
    }
}
