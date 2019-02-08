package com.ali.rnp.khodemon.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.Activites.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        startActivity(new Intent(SplashScreen.this, MainActivity.class));
        finish();

    }
}
