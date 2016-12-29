package com.estima

class BuildingLocationCriteria {
    LatLng latLng
    Integer radius

    BuildingLocationCriteria() {}

    BuildingLocationCriteria(LatLng latLng, Integer radius) {
        this.latLng = latLng
        this.radius = radius
    }
}
