package com.it.mahaalrasheed.route;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LevelListDrawable;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;




public class map extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    public static final String ROOT_URL = "http://192.168.1.58/";
    //public static final String ROOT_URL = "http://rawan.16mb.com/tesst/";

    double latat=0, longt=0;
    public GoogleMap map;
//
    private ViewGroup infoWindow;
    private Button infoButton;
    private Button infoButton1;
    private Button infoButton2;

    private OnInfoWindowElemTouchListener infoButtonListener;
    private OnInfoWindowElemTouchListener infoButtonListener1;
    private OnInfoWindowElemTouchListener infoButtonListener2;

    private Polyline mMutablePolyline;
    private static Polyline mClickablePolyline;
    

    static GoogleMap googleMap;
    static double  Tolng,Fromlng,lng;
    static double Tolat, Fromlat,lat;
    Button from, to, show;
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

    public static String walk = "";

    Realm relam;
    FavoriteClass F;
    int id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.getLayoutParams().height = 0;


        lv = (ListView) findViewById(R.id.list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        page = "";
        lv = (ListView) findViewById(R.id.list);



        DisplayMap();
        RetrieveNotifID();
        testroute.link();

        //PlotStation();

        spots = new HashMap<>();

        from = (Button) findViewById(R.id.frombutton);
        to = (Button) findViewById(R.id.tobutton);

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(map.this, from.class);
                startActivity(intent);
            }
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(map.this, to.class);
                startActivity(intent);
            }
        });

        page = getIntent().getStringExtra("page");
        Locationname = getIntent().getStringExtra("name");
        Toast.makeText(map.this, page, Toast.LENGTH_LONG).show();

       Toast.makeText(map.this, page, Toast.LENGTH_LONG).show();
        from.setText(fromname);

        if (page != null) {
            if (page.equals("from")) {
                fromname = Locationname;
                Fromlng = getIntent().getDoubleExtra("lng", 0);
                Fromlat = getIntent().getDoubleExtra("lat", 0);
                from.setText(fromname);
            } else if (page.equals("to")) {
                from.setText(fromname);
                to.setText(Locationname);
                Tolng = getIntent().getDoubleExtra("lng", 0);
                Tolat = getIntent().getDoubleExtra("lat", 0);
                if (Fromlat == 0 || Fromlng == 0) {
                    from.setText("Current Location");
                    Fromlng = lng;
                    Fromlat = lat;}
                Log.d("test", Fromlat + "|" + Fromlng + "|" + Tolat + "|" + Tolng + "|");
                testroute.route(Fromlat, Fromlng, Tolat, Tolng);
                /*double sum = Double.parseDouble(testroute.distanceFrom) + Double.parseDouble(testroute.distanceTo);
                double time = 15*(sum/15);
                if (time > 30 )
                    walk = "You need a car to reach the first station";
                else
                    walk = "You need to walk "+time+" minutes to reach the first station";*/



                mViewPager.getLayoutParams().height = 300;
                //         PlotStation(testroute.lineCoorAstar);
               // PlotLine(testroute.lineCoorBFS);
                }
            }
        onMapReady(googleMap);
    }


    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
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
                    lat = location.getLatitude();
                    lng = location.getLongitude();
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


    }


    private void PlotStation(CameraPosition arg0){
        //Here we will handle the http request to retrieve Metro coordinates from mysql db
        map.clear(); //to clear all markers before
        if(latat !=0 && longt !=0){
        LatLng update = new LatLng(latat,longt);
        map.addMarker(new MarkerOptions().position(update));}


if(arg0.zoom>=14){
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

                            int i = 0;
                            while (!output.equals("")) {
                                String XCoordinates = output.substring(0, output.indexOf(":"));
                                String YCoordinates = output.substring(output.indexOf(":") + 1, output.indexOf("%"));
                                output = output.substring(output.indexOf("%") + 1);

                                lat = Double.parseDouble(XCoordinates);
                                lng = Double.parseDouble(YCoordinates);

                                SPOTS_ARRAY[i++] = new MetroStation(new LatLng(lat, lng));
                            }

                            for (int k = 0; k < i; k++) {
                                Marker marker = googleMap.addMarker(new MarkerOptions()
                                        .position(SPOTS_ARRAY[k].getPosition())
                                        .title("Title")
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_metro)));
                                spots.put(marker, SPOTS_ARRAY[k]);

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
        );}//end if
    }

    public static void PlotLine(ArrayList<LatLng> lineCoor) {
        //Here we will handle the http request to retrieve Metro coordinates from mysql db


        Log.v("lineCoorAstar", lineCoor + "");
        //Creating a RestAdapter

        try {

            for (int j = 0; j < lineCoor.size() - 1; j++) {
                int type1 = routeInfo.type.get(j);
                int type2 = routeInfo.type.get(j + 1);
                LatLng tempCoor1 = lineCoor.get(j);
                LatLng tempCoor2 = lineCoor.get(j + 1);
                Log.e("type", type1 + ":" + type2);
                Log.e("type", tempCoor1 + ":" + tempCoor2);
                if (type1 == 1 && type2 == 1) {
                    mClickablePolyline = googleMap.addPolyline((new PolylineOptions())
                            .add(tempCoor1, tempCoor2)
                            .width(10)
                            .color(Color.BLUE)
                            .geodesic(true));
                } else {
                    // Getting URL to the Google Directions API
                    String url = getDirectionsUrl(tempCoor1, tempCoor2);
                    DownloadTask downloadTask = new DownloadTask();
                    // Start downloading json data from Google Directions API
                    downloadTask.execute(url);//not metro point
                }//end of else

            }//end of for


        } catch (Exception e) {
            e.printStackTrace();
        }

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

        //noinspection SimplifiableIfStatement
        if ( item.getItemId() == R.id.notifi) { //if user press the notification icon on the menu bar, go to  activity
            Intent intent = new Intent (this, notif.class);
            intent.putExtra("content", notif+"");
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

    private void RetrieveNotif(int notifID) {
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


                         //   Toast.makeText(getApplicationContext(), output + "", Toast.LENGTH_LONG).show();

                            if (!output.equals("NULL")) {
                                //Check if there is an output from server
                                notif = output;
                            }
                             else if (output.equals("NULL")) {
                               myMenu.findItem(R.id.notifi).setEnabled(false);
                               myMenu.findItem(R.id.notifi).setIcon(R.drawable.no_notification_);
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
        if(itemNot.size() != 0)
            notifID = itemNot.get(0).getID();
            RetrieveNotif(notifID);
    }



    private static String getDirectionsUrl(LatLng origin, LatLng dest){

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
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(24.96215255,46.70097149);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10));



        final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);

        // MapWrapperLayout initialization
        // 39 - default marker height
        // 20 - offset between the default InfoWindow bottom edge and it's content bottom edge
        mapWrapperLayout.init(map, getPixelsFromDp(this, 39 + 20));

        // We want to reuse the info window for all the markers,
        // so let's create only one class member instance
        this.infoWindow = (ViewGroup)getLayoutInflater().inflate(R.layout.activity_map_wrapper_layout, null);
        this.infoButton = (Button)infoWindow.findViewById(R.id.button);
        this.infoButton1 = (Button)infoWindow.findViewById(R.id.button2);
        this.infoButton2 = (Button)infoWindow.findViewById(R.id.button3);

        // Setting custom OnTouchListener which deals with the pressed state
        // so it shows up
        this.infoButtonListener = new OnInfoWindowElemTouchListener(infoButton,
                getResources().getDrawable(R.drawable.cast_ic_notification_1),
                getResources().getDrawable(R.drawable.cast_ic_notification_2))
        {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button
                Fromlat= latat;
                Fromlng = longt;
                from.setText(latat + "," + longt);
                Toast.makeText(getApplicationContext(), "from", Toast.LENGTH_SHORT).show();
            }
        };

        this.infoButtonListener1 = new OnInfoWindowElemTouchListener(infoButton1,
                getResources().getDrawable(R.drawable.cast_ic_notification_1),
                getResources().getDrawable(R.drawable.cast_ic_notification_2))
        {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button
                Tolat= latat;
                Tolng = longt;
                to.setText(latat + "," + longt);
                mViewPager.getLayoutParams().height = 300;
                testroute.route(Fromlat, Fromlng, Tolat, Tolng);
                Toast.makeText(getApplicationContext(),"to", Toast.LENGTH_SHORT).show();
            }
        };

        this.infoButtonListener2 = new OnInfoWindowElemTouchListener(infoButton2,
                getResources().getDrawable(R.drawable.cast_ic_notification_1),
                getResources().getDrawable(R.drawable.cast_ic_notification_2))
        {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {

                F = new FavoriteClass();
                F.setId((Favorites.id)++);
                F.setLat(latat);
                F.setLng(longt);
                F.setName(latat+","+longt);

                relam = Realm.getInstance(getApplicationContext());
                relam.beginTransaction();
                relam.copyToRealmOrUpdate(F);
                relam.commitTransaction();
                // Here we can perform some action triggered after clicking the button
                Toast.makeText(getApplicationContext(),"fav.", Toast.LENGTH_SHORT).show();
            }
        };
        this.infoButton.setOnTouchListener(infoButtonListener);
        this.infoButton1.setOnTouchListener(infoButtonListener1);
        this.infoButton2.setOnTouchListener(infoButtonListener2);


        map.setInfoWindowAdapter(new InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Setting up the infoWindow with current's marker info

                infoButtonListener.setMarker(marker);
                infoButtonListener1.setMarker(marker);
                infoButtonListener2.setMarker(marker);
                //infoButtonListener1.setMarker(marker);

                // We must call this to set the current marker and infoWindow references
                // to the MapWrapperLayout
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                return infoWindow;
            }
        });

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                map.clear(); //to clear all markers before
                map.addMarker(new MarkerOptions().position(latLng));
                latat = latLng.latitude;
                longt = latLng.longitude;

                Toast.makeText(getApplicationContext(), latat + "  and " + longt, Toast.LENGTH_SHORT).show();

            }
        });  //end on click


        final CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(24.96215255,46.70097149)).zoom(15).build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(CameraPosition arg0) {

                PlotStation(arg0);
            }
        });
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