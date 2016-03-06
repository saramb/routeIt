package com.it.mahaalrasheed.route;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class routeInfo extends AppCompatActivity {

    private String ROOT_URL = map.ROOT_URL;
    ArrayList<String> stationName = new ArrayList<String>() ;
    String ID;
    int number;
    static ArrayList<Integer>  type = new ArrayList<Integer>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_info);
        String path = testroute.path ;
        boolean flag = true;


        while (flag){
        if (path.indexOf("|")!=-1){
        ID = path.substring(0, path.indexOf("|"));}
            else{
        ID = path ;
            flag = false;}

        number = Integer.parseInt(ID.charAt(0) + "");
            type.add(number);
        path = path.substring(path.indexOf("|") + 1);
            stationName();
        }
        int i =0;
       while ( !stationName.isEmpty()){

       }

    }

    private void stationName(){
        //Here we will handle the http request to retrieve Metro coordinates from mysql db

        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);
        //Defining the method PlotStation of our interface
        api.stationName(
                number,
                ID,
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
                            Log.d("stationName", output + "");

                            //stationName.add(output+"");


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occurred displaying the error as toast
                        Toast.makeText(routeInfo.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
}
