package com.it.mahaalrasheed.route;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {


    //test commit
    public static final String ROOT_URL = "http://192.168.100.14/";
    //public static final String ROOT_URL = "http://rawan.16mb.com/tesst/";


    private Button infoButton;
    private Button infoButton1;
    private Button infoButton2;
    static Button from, to;
    ListView lv;
    static TextView section_label,duration;
    static ImageView img1;
    static ImageView img2;
    static ImageView img3;
    static ImageView img4;
    static ImageView img5;
    static ImageView img6;
    static ImageView img7,next1,next2 ,next3 ,next4,next5 ,next6 ;
    ImageButton left, right;
    static boolean DurFlag;


    static RelativeLayout frag;

    private OnInfoWindowElemTouchListener infoButtonListener;
    private OnInfoWindowElemTouchListener infoButtonListener1;
    private OnInfoWindowElemTouchListener infoButtonListener2;

    static ArrayList<Polyline> polylines = new ArrayList<Polyline>();
    static GoogleMap map;
    private ViewGroup infoWindow;
    static double Tolng, Fromlng, lng,latat;
    static double Tolat, Fromlat, lat,longt;
    Location location;
    MapFragment fm;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    String provider,notif,Locationname, page;
    static int notifID;
    public static String fromname = "From";
    public static String toname = "To";
    static Marker m;

    Menu myMenu;

    private Map<Marker, MetroStation> spots = new HashMap<>();
    private static MetroStation[] SPOTS_ARRAY;

    Realm relam;
    FavoriteClass F;
    int id;
    static int swiping = 1;

    public static String[] itemname = new String[100];
    public static Integer[] imgid = new Integer[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        Boolean isInternetPresent = cd.isConnectingToInternet(); // true or false

        if (!isInternetPresent)
            showInternetDisabledAlertToUser();

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            showGPSDisabledAlertToUser();

        DisplayMap();
        buildGoogleApiClient();
        testroute.RetrieveSchedule();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        //premission

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
        frag = (RelativeLayout) findViewById(R.id.frag);
        left = (ImageButton) findViewById(R.id.swipeleft);
        right = (ImageButton) findViewById(R.id.swiperight);
        section_label = (TextView) findViewById(R.id.section_label);
        duration = (TextView) findViewById(R.id.duration);
        img1 = (ImageView) findViewById(R.id.imageView9);
        img2 = (ImageView) findViewById(R.id.imageView8);
        img3 = (ImageView) findViewById(R.id.imageView7);
        img4 = (ImageView) findViewById(R.id.imageView6);
        img5 = (ImageView) findViewById(R.id.imageView5);
        img6 = (ImageView) findViewById(R.id.imageView4);
        img7 = (ImageView) findViewById(R.id.imageView2);

        next1 = (ImageView) findViewById(R.id.next1);
        next2 = (ImageView) findViewById(R.id.next2);
        next3 = (ImageView) findViewById(R.id.next8);
        next4 = (ImageView) findViewById(R.id.next3);
        next5 = (ImageView) findViewById(R.id.next4);
        next6 = (ImageView) findViewById(R.id.next6);

        frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(map.this, path_Info.class);
                startActivity(intent);
            }
        });

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

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duration.setText("");
                section_label.setText("Loading...");
                left.setImageResource(R.mipmap.left);
                swiping++;
                if (swiping == 3) {
                    right.setImageResource(R.mipmap.no_swip);
                    swiping = 3;
                }
                testroute.route(Fromlat, Fromlng, Tolat, Tolng, swiping);
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duration.setText("");
                section_label.setText("Loading...");
                swiping--;
                right.setImageResource(R.mipmap.right);
                if (swiping == 1) {
                    left.setImageResource(R.mipmap.no_swip);
                    swiping = 1;
                }
                testroute.route(Fromlat, Fromlng, Tolat, Tolng, swiping);
            }
        });

        RetrieveNotifID();

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
                toname = Locationname;
                Tolng = getIntent().getDoubleExtra("lng", 0);
                Tolat = getIntent().getDoubleExtra("lat", 0);
                to.setText(toname);
                if (Fromlat == 0 || Fromlng == 0) {
                    from.setText("Current Location");
                    Fromlng = lng;
                    Fromlat = lat;
                }
                if (Fromlng == Tolng && Fromlat == Tolat)
                    new AlertDialog.Builder(map.this)
                            .setMessage("The point you have chosen for 'From' is the same point in 'To'")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                else
                    test();
            }

        }

        // draw marker when clicked on specific favorite location
        if (Favorites.latFav != 0) {
            m = map.addMarker(new MarkerOptions().position(new LatLng(Favorites.latFav, Favorites.lngFav)));
        }
        onMapReady(map);
    }

    public void test() {
        android.view.Display display = ((android.view.WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        frag.getLayoutParams().height = (int)(display.getHeight()*0.2);
        swiping = 1;
        left.setImageResource(R.mipmap.no_swip);
        section_label.setText("Loading...");
        testroute.count =0;
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
            map = fm.getMap();
        } //!!!!!!!!!!Map part end
    }

    //maha
    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Enable",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void showInternetDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("There is no internet connection on your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Enable",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_SETTINGS);
                                startActivity(callGPSSettingIntent);
                                check();

                            }
                        });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    static Boolean isInternetPresent;

    public void check(){
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if(!isInternetPresent){
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    check();
                }
            }, 10);
        }
        else
        {
            return;}
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
                                 m = map.addMarker(new MarkerOptions()
                                        .position(SPOTS_ARRAY[k].getPosition())
                                        .title("Title")
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_metro)));
                                spots.put(m, SPOTS_ARRAY[k]);

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
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Fromlat, Fromlng), 15));

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

              /*  if (type1 == 2 && type2 == 2){
                    // Getting URL to the Google Directions API
                    String url = getDirectionsUrl(tempCoor1, tempCoor2);
                    DownloadTask downloadTask = new DownloadTask();
                    // Start downloading json data from Google Directions API
                    downloadTask.execute(url);//not metro point*/
                ///             }
                //else
                //   if (type1 == 1 && type2 == 1){

                    polylines.add(map.addPolyline((new PolylineOptions())
                            .add(tempCoor1, tempCoor2)
                            .width(10)
                            .color(Color.BLUE)
                            .geodesic(true)));
               // } else {
                    // Getting URL to the Google Directions API
                    //String url = getDirectionsUrl(tempCoor1, tempCoor2,1,0);
                    //DownloadTask downloadTask = new DownloadTask();
                    // Start downloading json data from Google Directions API
                   // downloadTask.execute(url);//not metro point



               // }//end of else
              //  }
             /*  else
                if ((type1 == 1 && type2 == 2 )|| (type1 == 2 && type2 == 1 )){

                    polylines.add(googleMap.addPolyline((new PolylineOptions())
                            .add(tempCoor1, tempCoor2)
                            .width(10)
                            .color(Color.GREEN)
                            .geodesic(true)));
                }
*/
            }//end of for


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
static         double DU=0;

    public static double CalDuration(ArrayList<LatLng> lineCoor,int deptTime,double goalX,double goalY) {
        DurFlag=false;
        Log.d("DurFlag: ",DurFlag + "");

        try {

            for (int j = 0; j < lineCoor.size() - 1; j++) {

                LatLng tempCoor1 = lineCoor.get(j);
                LatLng tempCoor2 = lineCoor.get(j + 1);
                Log.e("type", tempCoor1 + ":" + tempCoor2);
                String tem1=tempCoor1+"";
                String tem2=tempCoor2+"";

                // Getting URL to the Google Directions API
                String url2 = getDirectionsUrl(tempCoor1, tempCoor2, 2, deptTime);
                DownloadTask downloadTask2 = new DownloadTask();
                // Start downloading json data from Google Directions API
                //downloadTask2.delegate= this;

                Log.d("D",downloadTask2.execute(url2)+"");//not metro point
            }}catch (Exception e){}
               // CalcDuration c=new CalcDuration();
               // return c.CalcDuration(url2);
        DU=Algorithm.Duration("");
        //check();
        Log.d("DU",DU+"");
        return DU;
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
                relam = Realm.getInstance(getApplicationContext());
                //Update realm object with the new notification id
                relam .beginTransaction();
                relam.copyToRealmOrUpdate(n);
                relam.commitTransaction();
                myMenu.findItem(R.id.notifi).setEnabled(false);
                myMenu.findItem(R.id.notifi).setIcon(R.mipmap.no_notification_);
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

    public void RetrieveNotifID() {
        //retrieve realm content
        relam = Realm.getInstance(getApplicationContext());
        List<Notification> itemNot = relam.allObjects(Notification.class);

        //store all returned content from realm
        if (itemNot.size() != 0)
            notifID = itemNot.get(0).getID();
        RetrieveNotif(notifID);

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


                            if (output.length() != 1) {
                                //Check if there is an output from server
                                notif = output;
                            } else if (output.length() == 1) {
                                myMenu.findItem(R.id.notifi).setEnabled(false);
                                myMenu.findItem(R.id.notifi).setIcon(R.mipmap.no_notification_);
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

    static String url2;
    static String rl2;
    public static String getDirectionsUrl(LatLng origin, LatLng dest,int type,int deptTime) {

// Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

// Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

// Sensor enabled
        String sensor = "sensor=false";

// Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;
        String parameters2 = str_origin + "&" + str_dest + "&" + deptTime+"&"+"key=AIzaSyAnD0zTSJWDgBJLNXzMPbTd7x_RTjeiqiA";

// Output format
        String output = "json";
        String url="";
// Building the url to the web service
        if(type==1)
        url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        else if (type==2)
        url="https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters2;

        return url;
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
            Log.d("Ddata",e+"||eeeee|||||||||||||||||||||");//not metro point

        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {

        final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout) findViewById(R.id.map_relative_layout);

        // MapWrapperLayout initialization
        // 39 - default marker height
        // 20 - offset between the default InfoWindow bottom edge and it's content bottom edge
        mapWrapperLayout.init(googleMap, getPixelsFromDp(this, 39 + 20));

        // We want to reuse the info window for all the markers,
        // so let's create only one class member instance
        infoWindow = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_map_wrapper_layout, null);
        infoButton = (Button) infoWindow.findViewById(R.id.button);
        infoButton1 = (Button) infoWindow.findViewById(R.id.button2);
        infoButton2 = (Button) infoWindow.findViewById(R.id.button3);

        // Setting custom OnTouchListener which deals with the pressed state
        // so it shows up
        this.infoButtonListener = new OnInfoWindowElemTouchListener(infoButton,
                getResources().getDrawable(R.drawable.cast_ic_notification_1),
                getResources().getDrawable(R.drawable.cast_ic_notification_2)) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button

               // if (m != null)
                  //  m.remove();

                Fromlat = latat;
                Fromlng = longt;

                if (Favorites.nameFav.equals(""))
                    from.setText(Fromlat + "," + Fromlng);
                else
                    from.setText(Favorites.nameFav + "");
                // Here action triggered after clicking the button
                if (Tolat == Fromlat && Tolng == Fromlng)
                    new AlertDialog.Builder(map.this)
                            .setMessage("The point you have chosen for 'From' is the same point in 'To'")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                else
                    Toast.makeText(getApplicationContext(), "The point is added", Toast.LENGTH_SHORT).show();

                marker.remove();
            }
        };

        this.infoButtonListener1 = new OnInfoWindowElemTouchListener(infoButton1,
                getResources().getDrawable(R.drawable.cast_ic_notification_1),
                getResources().getDrawable(R.drawable.cast_ic_notification_2)) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button
               // if (m != null)
                  //  m.remove();

                Tolat = latat;
                Tolng = longt;

                if (Favorites.nameFav.equals(""))
                    to.setText(Tolat + "," + Tolng);
                else
                    to.setText(Favorites.nameFav + "");
                if (Tolat == Fromlat && Tolng == Fromlng)
                    new AlertDialog.Builder(map.this)
                            .setMessage("The point you have chosen for 'From' is the same point in 'To'")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();

                else
                    Toast.makeText(getApplicationContext(), "The point is added", Toast.LENGTH_SHORT).show();

                marker.remove();
                test();
            }
        };

        this.infoButtonListener2 = new OnInfoWindowElemTouchListener(infoButton2,
                getResources().getDrawable(R.drawable.cast_ic_notification_1),
                getResources().getDrawable(R.drawable.cast_ic_notification_2)) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {

                //if (m != null)
                   // m.remove();

                F = new FavoriteClass();
                F.setId((Favorites.id)++);
                F.setLat(latat);
                F.setLng(longt);
                F.setName(latat + "," + longt);

                relam = Realm.getInstance(getApplicationContext());
                relam.beginTransaction();
                relam.copyToRealmOrUpdate(F);
                relam.commitTransaction();

                // Here action triggered after clicking the button
                Toast.makeText(getApplicationContext(), "Successfully added to favorite", Toast.LENGTH_SHORT).show();

                marker.remove();

            }
        };
        infoButton.setOnTouchListener(infoButtonListener);
        infoButton1.setOnTouchListener(infoButtonListener1);
        infoButton2.setOnTouchListener(infoButtonListener2);

        googleMap.setInfoWindowAdapter(new InfoWindowAdapter() {
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
                // We must call this to set the current marker and infoWindow references
                // to the MapWrapperLayout
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                return infoWindow;
            }
        });

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                if (m != null)
                    m.remove();

                m = googleMap.addMarker(new MarkerOptions().position(latLng));

                latat = latLng.latitude;
                longt = latLng.longitude;

            }
        });  //end on click
        //PlotStation();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(100); // Update location every second

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
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        location = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        buildGoogleApiClient();
    }

    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

}
class DownloadTask extends AsyncTask<String, Void, String>  implements AsyncResponse{
    public AsyncResponse delegate = null;

    @Override
    public void processFinish(String output) {
        Log.d("dod", output + "");
        //delegate.processFinish(output);

        map.DU=Algorithm.Duration(output);
        Log.d("ood", map.DU+"");
        //Log.d("Duration:CalDuration",  Algorithm.Duration(output)+ "");
    }

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