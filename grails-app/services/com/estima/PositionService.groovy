package com.estima

import grails.transaction.Transactional
import groovy.sql.Sql

import java.sql.Timestamp

@Transactional
class PositionService {

    def dataSource

    List list(Long buildingId) {
        List result

        if (buildingId) {
            result = Position.findAllByBuilding(Building.load(buildingId))
        } else {
            result = Position.list()
        }

        return result
    }

    Position create(Long buildingId, Long dealerId, String contactName, String type, String spec, String grossPrice, String total, String status, String dateShipped) {
        Position position = new Position(building: Building.load(buildingId), dealer: DictionaryItem.load(dealerId),
                dateCreated: new Timestamp(new Date().time), contactName: contactName, type: type, spec: spec,
                grossPrice: grossPrice, total: total, status: status, dateShipped: null)

        position.save()

        if (position.hasErrors()) {
            transactionStatus.setRollbackOnly()
            return position
        }

        return position
    }

    boolean delete(Long id) {
        def sql = new Sql(dataSource)
        Position position = Position.get(id)

        if (!position) {
            log.debug "Cannot find Position with id: $id"
            return false
        }

        sql.execute([id: id], "DELETE FROM position WHERE id=:id")

        return true
    }
}
