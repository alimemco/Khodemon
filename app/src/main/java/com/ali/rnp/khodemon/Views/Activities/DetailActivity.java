package com.ali.rnp.khodemon.Views.Activities;

import android.os.Bundle;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.ScreenSlidePagerAdapter;
import com.ali.rnp.khodemon.R;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class DetailActivity extends AppCompatActivity {


    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    private Toolbar toolbar;
    WormDotsIndicator wormDotsIndicator;
    DotsIndicator dotsIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initToolbar();
        initViewPager();


    }

    private void initToolbar() {
        toolbar = findViewById(R.id.activity_detail_toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null ){
            setSupportActionBar(toolbar);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void initViewPager() {
        List<String> imgAddressList = new ArrayList<>();
        imgAddressList.add("https://nex1music.ir/upload/ads/pouya-arzani-jane-man-67-header.jpg");
        imgAddressList.add("https://nex1music.ir/upload/2019-04-30/2019-04-30-11-17-09.jpg");
        imgAddressList.add("https://nex1music.ir/upload/2019-04-29/2019-04-29-18-48-25.jpg");
        imgAddressList.add("https://nex1music.ir/upload/2019-04-30/ali-derakhshan-az-khodetam-2019-04-30-13-21-18.jpg");
        imgAddressList.add("https://nex1music.ir/upload/2019-04-29/macan-band-2-ta-setareh-2019-04-29-19-04-49.jpg");

        mPager = findViewById(R.id.pager);

        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), imgAddressList);
        mPager.setAdapter(pagerAdapter);

        wormDotsIndicator = findViewById(R.id.worm_dots_indicator);
        wormDotsIndicator.setViewPager(mPager);

        dotsIndicator = findViewById(R.id.dots_indicator);
        dotsIndicator.setViewPager(mPager);
    }


    @Override
    public boolean onSupportNavigateUp() {
        Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        return super.onSupportNavigateUp();

    }
}