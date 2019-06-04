package com.ali.rnp.khodemon.Views.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.ChoosePersonnelAdapter;
import com.ali.rnp.khodemon.Adapter.ChooseScaleAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;

import java.util.ArrayList;

public class ChooseScaleActivity extends AppCompatActivity implements
        ApiService.OnReceivedCategory ,
        ChooseScaleAdapter.OnItemClickListener {

    private RecyclerView rcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_scale);

        initView();
        initRCV();
    }



    private void initView() {
        rcv = findViewById(R.id.activity_choose_scale_rcv);

    }

    private void initRCV() {
        String GROUP_NAME = getIntent().getStringExtra(ProvidersApp.GROUP_NAME);
        ApiService apiService = new ApiService(this);
        apiService.getCategoryScale(GROUP_NAME,this);

    }


    @Override
    public void onItemClick(LocationPeople locationPeople) {

        Intent intent = new Intent();
       // intent.putExtra(ProvidersApp.KEY_LOCATION_ID,LOCATION_ID);
        intent.putExtra(ProvidersApp.KEY_LOCATION_PEOPLE,locationPeople);
        setResult(Activity.RESULT_OK,intent);
        finish();




    }

    @Override
    public void onReceived(int successCode, ArrayList<LocationPeople> locationPeopleList, String error) {

        String msg = "";
        switch (successCode){
            case ProvidersApp.STATUS_CODE_SUCCESSFULLY:

                if (locationPeopleList != null){

                    LinearLayoutManager ln = new LinearLayoutManager(ChooseScaleActivity.this, RecyclerView.VERTICAL,false);
                    StaggeredGridLayoutManager st = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
                    GridLayoutManager gd = new GridLayoutManager(ChooseScaleActivity.this,4,RecyclerView.VERTICAL,false);
                   rcv.setLayoutManager(gd);

                    ChooseScaleAdapter chooseScaleAdapter = new ChooseScaleAdapter( locationPeopleList);
                    chooseScaleAdapter.setOnItemClickListener(ChooseScaleActivity.this);
                    rcv.setAdapter(chooseScaleAdapter);
                }

                break;

            case ProvidersApp.STATUS_CODE_VOLLEY_ERROR:
                msg = successCode+" | "+error ;
                break;

            case ProvidersApp.STATUS_CODE_JSON_EXCEPTION_ERROR:
                msg = successCode+" | "+error ;
                break;

            case ProvidersApp.STATUS_CODE_SERVER_ERROR:
                msg = successCode+" | "+error ;
                break;

        }

        if (successCode!= ProvidersApp.STATUS_CODE_SUCCESSFULLY){
            Toast.makeText(this, msg , Toast.LENGTH_LONG).show();
        }
    }




}
