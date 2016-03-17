package com.it.mahaalrasheed.route;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Delete extends AppCompatActivity {

    Button delete_bus_station ,delete_metro_station;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        delete_bus_station=(Button)findViewById(R.id.button10);
        delete_metro_station=(Button)findViewById(R.id.button11);

//if the user clicked on delete bus
        delete_bus_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Delete.this,DeleteBusStation.class);
                startActivity(i);
            }
        });
//if the user clicked on delete metro
        delete_metro_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Delete.this, DeleteMetroStation.class);
                startActivity(i);
            }
        });
    }
}
