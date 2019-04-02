package com.ali.rnp.khodemon.Views.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.CityAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.City;
import com.ali.rnp.khodemon.DataModel.DataGenerator;
import com.ali.rnp.khodemon.MyLibrary.MyEditText;
import com.ali.rnp.khodemon.R;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CityChooseActivityNew extends AppCompatActivity {

    private RecyclerView recyclerViewCity;
    private Toolbar toolbar;
    private MyEditText searchEditText;
    private CityAdapter cityAdapter;
    List<City> citiesList;

    public static final String INTENT_CITY_ID = "city_id";
    public static final String INTENT_CITY_NAME = "city_name";
    public static final String INTENT_CITY_PROVINCE_NAME = "city_province_name";

    private static final String TAG = "CityChooseActivityNew";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_choose_new);

        setupToolbar();
        setupRecViewCity();
        setupEditText();
    }


    private void setupEditText() {
        searchEditText = findViewById(R.id.activity_city_choose_expandable_search_editText);





        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());
            }
        });


    }

    private void filter(String text) {

        List<City> filterCity = new ArrayList<>();


        for (City s : citiesList) {

            if (s.getCity().contains(text)) {
                filterCity.add(s);
            }
        }


        cityAdapter.filterList(filterCity);
    }

    private void sendCityData(String city) {

        Intent intent = new Intent(CityChooseActivityNew.this, MainActivity.class);
        intent.putExtra(INTENT_CITY_NAME, city);
        setResult(RESULT_OK, intent);
        finish();

    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.activity_city_choose_expandable_toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }

    private void setupRecViewCity() {

        recyclerViewCity = findViewById(R.id.activity_city_choose_expandable_recView);
        cityAdapter = new CityAdapter(CityChooseActivityNew.this);



       // cityAdapter.setupCityAdapter(DataGenerator.getListCity());
        ApiService apiService = new ApiService(CityChooseActivityNew.this);
        apiService.getProvince(new ApiService.OnProvinceReceived() {
            @Override
            public void onReceived(List<City> cities, VolleyError error) {
                if (cities != null && error == null){
                    cityAdapter.setupCityAdapter(cities);
                    recyclerViewCity.setLayoutManager(new LinearLayoutManager(CityChooseActivityNew.this, RecyclerView.VERTICAL, false));
                    recyclerViewCity.setAdapter(cityAdapter);

                    citiesList = new ArrayList<>();
                    citiesList.addAll(cities);
                    Toast.makeText(CityChooseActivityNew.this, "size: "+citiesList.size()+"  "+cities.size(), Toast.LENGTH_SHORT).show();
                }else  if (error != null){
                    Toast.makeText(CityChooseActivityNew.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });







    }
}
