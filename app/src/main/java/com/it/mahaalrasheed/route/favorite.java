package com.it.mahaalrasheed.route;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.List;

import io.realm.Realm;

public class favorite extends AppCompatActivity {

    PlaceAutocompleteFragment autocompleteFragment;
    Realm relam;
    FavoriteClass F;
    List<FavoriteClass> item;
    SwipeMenuListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        lv = (SwipeMenuListView)findViewById(R.id.listView);

        relam = Realm.getInstance(getApplicationContext());
        item = relam.allObjects(FavoriteClass.class);
        String[] array = new String[item.size()];
        for (int i = 0; i < item.size(); i++) {
            array[i] = item.get(i).getName();}

        ArrayAdapter addapter = new ArrayAdapter(favorite.this, android.R.layout.simple_list_item_1,array);
        lv.setAdapter(addapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent = new Intent(favorite.this, Main22Activity.class);
                //intent.putExtra("id", position);
                //startActivity(intent);
            }
        });


        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(android.R.drawable.ic_menu_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        lv.setMenuCreator(creator);

        // step 2. listener item click event
        lv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        relam.beginTransaction();
                        F.removeFromRealm();
                        relam.commitTransaction();
                        Toast.makeText(favorite.this, "Correctly delete favorite", Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });

        // set SwipeListener
        lv.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // set MenuStateChangeListener
        lv.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
            }

            @Override
            public void onMenuClose(int position) {
            }
        });

        autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                F = new FavoriteClass();
                F.setLat(place.getLatLng().latitude);
                F.setLng(place.getLatLng().longitude);
                F.setName(place.getName().toString());

                relam = Realm.getInstance(getApplicationContext());
                relam.beginTransaction();
                relam.copyToRealmOrUpdate(F);
                relam.commitTransaction();
                Toast.makeText(favorite.this, "Correctly add favorite", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.

            }
        });

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        autocompleteFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();

        lv = (SwipeMenuListView)findViewById(R.id.listView);

        relam = Realm.getInstance(getApplicationContext());
        item = relam.allObjects(FavoriteClass.class);
        String[] array = new String[item.size()];
        for (int i = 0; i < item.size(); i++) {
            array[i] = item.get(i).getName();}

        ArrayAdapter addapter = new ArrayAdapter(favorite.this, android.R.layout.simple_list_item_1,array);
        lv.setAdapter(addapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent = new Intent(favorite.this, Main22Activity.class);
                //intent.putExtra("id", position);
                //startActivity(intent);
            }
        });

    }
}
