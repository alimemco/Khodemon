package com.ali.rnp.khodemon.Views.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.CityAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.City;
import com.ali.rnp.khodemon.ExpandableSingleItems.AdapterSingleExp;
import com.ali.rnp.khodemon.ExpandableSingleItems.ChildExp;
import com.ali.rnp.khodemon.ExpandableSingleItems.SingleCheckItemsExp;
import com.ali.rnp.khodemon.MyLibrary.MyEditText;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.fragments.FragmentAddLevelTwo;
import com.android.volley.VolleyError;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnCheckChildClickListener;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class CityChooseActivity extends AppCompatActivity implements
        TextWatcher {

    private RecyclerView recyclerViewCity;
    private Toolbar toolbar;
    private MyEditText searchEditText;
    private AdapterSingleExp adapterSingleExp;
    private CityAdapter cityAdapter;
    private MaterialProgressBar progressBar;

    private List<City> cityList;
    private List<SingleCheckItemsExp> makeSingleCheckParentList;

    public static final String INTENT_CITY_ID = "city_id";
    public static final String INTENT_CITY_NAME = "city_name";
    public static final String INTENT_CITY_PROVINCE_NAME = "city_province_name";



    private boolean isFromFragmentAddTwo = false ;

    private String provinceName;
    private String cityName;

    private static final String TAG = "CityChooseActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_choose);


        initViews ();
        getDataFromIntent();
        setupToolbar();
        setupRecViewForCity();





        progressBar = findViewById(R.id.activity_city_choose_progressBar);

        searchEditText = findViewById(R.id.activity_city_choose_cityAutoCompleteTextView);
        searchEditText.addTextChangedListener(this);


    }

    private void initViews() {
        cityAdapter = new CityAdapter(CityChooseActivity.this);
    }

    private void getDataFromIntent() {

        int requestCode = getIntent().getIntExtra(ProvidersApp.KEY_CHOOSE_CITY_FRG_ADD_LVL_TWO,0);

        isFromFragmentAddTwo = requestCode == ProvidersApp.REQUEST_CODE_CHOOSE_CITY_FRG_ADD_LVL_TWO;

        cityAdapter.setIsFromFragmentAddTwo(isFromFragmentAddTwo);

    }




    public void sendCityData() {
        Intent intent = new Intent();
        //  intent.putExtra(CityChooseActivity.INTENT_CITY_ID, city.getId());
        intent.putExtra(ProvidersApp.KEY_CITY_NAME, cityName);
        intent.putExtra(ProvidersApp.KEY_PROVINCE_NAME, provinceName);
        // intent.putExtra(CityChooseActivity.INTENT_CITY_PROVINCE_NAME, city.getProvince());
        setResult(RESULT_OK, intent);
        /*

        if (isFromFragmentAddTwo){

            String name = provinceName+" ، "+cityName;
            FragmentAddLevelTwo.chooseCityTextView.setText(name);

            FragmentAddLevelTwo.provinceName = provinceName;
            FragmentAddLevelTwo.cityName = cityName;

        }else {
            Intent intent = new Intent(CityChooseActivity.this, MainActivity.class);
            //  intent.putExtra(CityChooseActivity.INTENT_CITY_ID, city.getId());
            intent.putExtra(CityChooseActivity.INTENT_CITY_NAME, cityName);
            // intent.putExtra(CityChooseActivity.INTENT_CITY_PROVINCE_NAME, city.getProvince());
            setResult(RESULT_OK, intent);
        }
*/

        finish();

    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.activity_city_choose_toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }



    private void setupRecViewForCity() {

        recyclerViewCity = findViewById(R.id.activity_city_choose_recView);
        recyclerViewCity.setLayoutManager(new LinearLayoutManager(this));



        ApiService apiService = new ApiService(CityChooseActivity.this);

        apiService.getProvince(new ApiService.OnProvinceReceived() {
            @Override
            public void onReceived(List<SingleCheckItemsExp> makeSingleCheckParent,List<City> cities, VolleyError error) {

                progressBar.setVisibility(View.INVISIBLE);
                if (makeSingleCheckParent != null && cities != null && error == null){

                    cityList = new ArrayList<>();
                    cityList.addAll(cities);

                    makeSingleCheckParentList = new ArrayList<>();
                    makeSingleCheckParentList.addAll(makeSingleCheckParent);

                    setupRecWithExpandable(makeSingleCheckParent);


                }else if (error != null){
                    Toast.makeText(CityChooseActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void setupRecWithExpandable(List<SingleCheckItemsExp> makeSingleCheckParent){
        adapterSingleExp = new AdapterSingleExp(makeSingleCheckParent, CityChooseActivity.this);
        recyclerViewCity.setAdapter(adapterSingleExp);

        adapterSingleExp.setChildClickListener(new OnCheckChildClickListener() {
            @Override
            public void onCheckChildCLick(View v, boolean checked, CheckedExpandableGroup group, int childIndex) {
                adapterSingleExp.clearChoices();
                group.checkChild(childIndex);
                ChildExp childExp = (ChildExp) group.getItems().get(childIndex);
               // Toast.makeText(CityChooseActivity.this, childExp.getName(), Toast.LENGTH_SHORT).show();

                provinceName = group.getTitle();
                cityName = childExp.getName();

                sendCityData();



            }
        });

    }
/*
    public void detectDataForFragment(OnCityChooseClick onCityChooseClick) {
        onCityChooseClick.OnCityClick(provinceName,cityName);
    }
*/
    private void setupRecForSearch(){

                    cityAdapter.setupCityAdapter(cityList);
                    recyclerViewCity.setAdapter(cityAdapter);

    }




    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (s.toString().equals("")){
            if (makeSingleCheckParentList != null){
                setupRecWithExpandable(makeSingleCheckParentList);
            }


        }else {
            if (cityList != null){
                setupRecForSearch();
                filter(s.toString());
            }


        }

    }


    private void filter(String text) {

        List<City> filterCity = new ArrayList<>();


        for (City s : cityList) {

            if (s.getCityName().contains(text)) {
                filterCity.add(s);
            }
        }


        cityAdapter.filterList(filterCity);
    }




}
