package com.ali.rnp.khodemon;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.Library.CircularImageView;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class TestActivity extends AppCompatActivity {


    CircularImageView circularImageView;
    CircleImageView circleImageView;
    ImageView imageView;

    private static final String TAG = "TestActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        circularImageView = findViewById(R.id.imageView201);
        circleImageView = findViewById(R.id.imageView130);
        imageView = findViewById(R.id.imageView129);



        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // String url = "https://camo.githubusercontent.com/6cabd61bb7aef8d806561450f035509eae8710e0/687474703a2f2f6936372e74696e797069632e636f6d2f32696a316432722e6a7067";

                        ApiService apiService = new ApiService(TestActivity.this);

                        apiService.getPersonnel(new ApiService.OnPersonnelReceived() {
                            @Override
                            public void onItemReceived(ArrayList<LocationPeople> locationPeopleList, VolleyError error) {

                                String url = locationPeopleList.get(0).getOriginalPic();

                                Picasso.get().load(url)
                                        .placeholder(R.drawable.holder_banner)
                                        .resize(500,500)
                                        .centerCrop()
                                        .into(circularImageView);

                                Picasso.get().load(url)
                                        .placeholder(R.drawable.holder_banner)
                                        .resize(500,500)
                                        .centerCrop()
                                        .into(circleImageView);

                                Picasso.get().load(url)
                                        .placeholder(R.drawable.holder_banner)
                                        .resize(500,500)
                                        .centerCrop()
                                        .into(imageView);
                            }
                        });




                    }
                });


            }
        },3000);



    }





}