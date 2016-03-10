package com.it.mahaalrasheed.route;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.List;

import io.realm.Realm;

public class from extends AppCompatActivity {

    PlaceAutocompleteFragment autocompleteFragment;
    Realm relam;
    List<FavoriteClass> item;
    ListView lv;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from);

        lv = (ListView)findViewById(R.id.listView2);

        update();

        autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Intent n = new Intent(from.this, map.class);
                n.putExtra("lat", place.getLatLng().latitude );
                n.putExtra("lng", place.getLatLng().longitude);
                startActivity(n);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.

            }
        });
    }

    public void update(){
        relam = Realm.getInstance(getApplicationContext());
        item = relam.allObjects(FavoriteClass.class);
        String[] array = new String[item.size()];
        for (int i = 0; i < item.size(); i++) {
            array[i] = item.get(i).getName();}

        ArrayAdapter addapter = new ArrayAdapter(from.this, android.R.layout.simple_list_item_1,array);
        lv.setAdapter(addapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent n = new Intent(from.this, map.class);
                FavoriteClass F = relam.allObjects(FavoriteClass.class).get(position);
                n.putExtra("lat", F.getLat());
                n.putExtra("lng", F.getLng());
                startActivity(n);
            }
        });

    }
}