package com.it.mahaalrasheed.route;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;


public class path_Info extends AppCompatActivity {

    ListView list;
    String[] itemname = new String[routeInfo.count];
    Integer[] imgid = new Integer[routeInfo.count];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path__info);


         for( int i =0;i<routeInfo.count;i++){
             itemname[i]=routeInfo.stationName[i];
             if (routeInfo.Number[i]==1){
                 imgid[i]= R.mipmap.metro;
             }
             else
                 imgid[i]= R.mipmap.metro;// لازم نغير الصورة

         }

        CustomListAdapter adapter = new CustomListAdapter(this, itemname, imgid);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        /*list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem = itemname[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

            }
        });*/
    }
}