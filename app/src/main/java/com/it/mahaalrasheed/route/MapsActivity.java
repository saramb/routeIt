package com.it.mahaalrasheed.route;


import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.SeekBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static GoogleMap mMap;
    private static final LatLng MELBOURNE = new LatLng(24.895652,46.603078);

    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);

    private static final LatLng ADELAIDE = new LatLng(24.71347768,46.67521543);

    private static final LatLng PERTH = new LatLng(24.70334148,46.68034365);

    private static final LatLng LHR = new LatLng(51.471547, -0.460052);

    private static final LatLng LAX = new LatLng(33.936524, -118.377686);

    private static final LatLng JFK = new LatLng(40.641051, -73.777485);

    private static final LatLng AKL = new LatLng(-37.006254, 174.783018);

    private Polyline mMutablePolyline;

    private Polyline mClickablePolyline;
    ArrayList markerPoints;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        ///////////////////////////////////////////////////////ggggggggggggggggggggggggggggggggggggggggggggggggggggggg




        // Initializing
        markerPoints = new ArrayList();

// Getting reference to SupportMapFragment of the activity_main
        //SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

// Getting Map for the SupportMapFragment
        //ap = fm.getMap();

        //f(map!=null){

// Enable MyLocation Button in the Map
        //map.setMyLocationEnabled(true);

// Setting onclick event listener for the map

        /*

        mMap.setOnMapClickListener(new OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

// Already two locations
                if (markerPoints.size() > 1) {
                    markerPoints.clear();
                    mMap.clear();
                }

// Adding new item to the ArrayList
                markerPoints.add(point);

// Creating MarkerOptions
                MarkerOptions options = new MarkerOptions();

// Setting the position of the marker
                options.position(point);

/**
 * For the start location, the color of marker is GREEN and
 * for the end location, the color of marker is RED.
 *
 */

        /*
                if (markerPoints.size() == 1) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                } else if (markerPoints.size() == 2) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }

// Add new marker to the Google Map Android API V2
                mMap.addMarker(options);

// Checks, whether start and end locations are captured
                if (markerPoints.size() >= 2) {
                    LatLng origin = (LatLng) markerPoints.get(0);
                    LatLng dest = (LatLng) markerPoints.get(1);

// Getting URL to the Google Directions API
                    String url = getDirectionsUrl(origin, dest);
                    //String url = "htp://maps.google.com/maps?"+saddr+"=new+york&"+daddr+"=baltimore";

                    DownloadTask downloadTask = new DownloadTask();

// Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                }

            }
        });*/


        //LatLng origin = (LatLng) markerPoints.get(0);
        // LatLng dest = (LatLng) markerPoints.get(1);

// Getting URL to the Google Directions API
        String url = getDirectionsUrl(PERTH, ADELAIDE);
        //String url = "htp://maps.google.com/maps?"+saddr+"=new+york&"+daddr+"=baltimore";

        DownloadTask downloadTask = new DownloadTask();

// Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

////////////////////////////////////////////////////////////(basic)
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        // LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        // A geodesic polyline that goes around the world.
        mClickablePolyline = mMap.addPolyline((new PolylineOptions())
                .add(MELBOURNE, ADELAIDE, PERTH)
                .width(10)
                .color(Color.BLUE)
                .geodesic(true)
                .clickable(true));


        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(24.7117, 46.7242), 14.0f) );

        // Add a listener for polyline clicks that changes the clicked polyline's color.
        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                polyline.setColor(Color.GREEN);
            }
        });
    }



    private String getDirectionsUrl(LatLng origin,LatLng dest){

// Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

// Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

// Sensor enabled
        String sensor = "sensor=false";

// Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

// Output format
        String output = "json";

// Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    /** A method to download json data from url */
    public static String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

// Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

// Connecting to url
            urlConnection.connect();

// Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

}
////////////////////////////////

// Fetches data from url passed
class DownloadTask extends AsyncTask<String, Void, String>{

    // Downloading data in non-ui thread
    @Override
    protected String doInBackground(String... url) {

// For storing data from web service
        String data = "";

        try{
// Fetching the data from web service
            data = MapsActivity.downloadUrl(url[0]);
        }catch(Exception e){
            Log.d("Background Task",e.toString());
        }
        return data;
    }

    // Executes in UI thread, after the execution of
// doInBackground()
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        ParserTask parserTask = new ParserTask();

// Invokes the thread for parsing the JSON data
        parserTask.execute(result);

    }
}



