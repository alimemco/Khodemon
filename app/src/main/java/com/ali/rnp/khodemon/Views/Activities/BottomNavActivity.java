package com.ali.rnp.khodemon.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ali.rnp.khodemon.MyApplication;
import com.ali.rnp.khodemon.R;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        BottomNavigationViewEx btnEx = findViewById(R.id.activity_nav_btn);

        btnEx.setTypeface(MyApplication.getShpIranSansMoblie(this));

        btnEx.enableItemShiftingMode(true);
        btnEx.enableShiftingMode(false);
        btnEx.enableAnimation(true);

        int centerPosition = 2;
        btnEx.setIconSizeAt(centerPosition, 28, 28);
        btnEx.setTextSize(10);
        btnEx.setIconMarginTop(centerPosition, BottomNavigationViewEx.dp2px(this, 4));
        btnEx.setItemBackground(centerPosition, R.color.gray_100);

        btnEx.setIconTintList(centerPosition,
                getResources().getColorStateList(R.color.colorPrimaryDark));






    }
}
