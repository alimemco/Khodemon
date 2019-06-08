package com.ali.rnp.khodemon.Views.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.InfoAdapter;
import com.ali.rnp.khodemon.Adapter.PersonnelAdapter;
import com.ali.rnp.khodemon.Adapter.ScreenSlidePagerAdapter;
import com.ali.rnp.khodemon.Adapter.SimilarAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.BottomSheet.BottomSheetAddPersonnel;
import com.ali.rnp.khodemon.DataModel.Info;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.DataModel.PictureUpload;
import com.ali.rnp.khodemon.Interface.OnButtonAddClick;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.UtilsApp.ConverterJalali;
import com.ali.rnp.khodemon.UtilsApp.StatusBarUtil;
import com.ali.rnp.khodemon.UtilsApp.UtilsApp;
import com.ali.rnp.khodemon.Views.fragments.FragmentBottomSheetCall;
import com.android.volley.VolleyError;
import com.google.android.material.appbar.AppBarLayout;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class DetailActivity extends AppCompatActivity implements
        View.OnClickListener,
        ApiService.OnPersonnelReceived,
        ApiService.OnReceivedInfo,
        ApiService.OnGetDetails,
        ApiService.OnGetPictures,
        ApiService.OnReceivedSimilar,
PersonnelAdapter.OnItemClickListener,
        OnButtonAddClick {


    private static final String TAG = "DetailActivityDebug";
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    private Toolbar toolbar;

    private AppBarLayout appBarLayout;
    private MyTextView nameLocPeoTV;
    private MyTextView tagTV;
    private ImageView threeLineImageView;

    private RatingBar ratingBar;
    private MyTextView ratingBarTextView;

    private MyTextView titleSimilarTv;

    private RecyclerView personnelRecyclerView;
    private RecyclerView similarRecyclerView;
    private RecyclerView infoRecyclerView;

    //ConstraintLayout constraintLayout;
    private int post_id;
    private LocationPeople locPeoPost;
    private String number;

    ApiService apiService;


    // WormDotsIndicator wormDotsIndicator;
    DotsIndicator dotsIndicator;
    //ArrayList<String> imgAddressList;
    // private ArrayList<PictureUpload> pictureUploadList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
/*
        ConverterJalali cj = new ConverterJalali();
        cj.GregorianToPersian(2019,6,20);
        Toast.makeText(this, cj.toString(), Toast.LENGTH_LONG).show();
        */
        initStatusBar();
        initViews();
        initRatingBar();
        initToolbar();
        initFloatActionButton();


        //constraintLayout = findViewById(R.id.constraintLayout4);
        // constraintLayout.setVisibility(View.INVISIBLE);
        //constraintLayout.setAlpha(0);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //UtilsApp.animMoveViewVisible(constraintLayout);
                //constraintLayout.animate().y(25f);


            }
        }, 2000);





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

    private void initFloatActionButton() {
        ImageView callIV = findViewById(R.id.activity_detail_call_imageView);
        ImageView scaleIV = findViewById(R.id.activity_detail_scale_imageView);

        callIV.setOnClickListener(v -> {
            FragmentBottomSheetCall fragmentBottomSheetCall = FragmentBottomSheetCall.newInstance(number);
            fragmentBottomSheetCall.show(getSupportFragmentManager(), "FragmentBottomSheetCall");
        });

        scaleIV.setOnClickListener(v -> {
            Intent i = new Intent(DetailActivity.this,ScaleActivity.class);
            i.putExtra(ProvidersApp.KEY_POST_ID,post_id);
           // i.putExtra(ProvidersApp.GROUP_NAME,wef);
            i.putExtra(ProvidersApp.KEY_LOCATION_PEOPLE,locPeoPost);
            startActivity(i);
        });
    }

    private void initRatingBar() {

        ratingBar = findViewById(R.id.activity_detail_ratingBar);
        ratingBarTextView = findViewById(R.id.activity_detail_ratingBar_textView);

        float randomRating = UtilsApp.randomFloat(0.0f, 5.0f);

        ratingBar.setRating(randomRating);
        ratingBar.setIsIndicator(true);


        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(randomRating));
        sb.append(" | ");
        sb.append(String.valueOf(UtilsApp.randomInteger(10, 200)));
        sb.append(" نفر");
        ratingBarTextView.setText(sb);


    }


    private void initViews() {

        appBarLayout = findViewById(R.id.activity_detail_appbar);
        nameLocPeoTV = findViewById(R.id.activity_detail_locPeoName_textView);
        tagTV = findViewById(R.id.activity_detail_tag_textView);
        similarRecyclerView = findViewById(R.id.activity_detail_recyclerView_similar);
        personnelRecyclerView = findViewById(R.id.activity_detail_job_recyclerView_personnel);
        infoRecyclerView = findViewById(R.id.activity_detail_recyclerView_info);
        titleSimilarTv = findViewById(R.id.activity_detail_textView_similar);


        mPager = findViewById(R.id.pager);
        dotsIndicator = findViewById(R.id.dots_indicator);
        dotsIndicator.setVisibility(View.INVISIBLE);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            locPeoPost = extras.getParcelable(ProvidersApp.KEY_LOCATION_PEOPLE);

            if(locPeoPost!= null){

                nameLocPeoTV.setText(locPeoPost.getName());
                tagTV.setText(locPeoPost.getTag());


                post_id = locPeoPost.getId();
                ApiService apiService = new ApiService(this);

                apiService.getPicture(post_id,this);
                apiService.getInfo(true,post_id, locPeoPost.getGroup(),this);
                apiService.getDetail(post_id,this);
                apiService.getSimilar(locPeoPost.getGroup(),this);

                if (locPeoPost.getGroup().equals(ProvidersApp.GROUP_NAME_LOCATION)){
                    apiService.getPersonnel(post_id, this);
                    titleSimilarTv.setText("مکان های مشابه");
                }else {
                    titleSimilarTv.setText("متخصص های مشابه");
                }
            }
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

    private void initViewPager(ArrayList<PictureUpload> pictureUploadList) {


        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), pictureUploadList);
        mPager.setAdapter(pagerAdapter);

        UtilsApp.animMoveViewVisible(dotsIndicator);

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

        SimpleDateFormat convertDateFormatYear = new SimpleDateFormat("yyyy", Locale.getDefault());
        String year = convertDateFormatYear.format(date);

        SimpleDateFormat convertDateFormatHour = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
        String hour = convertDateFormatHour.format(date);
        return year + "   " + hour;
    }


    @Override
    public void onItemReceived(int status, ArrayList<LocationPeople> locationPeopleList, String error) {

        String msg = "known";

        switch (status) {
            case ProvidersApp.KEY_SUCCESS:

                if (locationPeopleList != null) {

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailActivity.this, RecyclerView.VERTICAL, false);
                    personnelRecyclerView.setLayoutManager(linearLayoutManager);
                    PersonnelAdapter personnelAdapter = new PersonnelAdapter(DetailActivity.this, locationPeopleList);
                    personnelAdapter.setOnButtonAddClick(this);
                    personnelAdapter.setOnItemClickListener(this);
                    personnelRecyclerView.setAdapter(personnelAdapter);


                }

                break;

            case ProvidersApp.KEY_EMPTY_DATA:

                msg = "هیچ پرسنلی یافت نشد";


                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailActivity.this, RecyclerView.VERTICAL, false);
                personnelRecyclerView.setLayoutManager(linearLayoutManager);
                PersonnelAdapter personnelAdapter = new PersonnelAdapter(DetailActivity.this, true);
                personnelAdapter.setOnButtonAddClick(this);
                personnelRecyclerView.setAdapter(personnelAdapter);

                break;

            case ProvidersApp.KEY_JSON_EXCEPTION:
                msg = error;

                break;

            case ProvidersApp.KEY_VOLLEY_ERROR:
                msg = error;

                break;
        }

        if (status == ProvidersApp.KEY_VOLLEY_ERROR || status == ProvidersApp.KEY_JSON_EXCEPTION)
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == ProvidersApp.REQUEST_CODE_CHOOSE_PERSONNEL && data != null) {

            if (data.getExtras() != null) {
                int LOCATION_ID = data.getExtras().getInt(ProvidersApp.KEY_LOCATION_ID);
                ApiService apiService = new ApiService(DetailActivity.this);
                apiService.getPersonnel(LOCATION_ID, DetailActivity.this);
            }

        }

    }



    @Override
    public void OnAddClick(int rcvModel) {
        switch (rcvModel){
            case ProvidersApp.RECYCLER_VIEW_PERSONNEL:
                BottomSheetAddPersonnel btmShtAddPersonnel = BottomSheetAddPersonnel.newInstance(post_id);
                btmShtAddPersonnel.show(getSupportFragmentManager(),"BottomSheetAddPersonnel");
                break;

            case ProvidersApp.RECYCLER_VIEW_INFO:
                Toast.makeText(this, "INFO ADD", Toast.LENGTH_SHORT).show();
                break;
        }

    }


    @Override
    public void OnGetDetail(int statusCode, LocationPeople locationPeople, String error) {
        String ERR ="";
        switch (statusCode) {

            case ProvidersApp.STATUS_CODE_SUCCESSFULLY:

                if (locationPeople != null) {

                    number = locationPeople.getPhone();
                    Log.i(TAG, "OnGetDetail: "+number);

                }else {
                    Log.i(TAG, "OnGetDetail: NULL");
                }
                break;

            case ProvidersApp.STATUS_CODE_JSON_EXCEPTION_ERROR:
                ERR = "JSON_EXCEPTION_ERROR";
                break;
            case ProvidersApp.STATUS_CODE_VOLLEY_ERROR:
                ERR = "VOLLEY_ERROR";
                break;
            case ProvidersApp.STATUS_CODE_SERVER_ERROR:
                ERR = "SERVER_ERROR";
                break;

        }

        if (statusCode != ProvidersApp.STATUS_CODE_SUCCESSFULLY){
            Toast.makeText(this, ERR + " -> " + error, Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void OnReceived(boolean isFirstScale ,int statusCode, ArrayList<Info> infoList, String error) {
        switch (statusCode) {
            case ProvidersApp.STATUS_CODE_SUCCESSFULLY:

                if (infoList != null) {
                    LinearLayoutManager ln = new LinearLayoutManager(DetailActivity.this, RecyclerView.VERTICAL, false);
                    InfoAdapter infoAdapter = new InfoAdapter(infoList,locPeoPost.getGroup());
                    infoAdapter.setOnButtonAddClick(this);
                    infoRecyclerView.setLayoutManager(ln);
                    infoRecyclerView.setAdapter(infoAdapter);

                }
                break;

            case ProvidersApp.STATUS_CODE_JSON_EXCEPTION_ERROR:
            case ProvidersApp.STATUS_CODE_VOLLEY_ERROR:
            case ProvidersApp.STATUS_CODE_SERVER_ERROR:

                Toast.makeText(this, statusCode + " -> " + error, Toast.LENGTH_LONG).show();
                break;

        }
    }

    @Override
    public void OnGetPicture(ArrayList<PictureUpload> pictureUploadList, VolleyError error) {
        if (pictureUploadList != null && error == null) {
            initViewPager(pictureUploadList);


        } else {
            Toast.makeText(DetailActivity.this, error.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onReceivedSmr(int statusCode, ArrayList<LocationPeople> locationPeopleList, String error) {
        if (statusCode == ProvidersApp.STATUS_CODE_SUCCESSFULLY){


            SimilarAdapter similarAdapter = new SimilarAdapter(DetailActivity.this, locationPeopleList);
            LinearLayoutManager linearLayoutManagerSim = new LinearLayoutManager(
                    this, RecyclerView.HORIZONTAL, false);

            similarRecyclerView.setLayoutManager(linearLayoutManagerSim);
            similarRecyclerView.setAdapter(similarAdapter);


        }else {
            String msg = UtilsApp.statusCodeToError(statusCode,error);
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(LocationPeople locationPeople) {
        Intent i = new Intent(DetailActivity.this,DetailActivity.class);
        Toast.makeText(this, locationPeople.getName(), Toast.LENGTH_SHORT).show();
    }
}