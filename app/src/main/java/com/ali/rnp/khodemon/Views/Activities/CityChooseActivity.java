package com.ali.rnp.khodemon.Views.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.rnp.khodemon.Adapter.CityAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.City;
import com.ali.rnp.khodemon.ExpandableSingleItems.AdapterSingleExp;
import com.ali.rnp.khodemon.ExpandableSingleItems.ChildExp;
import com.ali.rnp.khodemon.ExpandableSingleItems.SingleCheckItemsExp;
import com.ali.rnp.khodemon.MyLibrary.MyEditText;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class CityChooseActivity extends AppCompatActivity implements
        TextWatcher,
        ApiService.OnProvinceReceived {

    private RecyclerView rcv;
    private AdapterSingleExp adapterSingleExp;
    private MaterialProgressBar progressBar;

    private List<City> cityList;
    private List<SingleCheckItemsExp> makeSingleCheckParentList;


    private String provinceName;
    private String cityName;

    private static final String TAG = "CityChooseActivity";
    boolean isSave = true;
    private Bundle mBundle;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_choose);

        mBundle = savedInstanceState;

        initViews();
        setupToolbar();
        setupRecView();

        btn = findViewById(R.id.city_btn);
        
        


    }

    private void initViews() {
        progressBar = findViewById(R.id.activity_city_choose_progressBar);

        MyEditText searchEditText = findViewById(R.id.activity_city_choose_cityAutoCompleteTextView);
        searchEditText.addTextChangedListener(this);

        //  cityAdapter = new CityAdapter(CityChooseActivity.this);
    }

    public void sendCityData() {
        Intent intent = new Intent();
        intent.putExtra(ProvidersApp.KEY_CITY_NAME, cityName);
        intent.putExtra(ProvidersApp.KEY_PROVINCE_NAME, provinceName);
        setResult(RESULT_OK, intent);

        finish();

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.activity_city_choose_toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }


    private void setupRecView() {

        rcv = findViewById(R.id.activity_city_choose_recView);
        rcv.setLayoutManager(new LinearLayoutManager(this));

        ApiService apiService = new ApiService(CityChooseActivity.this);

        apiService.getProvince(this);


    }

    private void setupRecWithExpandable(List<SingleCheckItemsExp> makeSingleCheckParent) {

        adapterSingleExp = new AdapterSingleExp(makeSingleCheckParent);
        rcv.setAdapter(adapterSingleExp);

        adapterSingleExp.setChildClickListener((v, checked, group, childIndex) -> {
            adapterSingleExp.clearChoices();
            group.checkChild(childIndex);
            ChildExp childExp = (ChildExp) group.getItems().get(childIndex);

            provinceName = group.getTitle();
            cityName = childExp.getName();

            sendCityData();

        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSave) {


                    // onSaveInstanceState(mBundle);
                    saveList();

                    Log.i(TAG, "onClick: saveee");
                } else {

                    // onCreate(mBundle);
                    Log.i(TAG, "onClick: get");

                    restoreList();

                    if (mBundle != null) {

                        //  adapterSingleExp.onRestoreInstanceState(mBundle);

                    }

                }

                isSave = !isSave;


            }
        });

        if (mBundle != null) {

            adapterSingleExp.onRestoreInstanceState(mBundle);

        }


    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (s.toString().equals("")) {
            if (makeSingleCheckParentList != null) {
                setupRecWithExpandable(makeSingleCheckParentList);
            }


        } else {
            if (cityList != null) {

                filterCity(s.toString());
            }

        }

    }


    private void filterCity(String text) {

        List<City> filteredCity = new ArrayList<>();

        for (City s : cityList) {

            if (s.getCityName().contains(text)) {
                filteredCity.add(s);
            }
        }

        CityAdapter cityAdapter = new CityAdapter(filteredCity);
        rcv.setAdapter(cityAdapter);
    }


    @Override
    public void onReceived(List<SingleCheckItemsExp> makeSingleCheckParent, List<City> cities, VolleyError error) {
        progressBar.setVisibility(View.INVISIBLE);
        if (makeSingleCheckParent != null && cities != null && error == null) {

            cityList = new ArrayList<>();
            cityList.addAll(cities);

            makeSingleCheckParentList = new ArrayList<>();
            makeSingleCheckParentList.addAll(makeSingleCheckParent);

            setupRecWithExpandable(makeSingleCheckParent);


        } else if (error != null) {
            Toast.makeText(CityChooseActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        adapterSingleExp.onSaveInstanceState(outState);


    }

    private void saveList() {
        mBundle = new Bundle();
        // mBundle.putParcelableArrayList("ali", new ArrayList(adapterSingleExp.getGroups()));
        adapterSingleExp.onSaveInstanceState(mBundle);
    }

    private void restoreList() {
        if (mBundle == null || !mBundle.containsKey("child_check_controller_checked_state_map")) {
            Log.i(TAG, "restoreList: null");
        } else {
            adapterSingleExp = new AdapterSingleExp(mBundle.getParcelableArrayList("child_check_controller_checked_state_map"));
            rcv.setAdapter(adapterSingleExp);
            Log.i(TAG, "restoreList: ");

        }


    }



}
