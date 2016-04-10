package com.it.mahaalrasheed.route;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
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
    //test commit
    public static final String ROOT_URL = "http://192.168.100.16/";

    //public static final String ROOT_URL = "http://rawan.16mb.com/tesst/";

    double latat = 0, longt = 0;
    public GoogleMap map;

    private ViewGroup infoWindow;
    private Button infoButton;
    private Button infoButton1;
    private Button infoButton2;

    static RelativeLayout frag;

    private OnInfoWindowElemTouchListener infoButtonListener;
    private OnInfoWindowElemTouchListener infoButtonListener1;
    private OnInfoWindowElemTouchListener infoButtonListener2;

    static ArrayList<Polyline> polylines = new ArrayList<Polyline>();

    static GoogleMap googleMap;
    static double Tolng, Fromlng, lng;
    static double Tolat, Fromlat, lat;
    static Button from, to;
    Location location;
    MapFragment fm;
    LocationManager locationManager;
    String provider;
    static int notifID;
    String notif = "";
    Realm realm;
    Menu myMenu;
    private Map<Marker, MetroStation> spots = new HashMap<>();
    ;
    private static MetroStation[] SPOTS_ARRAY;
    private BottomSheetBehavior mBottomSheetBehavior;
    ListView lv;
    String Locationname, page = "";
    public static String fromname = "From";

    Realm relam;
    FavoriteClass F;
    int id;
    static Marker marker, line;
    static Marker m;
    static LatLng riyadh = new LatLng(24.713552, 46.675296);


    ImageButton left, right;
    static int swiping = 1;
    static TextView section_label;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //premission

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                lng = location.getLongitude();
                lat = location.getLatitude();
            }
        };

        //end permission

        frag = (RelativeLayout) findViewById(R.id.frag);
        left = (ImageButton) findViewById(R.id.swipeleft);
        right = (ImageButton) findViewById(R.id.swiperight);
        section_label = (TextView) findViewById(R.id.section_label);

        frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(map.this, path_Info.class);
                startActivity(intent);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        lv = (ListView) findViewById(R.id.list);
        from = (Button) findViewById(R.id.frombutton);
        to = (Button) findViewById(R.id.tobutton);


        DisplayMap();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(riyadh, 10));

        //RetrieveNotifID();
        RetrieveNotifID();


        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                section_label.setText("right");
                swiping++;
                if (swiping == 4)
                    swiping = 1;

                testroute.route(Fromlat, Fromlng, Tolat, Tolng, swiping);


            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                section_label.setText("left");
                swiping--;
                if (swiping == -1)
                    swiping = 3;


                testroute.route(Fromlat, Fromlng, Tolat, Tolng, swiping);


            }
        });

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
                    Fromlat = lat;
                }

                test();
            }

        }


        // draw marker when clicked on specific favorite location
        if (Favorites.latFav != 0) {
            latat = Favorites.latFav;
            longt = Favorites.lngFav;
            m = googleMap.addMarker(new MarkerOptions().position(new LatLng(Favorites.latFav, Favorites.lngFav)));
        }
        onMapReady(googleMap);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public void test() {
        frag.getLayoutParams().height = 300;
        swiping = 1;
        section_label.setText("AStar");
        testroute.route(Fromlat, Fromlng, Tolat, Tolng, 1);
    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public void DisplayMap() {

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


        } //!!!!!!!!!!Map part end

    }


    private void PlotStation() {
        //Here we will handle the http request to retrieve Metro coordinates from mysql db
        map.clear(); //to clear all markers before
        if (latat != 0 && longt != 0) {
            LatLng update = new LatLng(latat, longt);
            map.addMarker(new MarkerOptions().position(update));
        }


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
                                double lat = Double.parseDouble(XCoordinates);
                                double lng = Double.parseDouble(YCoordinates);
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
        );
    }

    public static void PlotLine(ArrayList<LatLng> lineCoor, ArrayList<Integer> type) {
        //Here we will handle the http request to retrieve Metro coordinates from mysql db
        //to zoom the camera to the starting point
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Fromlat, Fromlng), 13));

        try {

            if (!polylines.isEmpty()) {
                for (Polyline p : polylines)
                    p.remove();

            }


            for (int j = 0; j < lineCoor.size() - 1; j++) {
                int type1 = type.get(j);
                int type2 = type.get(j + 1);
                LatLng tempCoor1 = lineCoor.get(j);
                LatLng tempCoor2 = lineCoor.get(j + 1);
                Log.e("type", type1 + ":" + type2);
                Log.e("type", tempCoor1 + ":" + tempCoor2);


                if (type1 == 1 && type2 == 1) {

                    polylines.add(googleMap.addPolyline((new PolylineOptions())
                            .add(tempCoor1, tempCoor2)
                            .width(10)
                            .color(Color.BLUE)
                            .geodesic(true)));
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map, menu);
        myMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the notification button

        //noinspection SimplifiableIfStatement
        if (item.getItemId() == R.id.notifi) { //if user press the notification icon on the menu bar, go to  activity
            Intent intent = new Intent(this, notif.class);
            intent.putExtra("content", notif + "");
            startActivityForResult(intent, 1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check if the request code is same as what is passed  here it is 1
        if (requestCode == 1) {
            //If the result is returned correctly
            if (resultCode == Activity.RESULT_OK) {
                String message = data.getStringExtra("id");
                notifID = Integer.parseInt(message + "");

                Notification n = new Notification();
                n.setID(notifID);
                n.setPk(0);
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
            Intent intent = new Intent(this, map.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {  //favorites
            Intent intent = new Intent(this, Favorites.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {  //about us
            Intent intent = new Intent(this, aboutusnav.class);
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


                            Toast.makeText(getApplicationContext(), output + "", Toast.LENGTH_LONG).show();
                            if (output.length() != 1) {
                                //Check if there is an output from server
                                notif = output;
                            } else if (output.length() == 1) {
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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "map Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.it.mahaalrasheed.route/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    public void RetrieveNotifID() {
        //retrieve realm content
        realm = Realm.getInstance(getApplicationContext());
        List<Notification> itemNot = realm.allObjects(Notification.class);

        //store all returned content from realm
        if (itemNot.size() != 0)
            notifID = itemNot.get(0).getID();
        RetrieveNotif(notifID);

    }


    private static String getDirectionsUrl(LatLng origin, LatLng dest) {

// Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

// Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

// Sensor enabled
        String sensor = "sensor=false";

// Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

// Output format
        String output = "json";

// Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    private final int SPLASH_DISPLAY_LENGTH = 1200;
    static int arraySize[] = new int[30];

    public static void numOfStation() {
        //Here we will handle the http request to retrieve from mysql db
        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);

        //Defining the method  RetrieveNotif of our interface
        api.numOfStation(

                //Passing the values
                "1",
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
                            Log.d("output", "output[" + "]" + ":" + output);

                            //Toast.makeText(getApplicationContext(), output + "", Toast.LENGTH_LONG).show();
                            int i = 0;
                            //Check if there is an output from server
                            while (!output.equals("")) {
                                arraySize[i] = Integer.parseInt(output.substring(0, output.indexOf("/"))) + 1;
                                Log.d("arraySize", "arraySize[" + i + "]" + ":" + arraySize[i]);
                                i++;
                                output = output.substring(output.indexOf("/") + 1);
                            }
                            testroute.Mline1 = new String[arraySize[0]][arraySize[0]];
                            testroute.Mline2 = new String[arraySize[1]][arraySize[1]];
                            testroute.Mline3 = new String[arraySize[2]][arraySize[2]];
                            testroute.Mline4 = new String[arraySize[3]][arraySize[3]];
                            testroute.Mline5 = new String[arraySize[4]][arraySize[4]];
                            testroute.Mline6 = new String[arraySize[5]][arraySize[5]];
                            testroute.Bline2_1 = new String[arraySize[6]][arraySize[6]];
                            testroute.Bline2_2 = new String[arraySize[7]][arraySize[7]];
                            testroute.Bline2_3 = new String[arraySize[8]][arraySize[8]];
                            testroute.Bline2_4 = new String[arraySize[9]][arraySize[9]];
                            testroute.Bline2_5 = new String[arraySize[10]][arraySize[10]];
                            testroute.Bline2_6 = new String[arraySize[11]][arraySize[11]];
                            testroute.Bline2_7 = new String[arraySize[12]][arraySize[12]];
                            testroute.Bline2_8 = new String[arraySize[13]][arraySize[13]];
                            testroute.Bline2_9 = new String[arraySize[14]][arraySize[14]];
                            testroute.Bline2_10 = new String[arraySize[15]][arraySize[15]];
                            testroute.Bline3_1 = new String[arraySize[16]][arraySize[16]];
                            testroute.Bline3_2 = new String[arraySize[17]][arraySize[17]];
                            testroute.Bline3_3 = new String[arraySize[18]][arraySize[18]];
                            testroute.Bline3_4 = new String[arraySize[19]][arraySize[19]];
                            testroute.Bline3_5 = new String[arraySize[20]][arraySize[20]];
                            testroute.Bline3_6 = new String[arraySize[21]][arraySize[21]];
                            testroute.Bline3_7 = new String[arraySize[22]][arraySize[22]];
                            testroute.Bline3_8 = new String[arraySize[23]][arraySize[23]];
                            testroute.Bline3_9 = new String[arraySize[24]][arraySize[24]];
                            testroute.Bline3_10 = new String[arraySize[25]][arraySize[25]];
                            testroute.Bline4_1 = new String[arraySize[26]][arraySize[26]];
                            testroute.Bline4_2 = new String[arraySize[27]][arraySize[27]];
                            testroute.link();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        // Toast.makeText(map.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    /**
     * A method to download json data from url
     */
    public static String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;

        try {

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
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;



        final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout) findViewById(R.id.map_relative_layout);

        // MapWrapperLayout initialization
        // 39 - default marker height
        // 20 - offset between the default InfoWindow bottom edge and it's content bottom edge
        mapWrapperLayout.init(map, getPixelsFromDp(this, 39 + 20));

        // We want to reuse the info window for all the markers,
        // so let's create only one class member instance
        this.infoWindow = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_map_wrapper_layout, null);
        this.infoButton = (Button) infoWindow.findViewById(R.id.button);
        this.infoButton1 = (Button) infoWindow.findViewById(R.id.button2);
        this.infoButton2 = (Button) infoWindow.findViewById(R.id.button3);

        // Setting custom OnTouchListener which deals with the pressed state
        // so it shows up
        this.infoButtonListener = new OnInfoWindowElemTouchListener(infoButton,
                getResources().getDrawable(R.drawable.cast_ic_notification_1),
                getResources().getDrawable(R.drawable.cast_ic_notification_2)) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button
                if (m != null)
                    m.remove();

                Fromlat = latat;
                Fromlng = longt;
                if (Favorites.nameFav.equals(""))
                    from.setText(latat + "," + longt);
                else
                    from.setText(Favorites.nameFav + "");

                marker.remove();

                Toast.makeText(getApplicationContext(), "from", Toast.LENGTH_SHORT).show();
            }
        };

        this.infoButtonListener1 = new OnInfoWindowElemTouchListener(infoButton1,
                getResources().getDrawable(R.drawable.cast_ic_notification_1),
                getResources().getDrawable(R.drawable.cast_ic_notification_2)) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button
                if (m != null)
                    m.remove();
                Tolat = latat;
                Tolng = longt;
                if (Favorites.nameFav.equals(""))
                    to.setText(latat + "," + longt);
                else
                    to.setText(Favorites.nameFav + "");
                Toast.makeText(getApplicationContext(), "to", Toast.LENGTH_SHORT).show();
                marker.remove();

                test();
            }
        };

        this.infoButtonListener2 = new OnInfoWindowElemTouchListener(infoButton2,
                getResources().getDrawable(R.drawable.cast_ic_notification_1),
                getResources().getDrawable(R.drawable.cast_ic_notification_2)) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {

                F = new FavoriteClass();
                F.setId((Favorites.id)++);
                F.setLat(latat);
                F.setLng(longt);
                F.setName(latat + "," + longt);

                relam = Realm.getInstance(getApplicationContext());
                relam.beginTransaction();
                relam.copyToRealmOrUpdate(F);
                relam.commitTransaction();
                if (m != null)
                    m.remove();
                // Here we can perform some action triggered after clicking the button
                Toast.makeText(getApplicationContext(), "fav.", Toast.LENGTH_SHORT).show();
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
                if (marker != null)
                    marker.remove();
                marker = map.addMarker(new MarkerOptions().position(latLng));

                latat = latLng.latitude;
                longt = latLng.longitude;

            }
        });  //end on click

        //PlotStation();


    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "map Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.it.mahaalrasheed.route/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
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