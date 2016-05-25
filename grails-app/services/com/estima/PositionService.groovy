package com.estima

import grails.transaction.Transactional

import java.sql.Timestamp

@Transactional
class PositionService {

    List list() {
        List result = Position.list()

        return result
    }

    Position create(Long buildingId, Long dealerId, String contactName, String type, String spec, String grossPrice, String total, String status, Timestamp dateShipped) {
        Position position = new Position(building: Building.load(buildingId), dealer: Dealer.load(dealerId),
                dateCreated: new Timestamp(new Date().time), contactName: contactName, type: type, spec: spec,
                grossPrice: grossPrice, total: total, status: status, dateShipped: dateShipped)

        position.save()

        if (position.hasErrors()) {
            transactionStatus.setRollbackOnly()
            return position
        }

        return position
    }
}
