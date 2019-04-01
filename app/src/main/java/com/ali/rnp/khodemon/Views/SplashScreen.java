package com.ali.rnp.khodemon.Views;

import android.content.Intent;
import android.os.Bundle;

import com.ali.rnp.khodemon.Views.Activities.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        startActivity(new Intent(SplashScreen.this, MainActivity.class));
        finish();

    }
}
