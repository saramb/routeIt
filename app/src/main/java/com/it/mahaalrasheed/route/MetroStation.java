package com.it.mahaalrasheed.route;

import com.google.android.gms.maps.model.LatLng;

public class MetroStation {
    private LatLng position;

    public MetroStation(LatLng position) {
        this.position = position;
    }

    public LatLng getPosition() {
        return position;
    }
}

