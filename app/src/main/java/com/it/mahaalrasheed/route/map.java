package com.it.mahaalrasheed.route;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.realm.Realm;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
//test githb
public class map extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

  // public static final String ROOT_URL = "http://192.168.100.15:8080/";
  public static final String ROOT_URL = "http://192.168.1.69/";

    GoogleMap googleMap;
    double lng;
    double lat;
    Button from, to;
    Location location;
    MapFragment fm;
    LocationManager locationManager;
    String provider;
    static int notifID=0;
    String notif="";
    Realm realm;
    Notification notification;
    Menu myMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        notification=new Notification();
        notification.setID(notifID);
        notification.setPk(0);

        realm = Realm.getInstance(getApplicationContext());

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(notification);
        realm.commitTransaction();


        notifID=realm.allObjects(Notification.class).get(0).getID();
        Toast.makeText(map.this, notifID+" //create", Toast.LENGTH_SHORT).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        from = (Button) findViewById(R.id.frombutton);
        to = (Button) findViewById(R.id.tobutton);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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
            googleMap.setMyLocationEnabled(true);

            // Getting LocationManager object from System Service LOCATION_SERVICE
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
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
                    drawMarker(location);
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
                drawMarker(location);
            }
            locationManager.requestLocationUpdates(provider, 0, 0, locationListener);

        } //!!!!!!!!!!Map part end

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


    }

    private void drawMarker(Location location){
        googleMap.clear();
        lat= location.getLatitude();
        lng= location.getLongitude();
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .snippet("Lat:" + location.getLatitude() + "Lng:" + location.getLongitude()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 16));

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

                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();

                            while (!output.equals("")) {
                                String XCoordinates = output.substring(0, output.indexOf(":"));
                                String YCoordinates = output.substring(output.indexOf(":") + 1, output.indexOf(" "));
                                output = output.substring(output.indexOf(" ") + 1);
                                lng = Double.parseDouble(XCoordinates);
                                lat = Double.parseDouble(YCoordinates);
                            }
                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(0, 0)));


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
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id1=item.getItemId();
        String s=R.id.notifi+"";

        Toast.makeText(map.this,  id1+"*"+s, Toast.LENGTH_SHORT).show();
        //noinspection SimplifiableIfStatement
        if ( item.getItemId() == R.id.notifi) { //if user press the notification icon on the menu bar, go to favorite activity
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

        // check if the request code is same as what is passed  here it is 2
        if(requestCode==1)
        {
            if(resultCode== Activity.RESULT_OK){
            String message=data.getStringExtra("id");
            //Toast.makeText(map.this, message, Toast.LENGTH_SHORT).show();
            notifID=Integer.parseInt( message+"");

            notification.setID(notifID);
            realm = Realm.getInstance(getApplicationContext());
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(notification);
            realm.commitTransaction();

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
            Intent intent = new Intent (this, favorite.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) { //login
            Intent intent = new Intent (this, login.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {  //about us
            Intent intent = new Intent (this, aboutus.class);
            startActivity(intent);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void RetrieveNotif() {
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);

       // Notification objnotification= notifRealm.allObjects(Notification.class).get(0);

        //Defining the method  RetrieveNotif of our interface
        api.RetrieveNotif(

                //Passing the values by getting it from editTexts
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


                            if (!output.equals("")) {
                                myMenu.findItem(R.id.notifi).setEnabled(true);
                                myMenu.findItem(R.id.notifi).setIcon(R.drawable.no_notification);
                                notif = output;
                                Toast.makeText(map.this, notif, Toast.LENGTH_SHORT).show();


                            }
                            if (output.equals("NULL")) {
                                myMenu.findItem(R.id.notifi).setEnabled(false);
                                myMenu.findItem(R.id.notifi).setIcon(R.drawable.no_notification_);

                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Displaying the output as a toast
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
        notifID=realm.allObjects(Notification.class).get(0).getID();
        Toast.makeText(map.this, notifID+" //start", Toast.LENGTH_SHORT).show();

        notification.setID(notifID);
        realm = Realm.getInstance(getApplicationContext());

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(notification);
        realm.commitTransaction();
        RetrieveNotif();

    }
}
