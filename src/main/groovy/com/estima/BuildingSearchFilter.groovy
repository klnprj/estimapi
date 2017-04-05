package com.estima

import java.time.Instant

class BuildingSearchFilter {
    String q
    List<Long> authorsIds
    List<String> statuses
    Date lastUpdatedFrom

    BuildingSearchFilter() {
        this.q = ''
        this.authorsIds = Collections.emptyList()
        this.statuses = Collections.emptyList()
        this.lastUpdatedFrom = null
    }

    BuildingSearchFilter(String q, List<Long> authorsIds, List<String> statuses, Instant lastUpdatedFrom) {
        this.q = q?.trim()
        this.authorsIds = authorsIds
        this.statuses = statuses
        this.lastUpdatedFrom = lastUpdatedFrom ? Date.from(lastUpdatedFrom) : null
    }
}
