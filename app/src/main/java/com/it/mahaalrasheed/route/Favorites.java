package com.it.mahaalrasheed.route;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLngBounds;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;


public class Favorites extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener {

    PlaceAutocompleteFragment autocompleteFragment;
    Realm relam;
    FavoriteClass F;
    List<FavoriteClass> item;
    SwipeMenuListView lv;
    public static int id = 0;
    int iddelete;
    ArrayAdapter addapter;
    ArrayList<String> array;
    static double latFav = 0.0;
    static double lngFav = 0.0;
    static String nameFav = "";
    Calendar calendar;

    ////////////////////// search ////////////////////
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    LatLngBounds BOUNDS_MOUNTAIN_VIEW;
    ////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
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

        lv = (SwipeMenuListView) findViewById(R.id.listView);

        update();

        //////---search-----////
        mGoogleApiClient = new GoogleApiClient.Builder(Favorites.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        mAutocompleteTextView = (AutoCompleteTextView) findViewById(R.id
                .autoCompleteTextView);
        mAutocompleteTextView.setThreshold(1);
        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);
        if (!mAutocompleteTextView.hasFocus()) {
            mAutocompleteTextView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.searchicon2, 0, 0, 0);
            mAutocompleteTextView.setHint("Search");

        }


        mAutocompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    mAutocompleteTextView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.searchicon2, 0, 0, 0);
                    mAutocompleteTextView.setHint("Search");

                } else {
                    //Assign your image again to the view, otherwise it will always be gone even if the text is 0 again.
                    mAutocompleteTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    mAutocompleteTextView.setHint("");

                }
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
                Drawable image = getResources().getDrawable(R.mipmap.pencil);
                //image.setBounds(80, 80, 100, 100);

                deleteItem.setWidth(dp2px(30));
                // set a icon
                deleteItem.setIcon(R.mipmap.pencil);
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
                        Intent n = new Intent(Favorites.this, Editfav.class);
                        n.putExtra("id", position);
                        startActivity(n);
                        break;
                }
                return false;
            }
        });

        // set SwipeListener
        lv.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                iddelete = position;
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                iddelete = position;
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
        update();

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        autocompleteFragment.onActivityResult(requestCode, resultCode, data);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                mAutocompleteTextView.setHint(" ");
            }

            @Override
            public void onError(Status status) {

            }
        });


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
            Intent intent = new Intent(this, map.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {  //favorites
            Intent intent = new Intent(this, Favorites.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {  //about us
            Intent intent = new Intent(this, aboutusnav.class);
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

    public void update() {
        relam = Realm.getInstance(getApplicationContext());
        item = relam.allObjects(FavoriteClass.class);
        array = new ArrayList<String>();
        for (int i = 0; i < item.size(); i++) {
            if (!item.get(i).getName().equals("-"))
                array.add(i, item.get(i).getName());
        }

        addapter=new ArrayAdapter(Favorites.this, android.R.layout.simple_list_item_1, array);

        lv.setAdapter(addapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()

                                  {
                                      @Override
                                      public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
                                          Intent n = new Intent(Favorites.this, map.class);
                                          FavoriteClass F = relam.allObjects(FavoriteClass.class).get(position);
                                          latFav = F.getLat();
                                          lngFav = F.getLng();
                                          nameFav = F.getName();
                                          startActivity(n);
                                          finish();
                                      }
                                  }

        ); }


    @Override
    protected void onResume() {
        super.onResume();
        update();
    }


    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess())
                return;
            else {
                // Selecting the first object buffer.
                final Place place = places.get(0);
                F = new FavoriteClass();
                F.setId((int) Calendar.getInstance().getTimeInMillis());
                F.setLat(place.getLatLng().latitude);
                F.setLng(place.getLatLng().longitude);
                F.setName(place.getName().toString());

                relam.beginTransaction();
                relam.copyToRealmOrUpdate(F);
                relam.commitTransaction();

                mAutocompleteTextView.setText(" ");

                Toast.makeText(getApplicationContext(), "Successfully added to favorite", Toast.LENGTH_SHORT).show();

                update();
            }
        }
    };

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode()+"shahad",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

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
            Intent intent = new Intent(this, map.class);
            startActivity(intent);
        }
        return true;
    }
}
