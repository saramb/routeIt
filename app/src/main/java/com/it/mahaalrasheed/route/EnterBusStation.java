package com.it.mahaalrasheed.route;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

    private String ROOT_URL = map.ROOT_URL;
    Spinner dropdown1;
    Spinner dropdown2;
    Spinner dropdown3;
    List<String> spin = new ArrayList<String>();
    List<String> spinMetro = new ArrayList<String>();
    List<String> spinPosition = new ArrayList<String>();
    boolean flag1=false;

    TextView error,error2,error3, stationStreet ;

    String s,n,c,c2,d1,d2,d3,st;
    EditText station,coorX ,name,coorY,street;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_bus_station);

        Button enter=(Button)findViewById(R.id.button9);
        station=(EditText)findViewById(R.id.editText4);
        name=(EditText)findViewById(R.id.editText5);
        coorX=(EditText)findViewById(R.id.editText15);
        coorY=(EditText)findViewById(R.id.editText);
        street=(EditText)findViewById(R.id.editText2);
        stationStreet=(TextView)findViewById(R.id.StreettextView);
        dropdown1=(Spinner)findViewById(R.id.spinner4);
        dropdown2=(Spinner)findViewById(R.id.spinner5);
        dropdown3=(Spinner)findViewById(R.id.spinner6);
        error=(TextView)findViewById(R.id.textView13);
        error2=(TextView)findViewById(R.id.textView15);
        error3=(TextView)findViewById(R.id.textView33);


        spinPosition.add(" ");
        spinPosition.add("At the begining");
        spinPosition.add("At the end");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EnterBusStation.this,android.R.layout.simple_dropdown_item_1line, spinPosition);

        dropdown3.setAdapter(adapter);


        Retrieve();

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinMetro.add(" ");

                s = station.getText().toString();
                n = name.getText().toString();
                st= street.getText().toString();
                c = coorX.getText().toString();
                c2 = coorY.getText().toString();
                d1=dropdown1.getSelectedItem().toString();
                d2=dropdown2.getSelectedItem().toString();
                d3=dropdown3.getSelectedItem().toString();




                if (s.equals("") || n.equals("") || c.equals("") || c2.equals("") || d1.equals(" ") || d2.equals(" ")||d3.equals(" ")||st.equals("")) {

                    if (dropdown1.getSelectedItem().toString().equals(" ")) {
                        //error.requestFocus();
                        error.setError("Please select line ID");
                    }else
                        error.setError(null);

                    if (dropdown2.getSelectedItem().toString().equals(" ")) {
                       // error2.requestFocus();
                        error2.setError("Please select a value");
                    }else
                        error2.setError(null);

                    if (dropdown3.getSelectedItem().toString().equals(" ")) {
                        // error2.requestFocus();
                        error3.setError("Please select the position of the station");
                    }else
                        error3.setError(null);


                    if (station.getText().toString().equals("")) {
                        station.setError("Please fill station ID");
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
                    if (street.getText().toString().equals("")) {
                        street.setError("Please fill the station street");
                    }


                } else {

                    error.setError(null);
                    error2.setError(null);
                    coorX.setError(null);
                    coorY.setError(null);
                    station.setError(null);
                    name.setError(null);
                    stationStreet.setError(null);



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

                flag1 = true;
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

        String LocationID = "2."+ dropdown1.getSelectedItemPosition()+"."+stationStreet.getText().toString()+"."+station.getText().toString();

        //Defining the method insertuser of our interface
        api.EnterBusStation(

                //Passing the values by getting it from editTexts
                LocationID,
                coorX.getText().toString(),
                coorY.getText().toString(),
                name.getText().toString(),
                dropdown2.getSelectedItem().toString(),
                dropdown1.getSelectedItemPosition(),
                getIntent().getExtras().getString("AdminID"),
                dropdown3.getSelectedItemPosition()+"",


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

                            while (!output2.equals("")) {
                                // Toast.makeText(EnterBusStation.this, output, Toast.LENGTH_LONG).show();
                                String color2 = output2.substring(0, output2.indexOf(" "));
                                output2 = output2.substring(output2.indexOf(" ") + 1);
                                if (!spinMetro.contains(color2)) {
                                    spinMetro.add(color2);
                                }
                            }

                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(EnterBusStation.this, android.R.layout.simple_dropdown_item_1line, spinMetro);

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
