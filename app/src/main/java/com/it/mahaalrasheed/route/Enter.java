package com.it.mahaalrasheed.route;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Enter extends AppCompatActivity {

     Button enter_bus_station , enter_metro_station;
    String adminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        enter_bus_station=(Button)findViewById(R.id.button6);
        enter_metro_station=(Button)findViewById(R.id.button7);

//if the user clicked on enter bus
        enter_bus_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminId= getIntent().getExtras().getString("AdminID");
                Intent i =new Intent(getApplicationContext(),EnterBusStation.class);
                i.putExtra("AdminID", adminId);
                startActivity(i);
            }
        });
//if the user clicked on enter metro
        enter_metro_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminId= getIntent().getExtras().getString("AdminID");
                Intent i =new Intent(getApplicationContext(),EnterMetroStation.class);
                i.putExtra("AdminID", adminId);
                startActivity(i);
            }
        });


    }
}
