package com.ali.rnp.khodemon.Views.Activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.ScreenSlidePagerAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.PictureUpload;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.UtilsApp.StatusBarUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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


   // WormDotsIndicator wormDotsIndicator;
    DotsIndicator dotsIndicator;
    //ArrayList<String> imgAddressList;
    private ArrayList<PictureUpload> pictureUploadList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initStatusBar();
        initViews();
        initToolbar();








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



    private void initViews() {

        appBarLayout = findViewById(R.id.activity_detail_appbar);
        textView = findViewById(R.id.activity_detail_textView);

        mPager = findViewById(R.id.pager);
        dotsIndicator = findViewById(R.id.dots_indicator);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

/*
            pictureUploadList = new ArrayList<>();
            PictureUpload pictureUpload = new PictureUpload();
            pictureUpload.setPic_address("R.drawable.holder_banner");
            pictureUploadList.add(pictureUpload);
*/


           // pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), pictureUploadList);
           // mPager.setAdapter(pagerAdapter);
            //TODO change first init

           // wormDotsIndicator = findViewById(R.id.worm_dots_indicator);
           // wormDotsIndicator.setViewPager(mPager);


           // dotsIndicator.setViewPager(mPager);


            int post_id = extras.getInt(ProvidersApp.KEY_POST_ID);

            ApiService apiService = new ApiService(this);

            apiService.getPicture(post_id,(pictureUploadList, error) -> {

                if (pictureUploadList != null && error == null) {
                    initViewPagerOnline(pictureUploadList);
                } else {
                    Toast.makeText(DetailActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            });


            apiService.getDetail(post_id,(locationPeople, error) -> {
                if (locationPeople != null && error == null) {
                   // Toast.makeText(this, ""+convertFormat(locationPeople.getTimeReg()), Toast.LENGTH_LONG).show();
               textView.setText(convertFormat(locationPeople.getTimeReg()));

                } else {
                    Toast.makeText(DetailActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            });

        }



    }

    private void initStatusBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        StatusBarUtil.setTranslucent(DetailActivity.this);

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


        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
        p.setMargins(0, getStatusBarHeight(), 0, 0);
        toolbar.requestLayout();

        threeLineImageView.setOnClickListener(this);

    }

    private void initViewPagerOnline(ArrayList<PictureUpload> pictureUploadList) {


        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), pictureUploadList);
        mPager.setAdapter(pagerAdapter);

       // wormDotsIndicator.setViewPager(mPager);

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

   /* public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }*/

    private int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return getResources().getDimensionPixelSize(resourceId);
    }

    public static String convertFormat(String inputDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());

        Date date = null;
        try {
            date = simpleDateFormat.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (date == null) {
            return "";
        }

        SimpleDateFormat convertDateFormatYear = new SimpleDateFormat("yyyy",Locale.getDefault());
        String year = convertDateFormatYear.format(date);

        SimpleDateFormat convertDateFormatHour = new SimpleDateFormat("hh:mm:ss",Locale.getDefault());
        String hour = convertDateFormatHour.format(date);
        return year+"   "+hour;
    }

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }


}