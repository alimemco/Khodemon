package com.ali.rnp.khodemon.Views.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.HoursAdapter;
import com.ali.rnp.khodemon.DataModel.DataGenerator;
import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.R;

public class HoursChooseActivity extends AppCompatActivity {

    private MyButton chooseBtn;
    private RecyclerView recyclerView;
    private HoursAdapter hoursAdapter;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hours_choose);

        initViews();
        initRecyclerView();
        initToolbar();
    }

    private void initToolbar() {

        setSupportActionBar(toolbar);
        ActionBar mActionBar = getSupportActionBar();

        if (mActionBar != null){
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(true);
        }



    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void initRecyclerView() {
        hoursAdapter = new HoursAdapter(this);
        //hoursAdapter.setData(DataGenerator.dayList());
        hoursAdapter.setData(DataGenerator.hourDaysList());
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(hoursAdapter);

    }

    private void initViews() {

        chooseBtn = findViewById(R.id.activity_choose_hours_buttonChoose);
        recyclerView = findViewById(R.id.activity_choose_hours_recyclerView);
        toolbar = findViewById(R.id.activity_choose_hours_toolbar);



        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(HoursChooseActivity.this, ""+hoursAdapter.getData().get(0).getHourFromOne(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
