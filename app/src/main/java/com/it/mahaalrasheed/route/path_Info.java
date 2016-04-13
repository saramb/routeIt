package com.it.mahaalrasheed.route;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;


public class path_Info extends AppCompatActivity {

    ListView list;
    String[] itemname = new String[routeInfo.number];
    Integer[] imgid = new Integer[routeInfo.number];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path__info);


        for( int i =0;i<routeInfo.number;i++){
            itemname[i]=routeInfo.stationName[i];
            if (routeInfo.Number[i]==1){
                imgid[i]= R.mipmap.metro;

            }
            else
            {  imgid[i]= R.mipmap.busicon;}
        }


        CustomListAdapter adapter = new CustomListAdapter(this, itemname, imgid);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
    }
}