package com.ali.rnp.khodemon.Views.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.ScaleAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.Info;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;

import java.util.ArrayList;

public class ScaleActivity extends AppCompatActivity implements
        ApiService.OnReceivedInfo,
        ScaleAdapter.OnAddScaleClick {

    private  ScaleAdapter scaleAdapter;
    private String IMAGE;
    private LocationPeople locPeoPost;
    private LocationPeople locPeoScale;
    //private ArrayList<Info> infoListScale;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale);

        initToolbar();
        int post_id = getIntent().getIntExtra((ProvidersApp.KEY_POST_ID),1);
       // IMAGE = getIntent().getStringExtra(ProvidersApp.KEY_IMG);

        locPeoPost = getIntent().getParcelableExtra(ProvidersApp.KEY_LOCATION_PEOPLE);

        ApiService apiService = new ApiService(this);
        apiService.getInfo(true,post_id,locPeoPost.getGroup(),this);
    }

    private void initToolbar() {

        toolbar = findViewById(R.id.activity_scale_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }


    }


    @Override
    public void OnReceived(boolean isFirstScale , int statusCode, ArrayList<Info> infoList, String error) {
        switch (statusCode) {
            case ProvidersApp.STATUS_CODE_SUCCESSFULLY:

                if (infoList != null) {

                    if (isFirstScale){
                       // infoListScale = infoList;

                        RecyclerView ScaleRcv = findViewById(R.id.activity_scale_rcv);
                        LinearLayoutManager ln = new LinearLayoutManager(ScaleActivity.this, RecyclerView.VERTICAL, false);
                        scaleAdapter = new ScaleAdapter(infoList,locPeoPost);
                        scaleAdapter.setOnAddScaleClick(this);
                        ScaleRcv.setLayoutManager(ln);
                        ScaleRcv.setAdapter(scaleAdapter);
                    }else {
                        scaleAdapter.setSaleSecond(infoList,locPeoScale);
                    }


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
    public void OnAddScale() {
        Intent i = new Intent(this,ChooseScaleActivity.class);
        i.putExtra(ProvidersApp.GROUP_NAME,locPeoPost.getGroup());
        startActivityForResult(i,ProvidersApp.REQUEST_CODE_CHOOSE_SCALE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ProvidersApp.REQUEST_CODE_CHOOSE_SCALE && resultCode == RESULT_OK){
            if (data != null){
                locPeoScale = data.getParcelableExtra(ProvidersApp.KEY_LOCATION_PEOPLE);
                ApiService apiService = new ApiService(ScaleActivity.this);
                apiService.getInfo(false , locPeoScale.getId(),locPeoPost.getGroup(), this);

            }

        }
    }
}
