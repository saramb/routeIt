package com.it.mahaalrasheed.route;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class notif extends AppCompatActivity {
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);



        String content=getIntent().getExtras().getString("content");
        List<String> ArrContent=new ArrayList<String>();
    //    String[] contentArray=new String[100];
         id="0";

        for(int i=0;i<content.length();i++){

            id=content.substring(0,content.indexOf("-"));
            ArrContent.add(content.substring(content.indexOf("-") + 1, content.indexOf("*")));
            content=content.substring(content.indexOf("*")+1);
        }


        ListView listView= (ListView)findViewById(R.id.notifListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(notif.this,android.R.layout.simple_list_item_activated_1,ArrContent);
        listView.setAdapter(adapter);


       // int iid=Integer.parseInt(id);
        Intent intent=new Intent();
        intent.putExtra("id", id);
        setResult(Activity.RESULT_OK,intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
       /* Realm realm=Realm.getInstance(getApplicationContext());
        Notification n=new Notification();
        n.setID(Integer.parseInt(id));n.setPk(0);
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(n);
        realm.commitTransaction();*/

    }
}
