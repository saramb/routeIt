package com.it.mahaalrasheed.route;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class routeInfo {


    private static String ROOT_URL = map.ROOT_URL;

    public static String [] stationName = new String[100];
    public static int [] linenumber = new int[100];
    public static int [] Number = new int[100];

    static String ID;
    static int number;
    public static int count = 0;
    static ArrayList<Integer>  type = new ArrayList<Integer>() ;

    public static void startRouteInfo(String Path){


        String path = Path ;
        boolean flag = true;

        while (flag){
            if (path.indexOf("|")!=-1){
                ID = path.substring(0, path.indexOf("|"));}
            else{
                ID = path ;
                flag = false;}
            number = Integer.parseInt(ID.charAt(0) + "");
            path = path.substring(path.indexOf("|") + 1);
            type.add(number);
            stationName();}

        Log.d("path", count+"");

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

                            Log.d("count", count+"");
                            stationName[count]=output+"";
                            Number[count]=number;
                            Log.d("path", stationName[count]);
                            linenumber[count++]= Integer.parseInt (ID.charAt(2)+"");


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occurred displaying the error as toast

                    }
                }
        );
    }
}
