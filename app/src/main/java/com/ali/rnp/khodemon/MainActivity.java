package com.ali.rnp.khodemon;

/*
Start Project
at 1397,10,29 Sunday
This Project Started for Achieve our dreams
managers : ali , raana
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;

public class MainActivity extends AppCompatActivity {


    private AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews ();

        SetupBottomNavigation();
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
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);

        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
        bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));

        // Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);


   // Manage titles
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);

        bottomNavigation.setCurrentItem(4);


        bottomNavigation.setNotification("1", 0);



        // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position){
                    case 0 :
                        bottomNavigation.setNotification("", position);
                        break;
                }
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override public void onPositionChange(int y) {
                // Manage the new y position
            }
        });

    }

    private void initViews() {
    }
}
