package com.ali.rnp.khodemon;

/*
Start Project
at 1397,10,29 Sunday
This Project Started for Achieve our dreams
managers : ali , raana
 */

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import io.fabric.sdk.android.Fabric;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ali.rnp.khodemon.DataModel.LocationCity;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.Views.Activites.CityChoose;
import com.ali.rnp.khodemon.Views.SplashScreen;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.crash.FirebaseCrash;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private AHBottomNavigation bottomNavigation;
    private Toolbar toolbar;
    private MyTextView cityName;
    private ImageView cityNameImg;
    private LinearLayout cityLinearLayout;
    private ActionBarDrawerToggle drawerToggle;
    private SharedPrefManager sharedPrefManager;


    private static final int REQUEST_CODE_GET_CITY = 501;

    private static final String TAG = "MainActivityLogcat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        SetupBottomNavigation();
        SetupToolbar();
        SetupCityFromSharedPref();

        FirebaseCrash.log("Activity created with CrashReporter");

        FirebaseCrash.logcat(Log.ERROR, TAG, "NPE caught");

        Fabric.with(this, new Crashlytics());

    }

    private void SetupCityFromSharedPref() {

        SharedPrefManager sharedPrefManager = new SharedPrefManager(this);
        LocationCity locationCity = sharedPrefManager.getSharedCity();

        cityName.setText(locationCity.getCityName());
    }


    private void SetupToolbar() {

        toolbar = findViewById(R.id.mainActivity_toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        DrawerLayout drawerLayout = findViewById(R.id.mainActivity_drawer_layout);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        drawerToggle.syncState();


    }

    private void SetupBottomNavigation() {

        bottomNavigation = findViewById(R.id.bottom_navigation);

        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.bottom_navigation_tab_heart, R.drawable.ic_heart, R.color.blue_600);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.bottom_navigation_tab_search, R.drawable.ic_magnifying_glass, R.color.blue_600);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.bottom_navigation_tab_add, R.drawable.ic_add, R.color.blue_600);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.bottom_navigation_tab_user, R.drawable.ic_avatar, R.color.blue_600);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(R.string.bottom_navigation_tab_home, R.drawable.ic_house, R.color.blue_600);

// Add items
        bottomNavigation.addItem(item5);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item1);

        // bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
        bottomNavigation.setDefaultBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.background_01, null));
        bottomNavigation.setAccentColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
        bottomNavigation.setInactiveColor(ResourcesCompat.getColor(getResources(), R.color.inactive_01, null));
        bottomNavigation.setNotificationBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.notification_color, null));

        // Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);


        // Manage titles
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

        bottomNavigation.setCurrentItem(0);

        bottomNavigation.setNotification("1", 4);

        bottomNavigation.setUseElevation(true);

        // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                switch (position) {
                    case 4:
                        bottomNavigation.setNotification("", position);
                        break;
                }
                return true;
            }
        });

    }

    private void initViews() {


        cityName = findViewById(R.id.main_activity_city_name_txt);
        cityNameImg = findViewById(R.id.main_activity_city_name_img);
        cityLinearLayout = findViewById(R.id.main_activity_city_LinearLayout);

        cityLinearLayout.setOnClickListener(this);


        sharedPrefManager = new SharedPrefManager(MainActivity.this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.main_activity_city_LinearLayout:
                startActivityForResult(new Intent(MainActivity.this, CityChoose.class), REQUEST_CODE_GET_CITY);
                break;

        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {

            case REQUEST_CODE_GET_CITY:

                if (resultCode == RESULT_OK && data != null) {
                    final int cityId=data.getIntExtra(CityChoose.INTENT_CITY_ID,1);
                    final String cityNameString=data.getStringExtra(CityChoose.INTENT_CITY_NAME);
                    final String cityProvinceNameString=data.getStringExtra(CityChoose.INTENT_CITY_PROVINCE_NAME);

                    cityName.setText(cityNameString);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LocationCity locationCity = new LocationCity();
                            locationCity.setCityId(cityId);
                            locationCity.setCityName(cityNameString);
                            locationCity.setCityProvinceName(cityProvinceNameString);
                            sharedPrefManager.setSharedCity(locationCity);
                        }
                    });

                }

                break;
        }
    }


}
