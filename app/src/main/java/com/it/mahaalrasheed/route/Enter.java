package com.it.mahaalrasheed.route;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Enter extends AppCompatActivity {

     Button enter_bus_station , enter_metro_station;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        enter_bus_station=(Button)findViewById(R.id.button6);
        enter_metro_station=(Button)findViewById(R.id.button7);


        enter_bus_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),EnterBusStation.class);
                startActivity(i);
            }
        });

        enter_metro_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EnterMetroStation.class);
                startActivity(i);
            }
        });


    }
}
