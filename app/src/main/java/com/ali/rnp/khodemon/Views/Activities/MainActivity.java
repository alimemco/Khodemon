package com.ali.rnp.khodemon.Views.Activities;

/*
Start Project
at 1397,10,29 Sunday
This Project Started for Achieve our dreams
managers : ali , raana
 */

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.Dialogs.DialogCompleteAdd;
import com.ali.rnp.khodemon.DiscreteScrollView.WeatherActivity;
import com.ali.rnp.khodemon.Interface.OnLoginListener;
import com.ali.rnp.khodemon.MyApplication;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.SharedPrefManager;
import com.ali.rnp.khodemon.TestActivity;
import com.ali.rnp.khodemon.Views.fragments.FragmentAdd;
import com.ali.rnp.khodemon.Views.fragments.FragmentDialog;
import com.ali.rnp.khodemon.Views.fragments.FragmentFavorite;
import com.ali.rnp.khodemon.Views.fragments.FragmentHome;
import com.ali.rnp.khodemon.Views.fragments.FragmentSearch;
import com.ali.rnp.khodemon.Views.fragments.FragmentSearchTwo;
import com.ali.rnp.khodemon.Views.fragments.FragmentUser;
import com.ali.rnp.khodemon.Views.fragments.FragmentUserInfo;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        OnLoginListener,
FragmentUserInfo.OnLogOut{


    private AHBottomNavigation bottomNavigation;
    private MyTextView cityName;
    private ImageView imageView;
    private NavigationView navigationView;
    private SharedPrefManager sharedPrefManager;
    //private FrameLayout frameLayout;

    private FragmentManager fragmentManager;
    private FragmentHome fragmentHome;
    private FragmentUser fragmentUser;
    private FragmentAdd fragmentAdd;
    private FragmentSearchTwo fragmentSearch;
    private FragmentFavorite fragmentFavorite;

    private static final int BOTTOM_NAV_ITEM_HOME=0;
    public static final int BOTTOM_NAV_ITEM_SEARCH=1;
    private static final int BOTTOM_NAV_ITEM_ADD=2;
    private static final int BOTTOM_NAV_ITEM_FAVORITE =3;
    private static final int BOTTOM_NAV_ITEM_USER=4;



    private static final int REQUEST_CODE_GET_CITY = 501;

    private static final String TAG = "MainActivityLogcat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();


        SetupFragments();
        SetupBottomNavigation();
        SetupToolbar();
        SetupCityFromSharedPref();
        SetupNavigationView();

        Fabric.with(this, new Crashlytics());

        FcmSetup();





    }

    private void SetupNavigationView() {
        navigationView = findViewById(R.id.mainActivity_navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.navigation_menu_userManagement:
                        startActivity(new Intent(MainActivity.this,testUnit.class));

                        break;

                    case R.id.navigation_menu_about:
                       // Toast.makeText(MainActivity.this, item.getDescription(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, WeatherActivity.class));



                        break;

                    case R.id.navigation_menu_places:
                        startActivity(new Intent(MainActivity.this,TestPlace.class));

                        break;

                    case R.id.navigation_menu_bottomNav:
                        startActivity(new Intent(MainActivity.this,TestActivity.class));

/*
                        DialogCompleteAdd dialogCompleteAdd = new DialogCompleteAdd();

                        dialogCompleteAdd.show(fragmentManager, "DialogFragment");
                        break;
*/
                        case R.id.navigation_menu_hour_picker:
                        startActivity(new Intent(MainActivity.this,HoursChooseActivity.class));

                        break;



                    case R.id.navigation_menu_Expandable:

                        break;

                        case R.id.navigation_menu_googleMap:
                        startActivity(new Intent(MainActivity.this, GoogleMapsActivity.class));

                        break;

                        case R.id.navigation_menu_chooseCity:

                        //startActivity(new Intent(MainActivity.this, CityChooseActivityNew.class));
                       // showDialog();
                            DialogCompleteAdd dialogCompleteAdd = new DialogCompleteAdd();

                            dialogCompleteAdd.show(fragmentManager, "DialogFragment");
                        break;

                        case R.id.navigation_menu_btv:

                    
                        break;


                }

                return false;
            }
        });
    }


    private void SetupFragments() {

        fragmentManager = getSupportFragmentManager();


        fragmentHome = FragmentHome.newInstance();
        fragmentUser = new FragmentUser();
        fragmentAdd = new FragmentAdd();
       // fragmentSearch = new FragmentSearch();
        fragmentSearch = new FragmentSearchTwo();
        fragmentFavorite = new FragmentFavorite();


        FragmentTransaction transactionHome = fragmentManager.beginTransaction();
        transactionHome.replace(R.id.mainActivity_fragment_container, fragmentHome);
        transactionHome.commit();
    }

    private void FcmSetup() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }


        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }


        FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        Log.d(TAG, msg);
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                       // Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void SetupCityFromSharedPref() {

        SharedPrefManager sharedPrefManager = new SharedPrefManager(this);
        LocationPeople locationPeople = sharedPrefManager.getSharedCity();

        cityName.setText(locationPeople.getCity());
    }


    private void SetupToolbar() {

        Toolbar toolbar = findViewById(R.id.mainActivity_toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        DrawerLayout drawerLayout = findViewById(R.id.mainActivity_drawer_layout);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        drawerToggle.syncState();


    }

    private void SetupBottomNavigation() {

        bottomNavigation = findViewById(R.id.bottom_navigation);

        // Create items
        AHBottomNavigationItem itemHeart = new AHBottomNavigationItem(R.string.bottom_navigation_tab_heart, R.drawable.ic_heart, R.color.blue_600);
        AHBottomNavigationItem itemSearch = new AHBottomNavigationItem(R.string.bottom_navigation_tab_search, R.drawable.ic_magnifying_glass, R.color.blue_600);
        AHBottomNavigationItem itemAdd = new AHBottomNavigationItem(R.string.bottom_navigation_tab_add, R.drawable.ic_add, R.color.blue_600);
        AHBottomNavigationItem itemUser = new AHBottomNavigationItem(R.string.bottom_navigation_tab_user, R.drawable.ic_avatar, R.color.blue_600);
        AHBottomNavigationItem itemHome = new AHBottomNavigationItem(R.string.bottom_navigation_tab_home, R.drawable.ic_house, R.color.blue_600);

// Add items
        bottomNavigation.addItem(itemHome);
        bottomNavigation.addItem(itemSearch);

        bottomNavigation.addItem(itemAdd);

        bottomNavigation.addItem(itemHeart);
        bottomNavigation.addItem(itemUser);

        bottomNavigation.setDefaultBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.background_01, null));
        bottomNavigation.setAccentColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
        bottomNavigation.setInactiveColor(ResourcesCompat.getColor(getResources(), R.color.inactive_01, null));
        bottomNavigation.setNotificationBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.notification_color, null));

        // Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);

        bottomNavigation.setBehaviorTranslationEnabled(true);



        // Manage titles
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

        bottomNavigation.setTitleTypeface(MyApplication.getShpIranSansMoblie(MainActivity.this));

        bottomNavigation.setCurrentItem(BOTTOM_NAV_ITEM_HOME);

        bottomNavigation.setNotification("1", BOTTOM_NAV_ITEM_FAVORITE);
        bottomNavigation.setNotification("new", BOTTOM_NAV_ITEM_USER);



        bottomNavigation.setUseElevation(true);

        // Set listeners
        bottomNavigation.setOnTabSelectedListener((position, wasSelected) -> {

            if (!wasSelected) {

                clearFragmentBackStack();

                switch (position) {
                    case BOTTOM_NAV_ITEM_HOME:
                        fragmentReplace(fragmentHome,"FragmentHome");
                        break;

                    case BOTTOM_NAV_ITEM_USER:
                        String userName = sharedPrefManager.getUser().getUserName();
                        if (userName.equals("")){
                            fragmentReplace(fragmentUser,"FragmentUser");

                            fragmentUser.setOnLoginListener(MainActivity.this);




                        }else {
                            FragmentUserInfo fragmentUserInfo = FragmentUserInfo.newInstance(userName);
                            fragmentReplace(fragmentUserInfo,"FragmentUserInfo");

                        }

                        break;

                    case BOTTOM_NAV_ITEM_ADD:
                        fragmentReplace(fragmentAdd,"FragmentAdd");
                        break;

                    case BOTTOM_NAV_ITEM_SEARCH:
                         fragmentReplace(fragmentSearch,"FragmentSearch");
                        break;

                    case BOTTOM_NAV_ITEM_FAVORITE:
                        fragmentReplace(fragmentFavorite,"FragmentFavorite");
                        bottomNavigation.setNotification("", BOTTOM_NAV_ITEM_FAVORITE);
                        break;
                }
            }


            return true;
        });

        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override public void onPositionChange(int y) {
                Toast.makeText(MainActivity.this, "position"+y, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initViews() {


        cityName = findViewById(R.id.main_activity_city_name_txt);
        MyTextView titleToolbarTxt = findViewById(R.id.main_activity_toolbar_name_textView);
        titleToolbarTxt.setOnClickListener(this);
        LinearLayout cityLinearLayout = findViewById(R.id.main_activity_city_LinearLayout);

        cityLinearLayout.setOnClickListener(this);


        sharedPrefManager = new SharedPrefManager(MainActivity.this);




      imageView = findViewById(R.id.fragment_search_imageView);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.main_activity_city_LinearLayout:
                startActivityForResult(new Intent(MainActivity.this, CityChooseActivity.class), REQUEST_CODE_GET_CITY);
                break;

            case R.id.main_activity_toolbar_name_textView:
                startActivity(new Intent(MainActivity.this,TestActivity.class));
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK){

            switch (requestCode) {

                case REQUEST_CODE_GET_CITY:

                    if (data != null) {
                        final String cityNameString = data.getStringExtra(ProvidersApp.KEY_CITY_NAME);

                        cityName.setText(cityNameString);

                        runOnUiThread(() -> {
                            LocationPeople locationPeople = new LocationPeople();
                            locationPeople.setCity(cityNameString);
                            sharedPrefManager.setSharedCity(locationPeople);
                        });

                    }

                    break;


                case ProvidersApp.REQUEST_CODE_CHOOSE_CATEGORY:
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag("FragmentSearch");
                    if (fragment != null){
                        fragment.onActivityResult(requestCode,resultCode,data);
                    }
                    break;
            }
        }

    }

    private void DetectFragment(){
        Fragment frCurrent = getSupportFragmentManager().findFragmentById(R.id.mainActivity_fragment_container);

        if (!(frCurrent instanceof FragmentHome)){
            fragmentReplace(fragmentHome,"FragmentHome");
            bottomNavigation.setCurrentItem(0);
        }

    }

    private void fragmentReplace(Fragment fragment,String tag) {
        FragmentTransaction transactionFragment = fragmentManager.beginTransaction();
        transactionFragment.replace(R.id.mainActivity_fragment_container, fragment,tag);
        if (fragment instanceof FragmentUserInfo){
            ((FragmentUserInfo) fragment).setOnLogOutListener(MainActivity.this);
        }
        transactionFragment.commit();
    }


    boolean doubleBackToExitPressedOnce= false;

    @Override
    public void onBackPressed() {

        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();

        if (backStackEntryCount == 0) {

            goBackWithTimer();

        } else {

            super.onBackPressed();
        }

    }

    private void goBackWithTimer() {
        if (doubleBackToExitPressedOnce){
            super.onBackPressed();
            return;
        }
        DetectFragment();

        this.doubleBackToExitPressedOnce = true;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private void clearFragmentBackStack(){
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {

            fragmentManager.popBackStack();


        }
    }




    @Override
    public void onLogin(String userName) {
        FragmentUserInfo fragmentUserInfo = FragmentUserInfo.newInstance(userName);
        fragmentReplace(fragmentUserInfo,"FragmentUserInfo");
        Toast.makeText(this, "us "+userName, Toast.LENGTH_SHORT).show();

        fragmentUserInfo.setOnLogOutListener(this);
    }

    @Override
    public void OnLoginOut() {
        fragmentReplace(fragmentUser,"FragmentUser");
        Toast.makeText(this, "Log Out Person :D ", Toast.LENGTH_SHORT).show();
    }
}
