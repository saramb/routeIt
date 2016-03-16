package com.it.mahaalrasheed.route;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Sara on 1/30/2016.
 */
public class Notification extends RealmObject {
    @PrimaryKey
    private int pk;
    private int ID;

    public void setPk(int pk) {
        this.pk = pk;
    }

    public int getID() {
        return ID;
    }

    public int getPk() {return pk;}

    public void setID(int ID) {
        this.ID = ID;
    }



}
