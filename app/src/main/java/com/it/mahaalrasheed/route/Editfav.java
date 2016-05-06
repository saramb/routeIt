package com.it.mahaalrasheed.route;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;

public class Editfav extends AppCompatActivity {

    Realm relam ;
    int id ;
    FavoriteClass items;
    EditText ed;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_editfav);

        ed = (EditText)findViewById(R.id.editText);
        Button save = (Button) findViewById(R.id.button4);
        Button del = (Button) findViewById(R.id.button5);

        id = getIntent().getIntExtra("id", 0);
        relam = Realm.getInstance(getApplicationContext());
        items= relam.allObjects(FavoriteClass.class).get(id);
        ed.setText(items.getName().toString());
        str=ed.getText().toString();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(str.equals(ed.getText().toString())))
                {
                    relam.beginTransaction();
                    items.setName(ed.getText().toString());
                    relam.copyToRealmOrUpdate(items);
                    relam.commitTransaction();
                    Toast.makeText(Editfav.this, "Changes successfully added", Toast.LENGTH_SHORT).show();
                    finish();

                }else {
                    Toast.makeText(Editfav.this, "There is no changes to save", Toast.LENGTH_SHORT).show();
                    finish();

                }
            }
        }) ;
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                relam.beginTransaction();
                items.removeFromRealm();
                relam.commitTransaction();
                Toast.makeText(Editfav.this, "Your Favorite has been succeefully deleted", Toast.LENGTH_SHORT).show();
                finish();


            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.favorite_back, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId()== R.id.back){
            finish();
        }
        return true;
    }
}
