package com.it.mahaalrasheed.route;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class loginnav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //This is our root url
    private String ROOT_URL = map.ROOT_URL;
    EditText username , pass;
    TextView wrong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginnav);
        username =(EditText) findViewById(R.id.username);
        pass =(EditText) findViewById(R.id.pass);
        Button login = (Button) findViewById(R.id.button);
        wrong=(TextView)findViewById(R.id.textView31);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wrong.setText("");

                if(username.getText().toString().equals("")||pass.getText().toString().equals("")){
                    if(username.getText().toString().equals("")) {
                        username.setError("Please enter user name");
                    }
                    else
                        username.setError(null);

                    if(pass.getText().toString().equals("")) {
                        pass.setError("Please enter password");
                    }
                    else
                        pass.setError(null);
                }

                else{

                    username.setError(null);
                    pass.setError(null);

                }

                if(!(username.getText().toString().equals("")||pass.getText().toString().equals("")))
                    login();

            }
        });
    }

    private void login(){
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);

        //Defining the method insertuser of our interface
        api.login(

                //Passing the values by getting it from editTexts
                username.getText().toString(),
                pass.getText().toString(),


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
                            if (output.equals("Successfully logged in")) {
                                Toast.makeText(getApplicationContext(),output+"",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), com.it.mahaalrasheed.route.Menu.class);
                                i.putExtra("AdminID", username.getText().toString());
                                startActivity(i);
                            }
                            else
                                Toast.makeText(getApplicationContext(),output+"",Toast.LENGTH_SHORT).show();



                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Displaying the output as a toast
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(loginnav.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
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

        } else if (id == R.id.nav_slideshow) { //
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


}
