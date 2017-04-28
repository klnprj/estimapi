package com.estima

import com.estima.app.command.PositionCreateCommand
import com.estima.app.command.PositionUpdateCommand
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

    Position create(PositionCreateCommand cmd) {
        Position position = new Position(building: Building.load(cmd.buildingId), dealer: DictionaryItem.load(cmd.dealerId),
                dateCreated: new Timestamp(new Date().time), contactName: cmd.contactName, type: cmd.type, spec: cmd.spec,
                quantity: cmd.quantity, dealerPrice: cmd.dealerPrice,
                grossPrice: cmd.grossPrice, total: cmd.total, status: cmd.status, dateShipped: null)

        position.save()

        if (position.hasErrors()) {
            transactionStatus.setRollbackOnly()
            return position
        }

        return position
    }

    Position update(Long id, PositionUpdateCommand cmd) {
        Position position = Position.get(id)

        if (!position) {
            throw new IllegalArgumentException("Position [$id] not found.")
        }

        position.contactName = cmd.contactName
        position.type = cmd.type
        position.spec = cmd.spec
        position.quantity = cmd.quantity
        position.dealerPrice = cmd.dealerPrice
        position.grossPrice = cmd.grossPrice
        position.total = cmd.total
        position.status = cmd.status
        position.dateShipped = cmd.dateShipped ? new Timestamp(cmd.dateShipped.time) : null

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
