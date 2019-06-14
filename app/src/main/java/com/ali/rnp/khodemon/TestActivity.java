package com.ali.rnp.khodemon;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.Library.CircularImageView;
import com.ali.rnp.khodemon.Test.AliAsyncTask;
import com.ali.rnp.khodemon.Views.fragments.FragmentBottomSheetCall;
import com.android.volley.VolleyError;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class TestActivity extends AppCompatActivity {


    private static final String TAG = "TestActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

    }



}