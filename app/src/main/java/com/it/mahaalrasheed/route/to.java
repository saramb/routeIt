package com.it.mahaalrasheed.route;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;

import io.realm.Realm;

public class to extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

        Realm relam;
        List<FavoriteClass> item;
        ListView lv;
        int id;
        String[] array;
        ArrayAdapter addapter;

    ////////////////////// search ////////////////////
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    LatLngBounds BOUNDS_MOUNTAIN_VIEW ;
    ////////////////////////////////////////

    @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_to);

            BOUNDS_MOUNTAIN_VIEW = from.BOUNDS_MOUNTAIN_VIEW;

            lv = (ListView)findViewById(R.id.listView2);

            update();

        //////---search-----////
        mGoogleApiClient = new GoogleApiClient.Builder(to.this)
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
        if(!mAutocompleteTextView.hasFocus()){
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
            Intent n = new Intent(to.this, map.class);
            n.putExtra("page","to".toString());
            n.putExtra("name", place.getName().toString());
            n.putExtra("lat", place.getLatLng().latitude );
            n.putExtra("lng", place.getLatLng().longitude);
            startActivity(n);
            finish();}
        }
    };
        public void update(){
            relam = Realm.getInstance(getApplicationContext());
            item = relam.allObjects(FavoriteClass.class);
            array = new String[item.size()];
            for (int i = 0; i < item.size(); i++) {
                array[i] = item.get(i).getName();}

            addapter = new ArrayAdapter(to.this, android.R.layout.simple_list_item_1,array);
            lv.setAdapter(addapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent n = new Intent(to.this, map.class);
                    FavoriteClass F = relam.allObjects(FavoriteClass.class).get(position);
                    n.putExtra("page","to");
                    n.putExtra("name", F.getName().toString());
                    n.putExtra("lat", F.getLat());
                    n.putExtra("lng", F.getLng());
                    startActivity(n);
                    finish();
                }
            });

        }
    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.to_back, menu);
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
