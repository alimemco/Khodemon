package com.ali.rnp.khodemon.Views.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ali.rnp.khodemon.Adapter.CityAdapter;
import com.ali.rnp.khodemon.DataModel.DataGenerator;
import com.ali.rnp.khodemon.R;

public class CityChoose extends AppCompatActivity {

    private RecyclerView recyclerViewCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_choose);

        setupRecViewCity ();
    }

    private void setupRecViewCity() {

        recyclerViewCity = findViewById(R.id.activity_city_choose_recView);

        CityAdapter cityAdapter = new CityAdapter(CityChoose.this);
        cityAdapter.setupCityAdapter(DataGenerator.getListCity());
        recyclerViewCity.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        recyclerViewCity.setAdapter(cityAdapter);

    }
}
