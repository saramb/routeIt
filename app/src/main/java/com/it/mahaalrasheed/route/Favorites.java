package com.it.mahaalrasheed.route;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
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


public class Favorites extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    PlaceAutocompleteFragment autocompleteFragment;
    Realm relam;
    FavoriteClass F;
    List<FavoriteClass> item;
    SwipeMenuListView lv;
    public static int id;
    int iddelete;
    ArrayAdapter addapter;
    String[] array;
    static double latFav=0.0;
    static double lngFav=0.0;
    static String nameFav = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        lv = (SwipeMenuListView)findViewById(R.id.listView);

        update();

        autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                F = new FavoriteClass();
                F.setId(id++);
                F.setLat(place.getLatLng().latitude);
                F.setLng(place.getLatLng().longitude);
                F.setName(place.getName().toString());

                relam = Realm.getInstance(getApplicationContext());
                relam.beginTransaction();
                relam.copyToRealmOrUpdate(F);
                relam.commitTransaction();
                Toast.makeText(Favorites.this, "Correctly add favorite", Toast.LENGTH_LONG).show();
                update();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.

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
                        relam.allObjects(FavoriteClass.class).remove(iddelete);
                        relam.commitTransaction();
                        update();
                        Toast.makeText(Favorites.this, "Correctly delete favorite", Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });

        // set SwipeListener
        lv.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                iddelete=position;
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) { //map
            // Handle the map action
            Intent intent = new Intent (this, map.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {  //favorites
            Intent intent = new Intent (this, Favorites.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {  //about us
            Intent intent = new Intent (this, aboutusnav.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    public void update(){
        relam = Realm.getInstance(getApplicationContext());
        item = relam.allObjects(FavoriteClass.class);
        array = new String[item.size()];
        for (int i = 0; i < item.size(); i++) {
            array[i] = item.get(i).getName();}

        addapter = new ArrayAdapter(Favorites.this, android.R.layout.simple_list_item_1,array);
        lv.setAdapter(addapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent n = new Intent(Favorites.this,map.class);
                FavoriteClass F = relam.allObjects(FavoriteClass.class).get(position);
                latFav= F.getLat();
                lngFav = F.getLng();
                nameFav= F.getName();
                startActivity(n);
            }
        });

    }
}
