package com.it.mahaalrasheed.route;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class EnterBusStation extends AppCompatActivity {

    public static final String ROOT_URL = "http://192.168.1.69/";
    Spinner dropdown1;
    Spinner dropdown2;
    List<String> spin = new ArrayList<String>();
    List<String> spinMetro = new ArrayList<String>();

    boolean flag1=false;

    String s,n,c,c2;
    EditText station,coorX ,name,coorY ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_bus_station);

        Button enter=(Button)findViewById(R.id.button9);
        station=(EditText)findViewById(R.id.editText4);
        name=(EditText)findViewById(R.id.editText5);
        coorX=(EditText)findViewById(R.id.editText15);
        coorY=(EditText)findViewById(R.id.editText);
        dropdown1=(Spinner)findViewById(R.id.spinner4);
        dropdown2=(Spinner)findViewById(R.id.spinner5);
        final TextView error=(TextView)findViewById(R.id.textView13);
        final TextView error2=(TextView)findViewById(R.id.textView15);

        Retrieve();

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinMetro.add(" ");

                s = station.getText().toString();
                n = name.getText().toString();
                c = coorX.getText().toString();
                c2=coorY.getText().toString();
;

                if (s.equals("") || n.equals("") || c.equals("") || c2.equals("") || dropdown1.getSelectedItem().toString().equals(" ") || dropdown2.getSelectedItem().toString().equals(" ")) {

                    if (dropdown1.getSelectedItem().toString().equals(" ")) {
                        error.requestFocus();
                        error.setError("Please select line ID");
                    }

                    if (station.getText().toString().equals("")) {
                        station.setError("Please fill station ID");
                    }

                    if (dropdown2.getSelectedItem().toString().equals(" ")) {
                        error2.setError("Please select a value");
                    }
                    if (name.getText().toString().equals("")) {
                        name.setError("Please fill the name");
                    }
                    if (coorX.getText().toString().equals("")) {
                        coorX.setError("Please fill the X coordination");
                    }
                    if (coorY.getText().toString().equals("")) {
                        coorY.setError("Please fill the Y coordination");
                    }


                } else {

                    coorX.setError(null);
                    coorY.setError(null);
                    station.setError(null);
                    name.setError(null);
                    error.setError(null);
                    error2.setError(null);



                    new AlertDialog.Builder(EnterBusStation.this)
                            .setMessage("are you sure you want to continue the  entering process ?")
                            .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                  EnterBusStation();
                                    finish();
                                    Toast.makeText(getApplicationContext(), "your bus station Entered successfully", Toast.LENGTH_LONG).show();


                                }
                            })

                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }

                flag1=true;
            }
        });
    }

    private void EnterBusStation(){
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);

        String LocationID = "2"+ dropdown1.getSelectedItemPosition()+station.getText().toString();

        //Defining the method insertuser of our interface
        api.EnterBusStation(

                //Passing the values by getting it from editTexts
                LocationID,
                coorX.getText().toString(),
                coorY.getText().toString(),
                name.getText().toString(),
                dropdown2.getSelectedItem().toString(),
                "najat",


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

                            Toast.makeText(EnterBusStation.this, output, Toast.LENGTH_LONG).show();


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Displaying the output as a toast
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(EnterBusStation.this, error.toString(), Toast.LENGTH_LONG).show();
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
                "4",



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
//
                            spin.add(" ");

                            while(!output.equals("")) {
                               // Toast.makeText(EnterBusStation.this, output, Toast.LENGTH_LONG).show();
                                String color = output.substring(0, output.indexOf(" "));
                                output = output.substring(output.indexOf(" ") + 1);
                                if (!spin.contains(color)) {
                                    spin.add(color);
                                }
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(EnterBusStation.this,android.R.layout.simple_dropdown_item_1line, spin);

                            dropdown1.setAdapter(adapter);

                            //Toast.makeText(EnterBusStation.this, output, Toast.LENGTH_LONG).show();


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Displaying the output as a toast
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(EnterBusStation.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );







        RestAdapter adapter2 = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api2 = adapter.create(routeAPI.class);
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
                        BufferedReader reader2 = null;

                        //An string to store output from the server
                        String output2 = "";

                        try {
                            //Initializing buffered reader
                            reader2 = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            //Reading the output in the string
                            output2 = reader2.readLine();
//
                            spinMetro.add(" ");

                            while(!output2.equals("")) {
                                // Toast.makeText(EnterBusStation.this, output, Toast.LENGTH_LONG).show();
                                String color2 = output2.substring(0, output2.indexOf(" "));
                                output2 = output2.substring(output2.indexOf(" ") + 1);
                                if (!spinMetro.contains(color2)) {
                                    spinMetro.add(color2);
                                }
                            }

                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(EnterBusStation.this,android.R.layout.simple_dropdown_item_1line, spinMetro);

                            dropdown2.setAdapter(adapter2);

                            //Toast.makeText(EnterBusStation.this, output, Toast.LENGTH_LONG).show();


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Displaying the output as a toast
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(EnterBusStation.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );




    }

}
