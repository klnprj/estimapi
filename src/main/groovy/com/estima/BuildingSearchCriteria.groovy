package com.estima

class BuildingSearchCriteria {
    String sort
    String order
    Integer max
    Integer offset

    BuildingSearchCriteria() {
        this.sort = 'name'
        this.order = 'asc'
        this.max = 10000
        this.offset = 0
    }

    BuildingSearchCriteria(String sort, String order, Integer max, Integer offset) {
        this.sort = sort ?: 'name'
        this.order = order ?: 'asc'
        this.max = Math.min(max ?: 10, 10000)
        this.offset = offset ?: 0
    }
}
