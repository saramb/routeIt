package com.it.mahaalrasheed.route;



import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> > {
    static String durat="0";
    public static double heu=0;
    static ArrayList<Polyline> polylines = new ArrayList<Polyline>();
    // Parsing the data in non-ui thread
    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

        JSONObject jObject;
        List<List<HashMap<String, String>>> routes = null;

        try{
            jObject = new JSONObject(jsonData[0]);
            DirectionsJSONParser parser = new DirectionsJSONParser();

// Starts parsing data
            routes = parser.parse(jObject);
        }catch(Exception e){
            e.printStackTrace();
        }
        return routes;
    }

    // Executes in UI thread, after the parsing process
    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> result) {

        ArrayList points = null;
        PolylineOptions lineOptions = null;
        MarkerOptions markerOptions = new MarkerOptions();
        if (!polylines.isEmpty()) {
            for (Polyline p : polylines)
                p.remove();

        }

// Traversing through all the routes
        for(int i=0;i<result.size();i++){
            points = new ArrayList();
            lineOptions = new PolylineOptions();

// Fetching i-th route
            List<HashMap<String, String>> path = result.get(i);

// Fetching all the points in i-th route
            for(int j=0;j <path.size();j++){
                HashMap<String,String> point = path.get(j);

                if(j==0){ // Get duration from the list

                   // if(map.DurFlag==true){
                      //  Log.d("durationparse:", (String)point.get("duration")+ "");
                      //  Algorithm.duration += (String)point.get("duration");
                        durat = (String)point.get("duration");
                        Log.d("point :", path.get(1) + "" + path.get(path.size() - 1));
                    heu=Double.parseDouble(durat.substring(0,durat.indexOf(" ")));
                        Log.d("heu", heu + "");
                    //Algorithm.Astar(testroute.fromId, testroute.toId, Double.parseDouble(testroute.fromCoorX), Double.parseDouble(testroute.fromCoorY), Double.parseDouble(testroute.toCoorX), Double.parseDouble(testroute.toCoorY));
                    map.DurFlag=true;
                    //delegate.processFinish(durat);

                    return;//
                    // }

                   // continue;
                }
                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
            }


          /*  if(map.DurFlag==false) {
// Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(8);
                lineOptions.color(Color.RED);
            }*/
            lineOptions.addAll(points);
            lineOptions.width(8);
            lineOptions.color(Color.RED);

        }

if(map.DurFlag==false&&lineOptions!=null){
// Drawing polyline in the Google Map for the i-th route
        polylines.add( map.map.addPolyline(lineOptions));
    }
}}