package com.ali.rnp.khodemon.Views.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ali.rnp.khodemon.MyLibrary.MyEditText;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.Activities.CityChooseActivity;
import com.ali.rnp.khodemon.Views.Activities.GoogleMapsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


public class FragmentAddLevelTwo extends Fragment implements
        View.OnClickListener,
        OnMapReadyCallback {


    private MapView mMapView;
    private GoogleMap mMap;
    private Marker mMarker;
    private CardView chooseCityCard;
    public static MyTextView chooseCityTextView;
    public static MyEditText addressEdiText;
    private MyTextView chooseMapTextView;


    public static LatLng selectedLatLong;


    String countryName;
    public static String provinceName;
    public static String cityName;

    private final int PERMISSIONS_REQUEST_LOCATION_STORAGE = 4286;
    public final int REQUEST_CODE_LOCATION_CHOOSE = 7598;

    Context context;

    private static final String TAG = "FragmentAddLevelTwo";


    public FragmentAddLevelTwo() {


    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_level_two, container, false);

        initViews(rootView);
        initGoogleMap(savedInstanceState);


        return rootView;
    }

    private void initViews(View rootView) {
        mMapView = rootView.findViewById(R.id.mapView);
        chooseCityCard = rootView.findViewById(R.id.add_level_two_cardView_chooseCity);
        chooseCityTextView = rootView.findViewById(R.id.fragment_add_level_two_MyTextView_chooseCity);
        chooseMapTextView = rootView.findViewById(R.id.fragment_add_level_two_textView_chooseFromMap);
        addressEdiText = rootView.findViewById(R.id.fragment_add_level_two_EditText_address);


        chooseCityCard.setOnClickListener(this);

        /*
        cityChooseActivity.setOnCityChooseClickListener(new CityChooseActivity.OnCityChooseClick() {
            @Override
            public void OnCityClick(String provinceName, String cityName) {
                Toast.makeText(context, provinceName+" "+cityName, Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    private void initGoogleMap(Bundle savedInstanceState) {


        mMapView.onCreate(savedInstanceState);
        mMapView.onSaveInstanceState(savedInstanceState);
        mMapView.onResume();


        try {
            MapsInitializer.initialize(context);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //initGoogleMap(rootView,savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GoogleMapOptions options = new GoogleMapOptions();
                options.compassEnabled(false)
                        .rotateGesturesEnabled(false)
                        .tiltGesturesEnabled(false)
                        .liteMode(true);




                mMapView.getMapAsync(FragmentAddLevelTwo.this);
            }
        }, 100);


    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

        if (selectedLatLong != null && mMap != null) {

            if (mMarker != null) {
                mMarker.remove();
            }

            mMarker = mMap.addMarker(new MarkerOptions()
                    .position(selectedLatLong)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
            );

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLatLong, 12.0f));


            //  getInfoLocation(selectedLatLong);


        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();


    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();


    }




    private void getNeedPermissions() {
        if (getContext() != null) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED ||

                    ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    requestPermissions(
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION},
                            PERMISSIONS_REQUEST_LOCATION_STORAGE);
                }

            } else {
                goToMapForChoose();

            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION_STORAGE: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED

                ) {
                    // permission was granted, yay!

                    goToMapForChoose();


                } else {
                    // permission denied
                    Toast.makeText(getContext(), "Permissions is Denied !", Toast.LENGTH_SHORT).show();
                }
                return;
            }


        }

    }

    private void goToMapForChoose() {

        Intent intent = new Intent(getContext(), GoogleMapsActivity.class);

        //intent.putExtra(GoogleMapsActivity.KEY_REQUEST_CHOOSE_LOCATION_ON_MAP, GoogleMapsActivity.REQUEST_FROM_FRAGMENT_ADD_LEVEL_TWO);
        /*intent.putExtra(ProvidersApp.KEY_CHOOSE_MAP_FRG_ADD_LVL_TWO,
                ProvidersApp.REQUEST_CODE_CHOOSE_MAP_FRG_ADD_LVL_TWO);
*/
        if (getActivity() != null) {
            getActivity().startActivityForResult(intent, ProvidersApp.REQUEST_CODE_CHOOSE_MAP_FRG_ADD_LVL_TWO);

        }

    }


    private void getInfoLocation(LatLng latLng) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                    if (addresses != null && addresses.size() > 0) {
                        // countryName = addresses.get(0).getCountryName();
                        provinceName = addresses.get(0).getAdminArea();
                        cityName = addresses.get(0).getLocality();


                        String detailCity = "";
                        if (!provinceName.equals("") && !cityName.equals("")) {

                            detailCity = provinceName + " ، " + cityName;

                        } else if (!provinceName.equals("") || !cityName.equals("")) {
                            if (!provinceName.equals("")) {
                                detailCity = provinceName;
                            }
                            if (!cityName.equals("")) {
                                detailCity = cityName;
                            }
                        } else {
                            detailCity = "دور از محدوده";
                        }


                        chooseMapTextView.setText(detailCity);

                    }


                } catch (IOException e) {
                    //e.printStackTrace();
                    Log.i(TAG, "getInfoLocation: " + e.toString());
                }
            }
        }, 100);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_level_two_cardView_chooseCity:
                //startActivityForResult(new Intent(context,CityChooseActivity.class), ProvidersApp.REQUEST_CODE_CHOOSE_CITY_FRG_ADD_LVL_TWO);
                Intent intent = new Intent(context, CityChooseActivity.class);
                intent.putExtra(ProvidersApp.KEY_CHOOSE_CITY_FRG_ADD_LVL_TWO, ProvidersApp.REQUEST_CODE_CHOOSE_CITY_FRG_ADD_LVL_TWO);
                if (getActivity() != null) {
                    getActivity().startActivityForResult(intent, ProvidersApp.REQUEST_CODE_CHOOSE_CITY_FRG_ADD_LVL_TWO);
                }

                break;
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setIndoorEnabled(true);

        //LatLng abadan = new LatLng(30.3473, 48.2934);
        LatLng iran = new LatLng(32.4279, 53.6880);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(iran, 4.0f));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                getNeedPermissions();

            }
        });
    }
}
