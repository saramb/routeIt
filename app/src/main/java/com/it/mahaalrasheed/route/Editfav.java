package com.it.mahaalrasheed.route;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editfav);


         ed = (EditText)findViewById(R.id.editText);
        Button save = (Button) findViewById(R.id.button4);
        Button del = (Button) findViewById(R.id.button5);

        id = getIntent().getIntExtra("id", 0);
        relam = Realm.getInstance(getApplicationContext());
        items= relam.allObjects(FavoriteClass.class).get(id);
        ed.setText(items.getName().toString());


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relam.beginTransaction();
                items.setName(ed.getText().toString());
                relam.copyToRealmOrUpdate(items);
                relam.commitTransaction();
                Toast.makeText(Editfav.this, "تم التعديل بنجاح", Toast.LENGTH_SHORT).show();
                finish();
            }
        }) ;
     del.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             relam.beginTransaction();
             items.removeFromRealm();
             relam.commitTransaction();
             Toast.makeText(Editfav.this, "تم الحذف بنجاح", Toast.LENGTH_SHORT).show();
             finish();
         }
     });

    }
}
