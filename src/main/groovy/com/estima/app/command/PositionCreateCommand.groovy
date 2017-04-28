package com.estima.app.command

import grails.validation.Validateable

import java.time.LocalDate

class PositionCreateCommand implements Validateable {
    Long buildingId
    Long dealerId
    String contactName
    String type
    String spec
    String grossPrice
    String total
    String status
    Date dateShipped
    Integer dealerPrice
    Integer quantity

    static constraints = {
        type nullable: true
        spec nullable: true
        grossPrice nullable: true
        total nullable: true
        status nullable: true
        dateShipped nullable: true
        dealerPrice nullable: true
        quantity nullable: true
    }
}