package com.it.mahaalrasheed.route;

import android.graphics.Color;

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

    public static ArrayList<String>stationName = new ArrayList<String>();
    public static ArrayList<Integer> linenumber = new ArrayList<Integer>();
    public static ArrayList<Integer> Number = new ArrayList<Integer>();

    static String name;
    static int line;
    static  int number;
    public static int count = 0;
    static ArrayList<Integer>  type = new ArrayList<Integer>() ;
    static ArrayList<LatLng> linecoor;

    public static void startRouteInfo(String Path , ArrayList<LatLng> Coorline){
        linecoor = Coorline;
        stationName(Path);
    }

     static public void stationName(String path){
        //Creating a RestAdapter
        final RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);

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
                        String path ="";
                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
                            //Reading the output in the string
                            path = reader.readLine();

                            stationName = new ArrayList<String>();
                            linenumber = new ArrayList<Integer>();
                            Number = new ArrayList<Integer>();

                            while (path.length() != 0) {

                                int LineNumber = Integer.parseInt(path.substring(0, path.indexOf(":")));
                                type.add(LineNumber);
                                Number.add(LineNumber);
                                path = path.substring(path.indexOf(":") + 1);
                                line = Integer.parseInt(path.substring(0, path.indexOf(":")));
                                linenumber.add(line);
                                path = path.substring(path.indexOf(":") + 1);
                                name = path.substring(0, path.indexOf("|"));
                                stationName.add(name);
                                path = path.substring(path.indexOf("|") + 1);
                                count++;
                            }
                            number = count;
                            for( int i =0;i<number;i++){
                                map.itemname[i]=stationName.get(i);

                                if (routeInfo.Number.get(i)==1){
                                    map.imgid[i]= R.mipmap.metro;

                                }
                                else
                                {  map.imgid[i]= R.mipmap.busicon;}
                                int line = routeInfo.linenumber.get(i);
                                int color = 0;

                                switch(line){
                                    case 1:
                                        if (routeInfo.Number.get(i)==1){
                                            color = Color.parseColor("#0000FF"); //blue
                                        }
                                        else
                                        {
                                            color = Color.parseColor("#0000FF"); //blue
                                        }                break;
                                    case 2:
                                        if (routeInfo.Number.get(i)==1){
                                            color = Color.parseColor("#3F9415"); //green
                                        }
                                        else
                                        {  color = Color.parseColor("#3F9415"); //green
                                        }
                                        break;
                                    case 3:
                                        if (routeInfo.Number.get(i)==1){
                                            color = Color.parseColor("#FF8000"); //orange
                                        }
                                        else
                                        {  color = Color.parseColor("#942A15"); //red
                                        }
                                        break;
                                    case 4:
                                        if (routeInfo.Number.get(i)==1){
                                            color = Color.parseColor("#9933FF"); //purple
                                        }
                                        else
                                        {  color = Color.parseColor("#F2F274"); //yellow
                                        }
                                        break;
                                    case 5:
                                        color = Color.parseColor("#942A15"); //red
                                        break;
                                    case 6:
                                        color = Color.parseColor("#F2F274"); //yellow
                                        break;
                                }//end of switch


                                if(i == 0 ) {
                                    map.img1.setImageResource(map.imgid[i]);
                                    map.img1.setColorFilter(color);
                                }
                                else
                                if(i == 1 ) {
                                    map.img2.setImageResource(map.imgid[i]);
                                    map.img2.setColorFilter(color);
                                }
                                else
                                if(i == 2 ) {
                                    map.img3.setImageResource(map.imgid[i]);
                                    map.img3.setColorFilter(color);
                                }
                                else
                                if(i == 3 ) {
                                    map.img4.setImageResource(map.imgid[i]);
                                    map.img4.setColorFilter(color);
                                }
                                else
                                if(i == 4 ) {
                                    map.img5.setImageResource(map.imgid[i]);
                                    map.img5.setColorFilter(color);
                                }
                                else
                                if(i == 5 ) {
                                    map.img6.setImageResource(map.imgid[i]);
                                    map.img6.setColorFilter(color);
                                }
                                else
                                if(i == 6 ) {
                                    map.img7.setImageResource(map.imgid[i]);
                                    map.img7.setColorFilter(color);
                                }

                            }

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
