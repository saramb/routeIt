package com.it.mahaalrasheed.route;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NewPost extends AppCompatActivity {

    private String ROOT_URL = map.ROOT_URL;
    LineEditText message;
    Button enter;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

         enter=(Button)findViewById(R.id.button13);
         message=(LineEditText)findViewById(R.id.message);
         error=(TextView)findViewById(R.id.textView16);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message.getText().toString().equals("")) {
                    // error.requestFocus();
                    error.setText("The field is empty, Please enter yout notification ");
                    error.setError("");

                } else {
                    error.setError(null);
                    error.setText("");

                    new AlertDialog.Builder(NewPost.this)
                            .setMessage("are you sure you want to continue post process ?")
                            .setPositiveButton("Post", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    /*Intent intent = new Intent();
                                    PendingIntent pIntent = PendingIntent.getActivity(NewPost.this, 0, intent, 0);*///مهم نشوفها
                                    AddNotif();
                                    finish();
                                }
                            })

                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
        });
    }
    private void AddNotif() {
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);


        //Defining the method insertuser of our interface
        api.AddNotif(
                message.getText().toString(),
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

                            Toast.makeText(NewPost.this, output, Toast.LENGTH_LONG).show();


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Displaying the output as a toast
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(NewPost.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

}
