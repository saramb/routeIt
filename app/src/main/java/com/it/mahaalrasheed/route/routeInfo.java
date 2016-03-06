package com.it.mahaalrasheed.route;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class routeInfo extends AppCompatActivity {

    private static String ROOT_URL = map.ROOT_URL;

    public static String [] stationName = new String[100];
    public static Integer [] linenumber = new Integer[100];
    static String ID;
    static int number;
    static int count = 0;

    public static void startRouteInfo(){
        String path = testroute.path ;
        boolean flag = true;
        while (flag){
            if (path.indexOf("|")!=-1){
                ID = path.substring(0, path.indexOf("|"));}
            else{
                ID = path ;
                flag = false;}

            number = Integer.parseInt(ID.charAt(0) + "");
            path = path.substring(path.indexOf("|") + 1);
            stationName();
        }
    }

   static  public void stationName(){
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
                            stationName[count]= (output);
                            linenumber[count]= R.drawable.logo;

                            count ++;
                            /*int color = Integer.parseInt(ID.charAt(2)+"");
                            if (color == 1)
                                linenumber[count++]= R.drawable.logo;
                                if (color == 2)
                                    linenumber[count++]= R.drawable.logo;
                                    if (color == 3)
                                        linenumber[count++]= R.drawable.logo;
                                        if (color == 4)
                                            linenumber[count++]= R.drawable.logo;
                                            if (color == 5)
                                                linenumber[count++]= R.drawable.logo;
                                                if (color == 6)
                                                    linenumber[count++]= R.drawable.logo;*/


                            //  linenumber[count++]= ();
                            Log.d("stationName", output + "");

                            //stationName.add(output+"");


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occurred displaying the error ashow();

                    }
                }
        );
    }
}


