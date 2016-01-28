package com.it.mahaalrasheed.route;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by shahadaziz on 26 ينا، 2016 م.
 */
public class FavoriteClass extends RealmObject {
    @PrimaryKey
    private long id ;
    private double lng;
    private double lat;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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