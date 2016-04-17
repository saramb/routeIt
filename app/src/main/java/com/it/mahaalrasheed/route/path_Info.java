package com.it.mahaalrasheed.route;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;


public class path_Info extends AppCompatActivity {

    ListView list;
    public  String[] itemname = new String[routeInfo.number];
    public  Integer[] imgid = new Integer[routeInfo.number];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path__info);


        for( int i =0;i<routeInfo.number;i++){
            itemname[i]=routeInfo.stationName[i];

            if (routeInfo.Number[i]==1){
                imgid[i]= R.mipmap.metro;

            }
            else
            {  imgid[i]= R.mipmap.busicon;}

            if(i == 0 )
                map.img1.setImageResource(imgid[i]);
            else
            if(i == 1 ) map.img2.setImageResource(imgid[i]);
            else
            if(i == 2 ) map.img3.setImageResource(imgid[i]);
            else
            if(i == 3 ) map.img4.setImageResource(imgid[i]);
            else
            if(i == 4 ) map.img5.setImageResource(imgid[i]);
            else
            if(i == 5 ) map.img6.setImageResource(imgid[i]);
            else
            if(i == 6 ) map.img7.setImageResource(imgid[i]);

        }



        CustomListAdapter adapter = new CustomListAdapter(this, itemname, imgid);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.path_info_back, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (item.getItemId()== R.id.back){
            Intent intent = new Intent(this, map.class);
            startActivity(intent);
        }
        return true;

    }
}
