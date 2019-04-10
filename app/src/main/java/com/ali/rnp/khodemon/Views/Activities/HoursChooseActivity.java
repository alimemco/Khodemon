package com.ali.rnp.khodemon.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.ali.rnp.khodemon.Adapter.HoursAdapter;
import com.ali.rnp.khodemon.DataModel.DataGenerator;
import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.R;

public class HoursChooseActivity extends AppCompatActivity {

    private MyButton chooseBtn;
    private RecyclerView recyclerView;
    private HoursAdapter hoursAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hours_choose);

        initViews();
        initRecyclerView();
    }

    private void initRecyclerView() {
        hoursAdapter = new HoursAdapter(this);
        hoursAdapter.setData(DataGenerator.dayList());
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(hoursAdapter);

    }

    private void initViews() {

        chooseBtn = findViewById(R.id.activity_choose_hours_buttonChoose);
        recyclerView = findViewById(R.id.activity_choose_hours_recyclerView);



        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
