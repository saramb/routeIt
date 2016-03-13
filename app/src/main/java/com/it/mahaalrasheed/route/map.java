package com.it.mahaalrasheed.route;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class map extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener{

    public static final String ROOT_URL = "http://192.168.1.63/";
    //public static final String ROOT_URL = "http://rawan.16mb.com/tesst/";


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




    static GoogleMap googleMap;
    double lng;
    double lat;
    Button from, to;
    Location location;
    MapFragment fm;
    LocationManager locationManager;
    String provider;
    static int notifID;
    String notif="";
    Realm realm;
    Notification notification;
    Menu myMenu;
    private Map<Marker, MetroStation> spots;
    private static MetroStation[] SPOTS_ARRAY;
    private BottomSheetBehavior mBottomSheetBehavior;
    ListView lv;
    String Locationname , page="";
    public static String fromname = "From";

    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.getLayoutParams().height = 0;




        lv = (ListView)findViewById(R.id.list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        page="";
        lv = (ListView)findViewById(R.id.list);


       /* testroute.lineCoor.add(MELBOURNE);
        testroute.lineCoor.add(ADELAIDE);
        testroute.lineCoor.add(PERTH);*/


        DisplayMap();
        testroute.link();

        PlotStation();
       // RetrieveNotifID();

        spots = new HashMap<>();

        from = (Button) findViewById(R.id.frombutton);
        to = (Button) findViewById(R.id.tobutton);

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent (map.this, from.class);
                startActivity(intent);}
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(map.this, to.class);
                startActivity(intent);}
        });

        page = getIntent().getStringExtra("page");
        lng = getIntent().getDoubleExtra("lng", 0);
        lat = getIntent().getDoubleExtra("lat", 0);
        Locationname = getIntent().getStringExtra("name");

        Toast.makeText(map.this,page,Toast.LENGTH_LONG).show();
        from.setText(fromname);

        if ( page  != null){
            if (page.equals("from")) {
                fromname = Locationname;
                from.setText(fromname);
            } else if (page.equals("to")) {
                to.setText(Locationname);
                Intent intent = new Intent(map.this, info.class);
                startActivity(intent);
                testroute.route(24.84148388, 46.71737999, 24.96215255, 46.70097149);


                mViewPager.getLayoutParams().height = 250;
            }
        }


    }
    public void DisplayMap(){

        //!!!!!!!!!!Map part start
        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        } else { // Google Play Services are available

            // Getting reference to the SupportMapFragment of activity_main.xml
            fm = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment);

            // Getting GoogleMap object from the fragment
            googleMap = fm.getMap();

            // Enabling MyLocation Layer of Google Map
            //googleMap.setMyLocationEnabled(true);

            // Getting LocationManager object from System Service LOCATION_SERVICE
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getiting the name of the best provider
            provider = locationManager.getBestProvider(criteria, true);


            // Getting Current Location
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            location = locationManager.getLastKnownLocation(provider);

            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    // redraw the marker when get location update.
                    //drawMarker(location);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };

            if (location != null) {
                //PLACE THE INITIAL MARKER
                drawMarker(location.getLatitude(),location.getLongitude());
            }
            locationManager.requestLocationUpdates(provider, 0, 0, locationListener);

        } //!!!!!!!!!!Map part end
    }

    private void drawMarker(double latitude, double longitude) {
        googleMap.clear();
        lat= location.getLatitude();
        lng= location.getLongitude();
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .snippet("Lat:" + location.getLatitude() + "Lng:" + location.getLongitude()));
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 16));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(24.69812133, 46.71793858), 16.0f));



    }


    private void PlotStation(){
        //Here we will handle the http request to retrieve Metro coordinates from mysql db

        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);
        //Defining the method PlotStation of our interface
        api.PlotStation(
                "1",
                //Creating an anonymous callback
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        //On success we will read the server's output using buffered reader
                        //Creating a buffered reader object
                        BufferedReader reader = null;

                        //An string to store output from the server
                        String output = "";
                        String output1 = "";

                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();
                            output1 = output.substring(0, output.indexOf("/"));
                            output = output.substring(output.indexOf("/") + 1);
                            SPOTS_ARRAY = new MetroStation[Integer.parseInt(output1)];

             for(int j = 0 ; j < testroute.lineCoor.size()-1 ; j++) {
                            int type1 =  routeInfo.type.get(j);
                            int type2 = routeInfo.type.get(j+1);
                            LatLng   tempCoor1 = testroute.lineCoor.get(j);
                            LatLng   tempCoor2 = testroute.lineCoor.get(j+1);
                            Log.e("type",type1+":"+type2);
                 Log.e("type",tempCoor1+":"+tempCoor2);
                           if(type1 ==1 && type2 ==1 ){
                            mClickablePolyline = googleMap.addPolyline((new PolylineOptions())
                                    .add(tempCoor1, tempCoor2)
                                    .width(10)
                                    .color(Color.BLUE)
                                    .geodesic(true));
                           }

                            else{
                                        // Getting URL to the Google Directions API
                                        String url = getDirectionsUrl(tempCoor1, tempCoor2);
                                        DownloadTask downloadTask = new DownloadTask();
                                        // Start downloading json data from Google Directions API
                                        downloadTask.execute(url);//not metro point
                                     }//end of else

                        }//end of for

                            int i = 0;
                            while (!output.equals("")) {
                                String XCoordinates = output.substring(0, output.indexOf(":"));
                                String YCoordinates = output.substring(output.indexOf(":") + 1, output.indexOf("%"));
                                output = output.substring(output.indexOf("%") + 1);

                                lat = Double.parseDouble(XCoordinates);
                                lng = Double.parseDouble(YCoordinates);

                              //  SPOTS_ARRAY[i++] = new MetroStation(new LatLng(lat, lng));
                            }

                            for (int k = 0; k < i; k++) {
                                Marker marker = googleMap.addMarker(new MarkerOptions()
                                        .position(SPOTS_ARRAY[k].getPosition())
                                        .title("Title")
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_metro)));
                                //spots.put(marker, SPOTS_ARRAY[k]);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occurred displaying the error as toast
                        Toast.makeText(map.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.map, menu);
        myMenu=menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the notification button
        int id1=item.getItemId();
        String s=R.id.notifi+"";
        //noinspection SimplifiableIfStatement
        if ( item.getItemId() == R.id.notifi) { //if user press the notification icon on the menu bar, go to  activity
            Intent intent = new Intent (this, notif.class);
            intent.putExtra("content", notif);
            startActivityForResult(intent, 1);
           return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check if the request code is same as what is passed  here it is 1
        if(requestCode==1)
        {
            //If the result is returned correctly
            if(resultCode== Activity.RESULT_OK){
            String message=data.getStringExtra("id");
            notifID=Integer.parseInt( message+"");

                Notification n=new Notification();
                n.setID(notifID);n.setPk(0);
                Realm realm = Realm.getInstance(getApplicationContext());
                //Update realm object with the new notification id
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(n);
                realm.commitTransaction();
                myMenu.findItem(R.id.notifi).setEnabled(false);
                myMenu.findItem(R.id.notifi).setIcon(R.drawable.no_notification_);

            }

        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) { //map
            // Handle the map action
           Intent intent = new Intent (this, map.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {  //favorites
           Intent intent = new Intent (this, Favorites.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) { //login
            Intent intent = new Intent (this, loginnav.class);
            startActivity(intent);


        } else if (id == R.id.nav_manage) {  //about us
            Intent intent = new Intent (this, aboutusnav.class);
            startActivity(intent);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void RetrieveNotif() {
        //Here we will handle the http request to retrieve from mysql db
        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);

        //Defining the method  RetrieveNotif of our interface
        api.RetrieveNotif(

                //Passing the values
                notifID,
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

                            //Check if there is an output from server
                            if (!output.equals("") && !output.equals("NULL")) {
//                                myMenu.findItem(R.id.notifi).setEnabled(true);
                                //                              myMenu.findItem(R.id.notifi).setIcon(R.drawable.no_notification);
                                notif = output;

                            } else if (output.equals("NULL")) {
                                //   myMenu.findItem(R.id.notifi).setEnabled(false);
                                //  myMenu.findItem(R.id.notifi).setIcon(R.drawable.no_notification_);

                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(map.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void RetrieveNotifID(){
        //retrieve realm content
        realm = Realm.getInstance(getApplicationContext());
        List<Notification> itemNot =realm.allObjects(Notification.class);

        //store all returned content from realm
        int[] titles=new int[itemNot.size()];
        int l=-1;
        for(int i=0; i<itemNot.size();i++){
            titles[i]=itemNot.get(i).getID();
            l=i;
        }
          if(l!=-1)
              notifID=titles[l];

        RetrieveNotif();


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

 class DownloadTask extends AsyncTask<String, Void, String> {

    // Downloading data in non-ui thread
    @Override
    protected String doInBackground(String... url) {

// For storing data from web service
        String data = "";

        try{
// Fetching the data from web service
            data = map.downloadUrl(url[0]);
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




