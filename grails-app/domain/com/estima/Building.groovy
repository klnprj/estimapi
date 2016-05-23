package com.estima

class Building {
//    "address": "Москва, Доваторов, 2",
//    "client": "ЗАО 'Интеко'",
//    "id": 1,
//    "manager": "Владимир",
//    "name": "Садовые кварталы",
//    "project": "Скуратов С.А."

    static constraints = {
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'building_id_sequence']
    }
}
