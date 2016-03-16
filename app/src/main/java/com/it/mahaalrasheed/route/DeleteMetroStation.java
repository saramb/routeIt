package com.it.mahaalrasheed.route;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DeleteMetroStation extends AppCompatActivity {

    private String ROOT_URL = map.ROOT_URL;
    Spinner dropdown;
    List<String> spin = new ArrayList<String>();
    Button delete;
    TextView error;
    String selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_metro_station);

        delete=(Button)findViewById(R.id.button15);
        dropdown=(Spinner)findViewById(R.id.spinner3);
        error=(TextView)findViewById(R.id.textView24);

        spin.add("Please Select metro station ..");

        Retrieve();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = dropdown.getSelectedItem().toString();

                if (selected.equals("Please Select metro station ..")) {
                    error.setError("Please select a value !");
                } else {

                    error.setError(null);
                    new AlertDialog.Builder(DeleteMetroStation.this)
                            .setMessage("are you sure you want to continue the deletion process ?")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DeleteMetroStation();
                                    finish();
                                }
                            })

                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
        });
    }

    private void DeleteMetroStation(){
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);

        //Defining the method insertuser of our interface
        api.DeleteMetroStation(

                //Passing the values by getting it from editTexts
                selected,

                //Creating an anonymous callback
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        //On success we will read the server's output using bufferedreader
                        //Creating a bufferedreader object
                        BufferedReader reader = null;

                        //An string to store output from the server
                        String output = "";

                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();
                            Toast.makeText(getApplicationContext(),output, Toast.LENGTH_LONG).show();


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Displaying the output as a toast
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(DeleteMetroStation.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void Retrieve(){
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);
        //Defining the method insertuser of our interface
        api.Retrieve(

                //Passing the values by getting it from editTexts
                "2",


                //Creating an anonymous callback
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        //On success we will read the server's output using bufferedreader
                        //Creating a bufferedreader object
                        BufferedReader reader = null;

                        //An string to store output from the server
                        String output = "";

                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();

                            while (!output.equals("")) {
                                String stationName = output.substring(0, output.indexOf(" "));
                                output = output.substring(output.indexOf(" ") + 1);
                                if (!spin.contains(stationName)) {
                                    spin.add(stationName);
                                }
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeleteMetroStation.this, android.R.layout.simple_dropdown_item_1line, spin);

                            dropdown.setAdapter(adapter);
                            

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Displaying the output as a toast
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(DeleteMetroStation.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}
