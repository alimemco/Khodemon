package com.ali.rnp.khodemon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.ali.rnp.khodemon.Adapter.LinearSingleAdapter;
import com.ali.rnp.khodemon.Adapter.SingleItemAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.ListLayout;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.android.volley.VolleyError;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";
    
    private RecyclerView recyclerView;
    private List<ListLayout> listLayouts;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        recyclerView = findViewById(R.id.activity_test_rec);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        ApiService apiService = new ApiService(this);
        final SingleItemAdapter singleItemAdapter = new SingleItemAdapter(this);
        final LinearSingleAdapter linearSingleAdapter = new LinearSingleAdapter(this);

        apiService.getHomeRecyclerListItems(new ApiService.OnHomeListItemReceived() {
            @Override
            public void onItemReceived(List<ListLayout> listLayouts, List<LocationPeople> locationPeopleList, VolleyError error) {
                if (listLayouts != null && locationPeopleList != null){
                    singleItemAdapter.setListDataForAdapter(locationPeopleList);
                    linearSingleAdapter.setListDataForAdapter(listLayouts);
                    recyclerView.setAdapter(linearSingleAdapter);
                }else {
                    Log.i(TAG, "onItemReceived: Error");
                }

            }
        });



    }




}