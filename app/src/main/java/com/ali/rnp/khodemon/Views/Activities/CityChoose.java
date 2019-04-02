package com.ali.rnp.khodemon.Views.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.CityAdapter;
import com.ali.rnp.khodemon.DataModel.City;
import com.ali.rnp.khodemon.DataModel.DataGenerator;
import com.ali.rnp.khodemon.ExpandableTags.Expert;
import com.ali.rnp.khodemon.ExpandableTags.SingleCheckGroupingAdapter;
import com.ali.rnp.khodemon.MyApplication;
import com.ali.rnp.khodemon.R;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnCheckChildClickListener;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.ali.rnp.khodemon.ExpandableTags.TagsDataFactory.makeSingleCheckTags;

public class CityChoose extends AppCompatActivity {

    private RecyclerView recyclerViewCity;
    private Toolbar toolbar;
    private AutoCompleteTextView cityAutoTextEditText;
    private SingleCheckGroupingAdapter adapter;

    public static final String INTENT_CITY_ID = "city_id";
    public static final String INTENT_CITY_NAME = "city_name";
    public static final String INTENT_CITY_PROVINCE_NAME = "city_province_name";

    private static final String TAG = "CityChoose";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_choose);

        setupToolbar();
        setupRecViewCityWithExp();
        setupAutoCompleteText();


    }

    private void setupAutoCompleteText() {
        cityAutoTextEditText = findViewById(R.id.activity_city_choose_cityAutoCompleteTextView);
        cityAutoTextEditText.setTypeface(MyApplication.getIranSans(this));


        List<String> cityList = new ArrayList<>();

        List<City> cities = DataGenerator.getListCity();


        for (int i = 0; i < cities.size(); i++) {
            cityList.add(cities.get(i).getCity());
        }


        cityAutoTextEditText.setThreshold(1);

        ArrayAdapter<String> citiesAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, cityList);
        cityAutoTextEditText.setAdapter(citiesAdapter);
        cityAutoTextEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String city = parent.getItemAtPosition(position).toString();
                sendCityData(city);
            }
        });

    }

    private void sendCityData(String city) {

        Intent intent = new Intent(CityChoose.this, MainActivity.class);
        intent.putExtra(INTENT_CITY_NAME, city);
        setResult(RESULT_OK, intent);
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

    private void setupRecViewCity() {

        recyclerViewCity = findViewById(R.id.activity_city_choose_recView);

        CityAdapter cityAdapter = new CityAdapter(CityChoose.this);
        cityAdapter.setupCityAdapter(DataGenerator.getListCity());
        recyclerViewCity.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        recyclerViewCity.setAdapter(cityAdapter);

    }

    private void setupRecViewCityWithExp() {

        recyclerViewCity = findViewById(R.id.activity_city_choose_recView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        adapter = new SingleCheckGroupingAdapter(makeSingleCheckTags(),this);
        recyclerViewCity.setLayoutManager(layoutManager);
        recyclerViewCity.setAdapter(adapter);

        adapter.setChildClickListener(new OnCheckChildClickListener() {
            @Override
            public void onCheckChildCLick(View v, boolean checked, CheckedExpandableGroup group, int childIndex) {
                adapter.clearChoices();
                group.checkChild(childIndex);
                Expert expert = (Expert) group.getItems().get(childIndex);

                Toast.makeText(CityChoose.this, expert.getName(), Toast.LENGTH_SHORT).show();


            }
        });

    }


}
