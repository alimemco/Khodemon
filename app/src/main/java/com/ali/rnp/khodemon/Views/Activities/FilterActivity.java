package com.ali.rnp.khodemon.Views.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.rnp.khodemon.Adapter.FilterOptionAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.Filter;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MyTextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        initViews();
        initToolbar();

        ApiService apiService = new ApiService(this);
        apiService.getFilterOptions(new ApiService.OnGetFilterOptions() {
            @Override
            public void OnSuccessFilter(ArrayList<Filter> filterList) {
                if (filterList != null) {
                    FilterOptionAdapter adapter = new FilterOptionAdapter(filterList);

                    adapter.setOnItemClicked(new FilterOptionAdapter.OnItemClicked() {
                        @Override
                        public void onItemClicked(Filter filter) {
                            txt.setText(filter.getJsonObject().toString());
                        }
                    });

                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void OnErrorFiler(Object error) {
                Toast.makeText(FilterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);


        }
    }

    private void initViews() {
        toolbar = findViewById(R.id.activity_filter_toolbar);
        txt = findViewById(R.id.activity_filter_txt);
        recyclerView = findViewById(R.id.activity_filter_rcv_options);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
