package com.ali.rnp.khodemon.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Window;
import android.view.WindowManager;

import com.ali.rnp.khodemon.DataModel.LocationCity;
import com.ali.rnp.khodemon.MainActivity;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.SharedPrefManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        startActivity(new Intent(SplashScreen.this,MainActivity.class));
        finish();

    }
}
