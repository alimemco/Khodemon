package com.ali.rnp.khodemon;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";
    private BottomNavigationView bottomNavigationView;
    private MyTextView textView ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        bottomNavigationView = findViewById(R.id.activity_test_bottomNav);
        textView = findViewById(R.id.activity_test_textView);


        notificationBottomNav(3,"5");
        notificationBottomNav(1,"new");

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.item_1:
                    setTextToMyText("item_1");
                    break;

                case R.id.item_2:
                    setTextToMyText("item_2");
                    break;

                case R.id.item_3:
                    setTextToMyText("item_3");
                    break;

                case R.id.item_4:
                    setTextToMyText("item_4");
                    notificationBottomNav(3,"");
                    break;

                case R.id.item_5:
                    setTextToMyText("item_5");
                    break;


            }
            return true;
        });


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.setSelectedItemId(R.id.item_5);
            }
        });



    }

    private void notificationBottomNav(int position , String text){
        if (text.equals("")){

            Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();
            BottomNavigationMenuView bottomNavigationMenuView =
                    (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
            View v = bottomNavigationMenuView.getChildAt(position);
            BottomNavigationItemView itemView = (BottomNavigationItemView) v;

            View badge = LayoutInflater.from(this)
                    .inflate(R.layout.notification_badge_layout, itemView, true);

        }else {
            BottomNavigationMenuView bottomNavigationMenuView =
                    (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
            View v = bottomNavigationMenuView.getChildAt(position);
            BottomNavigationItemView itemView = (BottomNavigationItemView) v;

            View badge = LayoutInflater.from(this)
                    .inflate(R.layout.notification_badge_layout, itemView, true);

            TextView textView = badge.findViewById(R.id.notifications_badge_text);
            textView.setText(text);
        }

    }

    private void setTextToMyText(String text){
        textView.setText(text);
    }


}