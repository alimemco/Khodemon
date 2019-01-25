package com.ali.rnp.khodemon;

/*
Start Project
at 1397,10,29 Sunday
This Project Started for Achieve our dreams
managers : ali , raana
 */

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class MainActivity extends AppCompatActivity {


    private AHBottomNavigation bottomNavigation;
    private Toolbar toolbar;

    private static final String TAG = "MainActivityLogcat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews ();

        SetupBottomNavigation();
        SetupToolbar();
        SetupStatusBarColor();
    }

    private void SetupStatusBarColor() {

            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                Window window = this.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(this,R.color.backgroundApp));

            }
    }

    private void SetupToolbar() {

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        DrawerLayout drawerLayout = findViewById(R.id.mainActivity_drawer_layout);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,0,0);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this,R.color.colorPrimary));
        drawerToggle.syncState();



    }

    private void SetupBottomNavigation() {

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

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
        bottomNavigation.setDefaultBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.background_01,null));
        bottomNavigation.setAccentColor(ResourcesCompat.getColor(getResources(),R.color.colorPrimary,null));
        bottomNavigation.setInactiveColor(ResourcesCompat.getColor(getResources(),R.color.inactive_01,null));
        bottomNavigation.setNotificationBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.notification_color,null));

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

                switch (position){
                    case 4 :
                        bottomNavigation.setNotification("", position);
                        break;
                }
                return true;
            }
        });

    }

    private void initViews() {

        toolbar = findViewById(R.id.mainActivity_toolbar);
    }
}
