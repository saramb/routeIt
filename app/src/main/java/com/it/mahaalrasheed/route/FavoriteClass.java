package com.it.mahaalrasheed.route;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class FavoriteClass extends RealmObject {
    @PrimaryKey
    private int id ;
    private double lng;
    private double lat;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}