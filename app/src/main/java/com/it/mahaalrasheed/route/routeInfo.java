package com.it.mahaalrasheed.route;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

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

    public static String [] stationName = new String[1000];
    public static int [] linenumber = new int[1000];
    public static int [] Number = new int[1000];

    static String name;
    static int line;
    static  int number;
    public static int count = 0;
    static ArrayList<Integer>  type = new ArrayList<Integer>() ;
    static ArrayList<LatLng> linecoor;

    public static ArrayList<Integer> startRouteInfo(String Path , ArrayList<LatLng> Coorline){
        linecoor = Coorline;
        stationName(Path);
        return type;
    }

    static  public void stationName(String path){

        //Creating a RestAdapter
        final RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);

        final String Path = path;
        //Defining the method PlotStation of our interface
        api.stationName(
                path,
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
                            String path = output ;
                            while (path.length()!=0){
                                Log.d("rout", path);
                                type.add(Integer.parseInt(path.substring(0, path.indexOf(":"))));
                                System.out.print(type.get(0) + "****************");
                                Number[count ]=  Integer.parseInt(path.substring(0, path.indexOf(":")));
                                path = path.substring(path.indexOf(":") + 1);
                                line = Integer.parseInt(path.substring(0, path.indexOf(":")));
                                linenumber [count]= line;
                                path = path.substring(path.indexOf(":") + 1);
                                name = path.substring(0, path.indexOf("|"));
                                stationName[count++] = name;

                                path = path.substring(path.indexOf("|") + 1);
                            }
                            number = count;
                            map.PlotLine(linecoor,routeInfo.type);
                            count = 0;
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
