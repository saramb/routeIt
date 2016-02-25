package com.it.mahaalrasheed.route;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class testroute extends AppCompatActivity {

    public static final String ROOT_URL = "http://192.168.1.69/";

    EditText from , to;
    String fromId , toId, withen;
    String [][] Mline1 = new String [100][100];
    String [][] Mline2 = new String [100][100];
    String [][] Mline3 = new String [100][100];
    String [][] Mline4 = new String [100][100];
    String [][] Mline5 = new String [100][100];
    String [][] Mline6 = new String [100][100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testroute);
        link();



         from = (EditText)findViewById(R.id.editText3);
         to = (EditText)findViewById(R.id.editText6);
        Button button= (Button)findViewById(R.id.button17);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               route();
            }
        });

    }

    private void route(){
        //Here we will handle the http request to retrieve Metro coordinates from mysql db

        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);
        //Defining the method PlotStation of our interface
        api.route(
                "24.895652",
                "46.603078",
                "24.67424081",
                "46.69503247",
                //Creating an anonymous callback
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        //On success we will read the server's output using buffered reader
                        //Creating a buffered reader object
                        BufferedReader reader = null;

                        //An string to store output from the server
                        String output = "";

                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();
                            Toast.makeText(testroute.this, output + "**********", Toast.LENGTH_LONG).show();

                            fromId = output.substring(0, output.indexOf("/"));
                            toId = output.substring(output.indexOf("/") + 1);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occurred displaying the error as toast
                        Toast.makeText(testroute.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        );
    }



    private void link(){
        //Here we will handle the http request to retrieve Metro coordinates from mysql db

        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);
        //Defining the method PlotStation of our interface
        api.link(
                "2",
                //Creating an anonymous callback
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        //On success we will read the server's output using buffered reader
                        //Creating a buffered reader object
                        BufferedReader reader = null;

                        //An string to store output from the server
                        String output = "";

                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();
                            Toast.makeText(testroute.this, output , Toast.LENGTH_LONG).show();
                            while (!output.equals("")) {
                            fromId = output.substring(0, output.indexOf("/"));
                            output = output.substring(output.indexOf("/")+1);
                            toId= output.substring(0, output.indexOf("/"));
                            output = output.substring(output.indexOf("/")+1);
                            withen = output.substring(0, output.indexOf(":"));
                            output = output.substring(output.indexOf(":")+1);


                            int firstline = Integer.parseInt(fromId.charAt(2)+"");
                            int secondline =  Integer.parseInt(toId.charAt(2)+"");
                            String s1 = fromId.substring(6);
                            int station1 = Integer.parseInt(s1);

                            String s2 = toId.substring(6);
                           System.out.println (s2);

                            int station2 = Integer.parseInt(s2);


                            if (firstline == secondline) {
                                if (firstline == 1) { Mline1 [station1][station2] = "1"; Mline1 [station2][station1] = "1"; }
                                if (firstline == 2) { Mline2 [station1][station2] = "1"; Mline2 [station2][station1] = "1"; }
                                if (firstline == 3) { Mline3 [station1][station2] = "1"; Mline3 [station2][station1] = "1"; }
                                if (firstline == 4) { Mline4 [station1][station2] = "1"; Mline4 [station2][station1] = "1"; }
                                if (firstline == 5) { Mline5 [station1][station2] = "1"; Mline5 [station2][station1] = "1";}
                                if (firstline == 6) { Mline6 [station1][station2] = "1"; Mline6 [station2][station1] = "1"; }

                            }
                            else {
                                if (firstline == 1) { Mline1 [station1][29] = toId; }
                                if (firstline == 2) { Mline2 [station1][29] = toId; }
                                if (firstline == 3) { Mline3 [station1][29] = toId; }
                                if (firstline == 4) { Mline4 [station1][29] = toId; }
                                if (firstline == 5) { Mline5 [station1][29] = toId; }
                                if (firstline == 6) { Mline6 [station1][29] = toId; }
                                //////
                                if (secondline == 1) { Mline1 [station2][29] = fromId; }
                                if (secondline == 2) { Mline2 [station2][29] = fromId; }
                                if (secondline == 3) { Mline3 [station2][29] = fromId; }
                                if (secondline == 4) { Mline4 [station2][29] = fromId; }
                                if (secondline == 5) { Mline5 [station2][29] = fromId; }
                                if (secondline == 6) { Mline6 [station2][29] = fromId; }
                            }
                      }



                        } catch (IOException e) {
                            e.printStackTrace();

                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occurred displaying the error as toast
                        Toast.makeText(testroute.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
}
