package com.ali.rnp.khodemon.Views.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.fragments.FragmentAddLevelTwo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class GoogleMapsActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnCameraMoveListener {

    private GoogleMap mMap;
    private Marker mMarker;
    private int requestCode = 0;

    private boolean isFromFragmentAddTwo = false;


    private MyButton buttonChoose;


    private LatLng currentLatLng;


    public final static String KEY_REQUEST_CHOOSE_LOCATION_ON_MAP = "CHOOSE_LOCATION_ON_MAP";
    public final static int REQUEST_FROM_FRAGMENT_ADD_LEVEL_TWO = 5246;

    private static final String TAG = "GoogleMapsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        initViews();
        getDataFromIntent();
        setupToolbar();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }


    }

    private void getDataFromIntent() {

        requestCode = getIntent().getIntExtra(ProvidersApp.KEY_CHOOSE_MAP_FRG_ADD_LVL_TWO, 0);


        isFromFragmentAddTwo = requestCode == ProvidersApp.REQUEST_CODE_CHOOSE_MAP_FRG_ADD_LVL_TWO;


    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.activity_google_maps_Toolbar);

        setSupportActionBar(toolbar);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);


        }

        // DrawerLayout drawerLayout = findViewById(R.id.mainActivity_drawer_layout);

        // ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);

        //  drawerLayout.addDrawerListener(drawerToggle);
        // drawerToggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        // drawerToggle.syncState();
    }

    private void initViews() {

        buttonChoose = findViewById(R.id.activity_google_maps_button_choose);

        buttonChoose.setOnClickListener(v -> {

            if (currentLatLng != null) {
                /*
                Intent intent = new Intent();
                intent.putExtra(KEY_REQUEST_CHOOSE_LOCATION_ON_MAP, currentLatLng);
                intent.putExtra("test",currentLatLng.latitude);
                setResult(Activity.RESULT_OK, intent);
*/
              //  if (isFromFragmentAddTwo) {
                  //  FragmentAddLevelTwo.selectedLatLong = currentLatLng;
                    Intent intent = new Intent();
                    intent.putExtra(ProvidersApp.KEY_CHOOSE_MAP_LATITUDE,currentLatLng.latitude);
                    intent.putExtra(ProvidersApp.KEY_CHOOSE_MAP_LONGITUDE,currentLatLng.longitude);
                    setResult(Activity.RESULT_OK,intent);
                    finish();

               // }



            } else {
                Toast.makeText(this, "currentLatLng == null", Toast.LENGTH_SHORT).show();
            }

        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng abadan = new LatLng(30.3473, 48.2934);
/*
        mMarker = mMap.addMarker(new MarkerOptions()
                .position(abadan)
                .title(cityName)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        );

        mMarker.setTag(0);
*/


        /*
        mMap.moveCamera(CameraUpdateFactory.newLatLng(abadan));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(abadan, 12.0f));
*/
        CameraPosition cameraPosition = new CameraPosition.Builder().target(abadan).zoom(12).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        mMap.setOnMapClickListener(this);


        mMap.setOnMyLocationButtonClickListener(this);

        updateLocationUI();


    }


    @Override
    public boolean onMyLocationButtonClick() {

        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
        return false;
    }


    @Override
    public void onMapClick(LatLng latLng) {


        if (mMarker != null) {
            mMarker.remove();
        }

        mMarker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        );

        mMarker.setPosition(latLng);


        // Toast.makeText(GoogleMapsActivity.this, "latCurrent : " + latLng.latitude + "\n long : " + latLng.longitude, Toast.LENGTH_SHORT).show();
        //  Log.i(TAG, "onMapClick: latCurrent : " + latLng.latitude + "\n long : " + latLng.longitude);


        currentLatLng = latLng;


    }


    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onCameraMove() {
        Toast.makeText(this, "Move", Toast.LENGTH_SHORT).show();
    }
}
