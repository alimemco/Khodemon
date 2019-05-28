package com.ali.rnp.khodemon.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import android.os.Bundle;
import android.util.Log;

import com.ali.rnp.khodemon.MyApplication;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavActivity extends AppCompatActivity {

    private static final String TAG = "BottomNavActivity";
    private MyTextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        text = findViewById(R.id.BottomNavigationTV);
        
        initMeowBottomNavigation();

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

    private void initMeowBottomNavigation() {


            MeowBottomNavigation meowBottomNavigation = findViewById(R.id.MeowBottomNavigation);
            meowBottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_heart));
            meowBottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_magnifying_glass));
            meowBottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_add));
            meowBottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_avatar));
            meowBottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.ic_house));


           // meowBottomNavigation.setCount(3,"2");
            meowBottomNavigation.show(5,false);

            meowBottomNavigation.setOnClickMenuListener(model -> {
               // text.setText(String.valueOf(model.getId()));

                switch (model.getId()){
                    case 1:
                        text.setText("heart");
                        break;

                    case 2:
                        text.setText("magnifying_glass");
                        break;

                    case 3:
                        text.setText("add");
                        break;

                    case 4:
                        text.setText("avatar");
                        break;

                    case 5:
                        text.setText("house");
                        break;
                }
                return null;
            });


        
    }
}
