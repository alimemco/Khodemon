package com.ali.rnp.khodemon;

import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

    MeowBottomNavigation meowBottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationViewEx bnve = findViewById(R.id.activity_test_btn);
        String status = "";
        if (bnve != null){
            status = "OK";
            bnve.setCurrentItem(0);
        }else {
            status = "ERROR";
        }
        Toast.makeText(this, status, Toast.LENGTH_SHORT).show();

        BottomNavigationView btnNav = findViewById(R.id.testActivityNav);

/*
        bnve.setCurrentItem(2);

        bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Toast.makeText(TestActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        meowBottomNavigation = findViewById(R.id.MeowBottomNavigation);
        MeowBottomNavigation.Model model =  MeowBottomNavigation.Model(1,R.drawable.ic_heart);
        meowBottomNavigation.add(model);

        meowBottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_magnifying_glass));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_add));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_avatar));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.ic_house));

        meowBottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                Toast.makeText(TestActivity.this, ""+model.getId(), Toast.LENGTH_SHORT).show();
                return null;
            }
        });

*/

    }





}