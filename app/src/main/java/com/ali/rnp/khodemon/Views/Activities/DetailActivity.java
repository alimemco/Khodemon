package com.ali.rnp.khodemon.Views.Activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.ScreenSlidePagerAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.google.android.material.appbar.AppBarLayout;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class DetailActivity extends AppCompatActivity implements
        View.OnClickListener {


    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    private Toolbar toolbar;

    private AppBarLayout appBarLayout;
    private MyTextView textView;
    private ImageView threeLineImageView;


    WormDotsIndicator wormDotsIndicator;
    DotsIndicator dotsIndicator;
    ArrayList<String> imgAddressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initViews();
        initBundle();
        initToolbar();
        initViewPager();
/*
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int post_id = extras.getInt(ProvidersApp.KEY_POST_ID);

            ApiService apiService = new ApiService(this);

            apiService.getPicture(post_id, (imgAddressList, error) -> {
                DetailActivity.this.imgAddressList = imgAddressList;

                if (imgAddressList != null && error == null){
                    initViewPagerOnline();
                }else {
                    Toast.makeText(DetailActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }




            });

        }*/

        appBarLayout = findViewById(R.id.activity_detail_appbar);
        textView = findViewById(R.id.activity_detail_textView);
/*
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {


                int maxScroll = appBarLayout.getTotalScrollRange();
                float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

                StringBuilder str = new StringBuilder();
                str.append("max Scroll: ");
                str.append(maxScroll);
                str.append("\n percentage: ");
                str.append(String.valueOf(percentage));
                str.append("\n verticalOffset: ");
                str.append(String.valueOf(verticalOffset));
                textView.setText(str);
            }
        });

*/


    }

    private void initBundle() {

        Bundle extras = getIntent().getExtras();

        if (extras != null) {


            imgAddressList = new ArrayList<>();

            imgAddressList.add("R.drawable.holder_banner");
            mPager = findViewById(R.id.pager);

            pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), imgAddressList);
            mPager.setAdapter(pagerAdapter);

            wormDotsIndicator = findViewById(R.id.worm_dots_indicator);
            wormDotsIndicator.setViewPager(mPager);

            dotsIndicator = findViewById(R.id.dots_indicator);
            dotsIndicator.setViewPager(mPager);


            int post_id = extras.getInt(ProvidersApp.KEY_POST_ID);

            ApiService apiService = new ApiService(this);

            apiService.getPicture(post_id, (imgAddressList, error) -> {
                DetailActivity.this.imgAddressList = imgAddressList;

                if (imgAddressList != null && error == null) {
                    initViewPagerOnline();
                } else {
                    Toast.makeText(DetailActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }


            });

        }

/*
        ImageView imageView = (ImageView) findViewById(R.id.animal_detail_image_view);
        TextView textView = (TextView) findViewById(R.id.animal_detail_text);
        textView.setText(animalItem.detail);
*/


    }

    private void initViews() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.activity_detail_toolbar);
        threeLineImageView = findViewById(R.id.activity_detail_toolbar_3line);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            setSupportActionBar(toolbar);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        threeLineImageView.setOnClickListener(this);

    }

    private void initViewPager() {
        imgAddressList = new ArrayList<>();

        imgAddressList.add("R.drawable.holder_banner");

        mPager = findViewById(R.id.pager);

        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), imgAddressList);
        mPager.setAdapter(pagerAdapter);

        wormDotsIndicator = findViewById(R.id.worm_dots_indicator);
        wormDotsIndicator.setViewPager(mPager);

        dotsIndicator = findViewById(R.id.dots_indicator);
        dotsIndicator.setViewPager(mPager);
    }

    private void initViewPagerOnline() {


        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), imgAddressList);
        mPager.setAdapter(pagerAdapter);

        wormDotsIndicator.setViewPager(mPager);

        dotsIndicator.setViewPager(mPager);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_detail_toolbar_3line:
                Toast.makeText(this, "three line Clicked..", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}