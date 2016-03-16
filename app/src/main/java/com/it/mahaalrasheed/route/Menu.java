package com.it.mahaalrasheed.route;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    Button enter , delete ,post;
    String adminId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        enter=(Button)findViewById(R.id.button2);
        delete=(Button)findViewById(R.id.button3);
        post=(Button)findViewById(R.id.button4);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminId= getIntent().getExtras().getString("AdminID");
                Intent i =new Intent(getApplicationContext(),Enter.class);
                i.putExtra("AdminID", adminId);
                startActivity(i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Delete.class);
                startActivity(i);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NewPost.class);
                startActivity(i);
            }
        });
    }
}
