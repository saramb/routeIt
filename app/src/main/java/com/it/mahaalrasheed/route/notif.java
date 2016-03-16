package com.it.mahaalrasheed.route;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class notif extends AppCompatActivity {
    String id , content;
    List<String> ArrContent;
    int length;
    ListView listView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);

        content=getIntent().getExtras().getString("content");
        ArrContent=new ArrayList<String>();
        id="0";
        length = content.length();

        for(int i=0;i<length;i++) {

            if (content.indexOf("-") != -1 && content.indexOf("*") != -1 ) {

                id = content.substring(0, content.indexOf("-"));
                ArrContent.add(content.substring(content.indexOf("-") + 1, content.indexOf("*")));

                if (!content.substring(content.indexOf("*") +1).equals(""))
                    content = content.substring(content.indexOf("*") + 1);
                else
                    content = "";
            }

        }

        listView= (ListView)findViewById(R.id.notifListView);
        adapter = new ArrayAdapter<String>(notif.this,android.R.layout.simple_list_item_activated_1,ArrContent);
        listView.setAdapter(adapter);

        Intent intent=new Intent();
        intent.putExtra("id", id);
        setResult(Activity.RESULT_OK,intent);
    }
}
