package com.estima

class LatLng {
    Double lat
    Double lng

    LatLng() {}

    LatLng(Double lat, Double lng) {
        this.lat = lat
        this.lng = lng
    }

    LatLng(List<Double> latLng) {
        this(latLng?.get(0), latLng?.get(1))
    }

    boolean isEmpty() {
        !(lat && lng)
    }
}
