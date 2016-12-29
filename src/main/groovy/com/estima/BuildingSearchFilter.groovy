package com.estima

class BuildingSearchFilter {
    String q
    List<Long> authorsIds
    List<String> statuses

    BuildingSearchFilter() {
        this.q = ''
        this.authorsIds = Collections.emptyList()
        this.statuses = Collections.emptyList()
    }

    BuildingSearchFilter(String q, List<Long> authorsIds, List<String> statuses) {
        this.q = q?.trim()
        this.authorsIds = authorsIds
        this.statuses = statuses
    }
}
