package com.estima

class Position {

//    "id": 1,
//    "buildingId": 1,
//    "dealerId": 1,
//    "buildingDealerId": 1,
//    "dateCreated": 1,
//    "contactName": 1,
//    "type": 1,
//    "spec": 1,
//    "grossPrice": 1,
//    "total": 1,
//    "status": 1,
//    "dateShipped": 1

    static constraints = {
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'position_id_sequence']
    }
}
