package com.it.mahaalrasheed.route;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Splash extends AppCompatActivity {

    private static int SPLASH_DISPLAY_LENGTH;
    static int arraySize[] = new int[30];
    static Boolean isInternetPresent;
    static LocationManager locationManager;
    boolean flag = false;
    public static final String ROOT_URL ="http://rawan.16mb.com/RouteIt/";
    //public static final String ROOT_URL = "http://192.168.100.16/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet(); // true or false
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && isInternetPresent) {
            numOfStation();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(Splash.this, map.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        } else if (!isInternetPresent)
            showInternetDisabledAlertToUser();
        else if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            showGPSDisabledAlertToUser();


    }

    public void numOfStation() {
        //Here we will handle the http request to retrieve from mysql db
        //Creating a RestAdapter
        SPLASH_DISPLAY_LENGTH = 1200;
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

                            int i = 0;
                            //Check if there is an output from server
                            while (!output.equals("")) {
                                arraySize[i] = Integer.parseInt(output.substring(0, output.indexOf("/"))) + 1;
                                i++;
                                output = output.substring(output.indexOf("/") + 1);
                            }
                            testroute.Mline1 = new String[Splash.arraySize[0]][Splash.arraySize[0]];
                            testroute.Mline2 = new String[Splash.arraySize[1]][Splash.arraySize[1]];
                            testroute.Mline3 = new String[Splash.arraySize[2]][Splash.arraySize[2]];
                            testroute.Mline4 = new String[Splash.arraySize[3]][Splash.arraySize[3]];
                            testroute.Mline5 = new String[Splash.arraySize[4]][Splash.arraySize[4]];
                            testroute.Mline6 = new String[Splash.arraySize[5]][Splash.arraySize[5]];
                            testroute.Bline2_1 = new String[Splash.arraySize[6]][Splash.arraySize[6]];
                            testroute.Bline2_2 = new String[Splash.arraySize[7]][Splash.arraySize[7]];
                            testroute.Bline2_3 = new String[Splash.arraySize[8]][Splash.arraySize[8]];
                            testroute.Bline2_4 = new String[Splash.arraySize[9]][Splash.arraySize[9]];
                            testroute.Bline2_5 = new String[Splash.arraySize[10]][Splash.arraySize[10]];
                            testroute.Bline2_6 = new String[Splash.arraySize[11]][Splash.arraySize[11]];
                            testroute.Bline2_7 = new String[Splash.arraySize[12]][Splash.arraySize[12]];
                            testroute.Bline2_8 = new String[Splash.arraySize[13]][Splash.arraySize[13]];
                            testroute.Bline2_9 = new String[Splash.arraySize[14]][Splash.arraySize[14]];
                            testroute.Bline2_10 = new String[Splash.arraySize[15]][Splash.arraySize[15]];
                            testroute.Bline3_1 = new String[Splash.arraySize[16]][Splash.arraySize[16]];
                            testroute.Bline3_2 = new String[Splash.arraySize[17]][Splash.arraySize[17]];
                            testroute.Bline3_3 = new String[Splash.arraySize[18]][Splash.arraySize[18]];
                            testroute.Bline3_4 = new String[Splash.arraySize[19]][Splash.arraySize[19]];
                            testroute.Bline3_5 = new String[Splash.arraySize[20]][Splash.arraySize[20]];
                            testroute.Bline3_6 = new String[Splash.arraySize[21]][Splash.arraySize[21]];
                            testroute.Bline3_7 = new String[Splash.arraySize[22]][Splash.arraySize[22]];
                            testroute.Bline3_8 = new String[Splash.arraySize[23]][Splash.arraySize[23]];
                            testroute.Bline3_9 = new String[Splash.arraySize[24]][Splash.arraySize[24]];
                            testroute.Bline3_10 = new String[Splash.arraySize[25]][Splash.arraySize[25]];
                            testroute.Bline4_1 = new String[Splash.arraySize[26]][Splash.arraySize[26]];
                            testroute.Bline4_2 = new String[Splash.arraySize[27]][Splash.arraySize[27]];
                            testroute.link();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        //Toast.makeText(Splash.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    //maha
    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Enable",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                                check2();
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    //maha
    public void showInternetDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("There is no internet connection on your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Enable",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_SETTINGS);
                                startActivity(callGPSSettingIntent);
                                check();

                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void callnum() {
        numOfStation();
    }

    public void callIntent() {
    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER ))
    check2();
        Intent mainIntent = new Intent(Splash.this, map.class);
        startActivity(mainIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    public void check() {
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if (!isInternetPresent) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    check();
                }
            }, 10);
        } else {
            callnum();
            flag = true;
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                showGPSDisabledAlertToUser();
            else
                check2();
        }
    }

    public void check2() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    check2();
                }
            }, 10);
    }
    else
    {
        if (flag)
        callnum();
        callIntent();
        return;
    }
}
}
