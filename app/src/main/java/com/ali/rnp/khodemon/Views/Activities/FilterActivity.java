package com.ali.rnp.khodemon.Views.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.rnp.khodemon.Adapter.FilterNormalAdapter;
import com.ali.rnp.khodemon.Adapter.FilterOptionAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.CheckModel;
import com.ali.rnp.khodemon.DataModel.Filter;
import com.ali.rnp.khodemon.ExpandableSingleItems.ChildExp;
import com.ali.rnp.khodemon.MultiCheckExpand.MultiCheckGenreAdapter;
import com.ali.rnp.khodemon.MultiCheckExpand.MultiCheckGroup;
import com.ali.rnp.khodemon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity implements
        FilterOptionAdapter.OnItemClicked,
        ApiService.OnGetFilterOptions {

    private Toolbar toolbar;
    private RecyclerView rcvOptions;
    private RecyclerView rcvValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        initViews();
        initToolbar();

        ApiService apiService = new ApiService(this);
        apiService.getFilterOptions(FilterActivity.this);
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
        rcvOptions = findViewById(R.id.activity_filter_rcv_options);
        rcvValues = findViewById(R.id.activity_filter_rcv_values);

        rcvOptions.setLayoutManager(new LinearLayoutManager(this));
        rcvValues.setLayoutManager(new LinearLayoutManager(this));
    }

    private void parseJson(JSONObject jsonObject) {



        try {

            boolean hasChild = jsonObject.getBoolean("hasChild");

            if (hasChild) {

                Expandable(jsonObject);

            } else {

                NonExpandable(jsonObject);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void NonExpandable(JSONObject jsonObject) throws JSONException {
        ArrayList<CheckModel> checkList = new ArrayList<>();

        JSONArray jsAryItems = jsonObject.getJSONArray("items");

        for (int i = 0; i < jsAryItems.length(); i++) {
            JSONObject jsObjItem = jsAryItems.getJSONObject(i);

            checkList.add(new CheckModel(jsObjItem.getString("title")));
        }

        FilterNormalAdapter filterNormalAdapter = new FilterNormalAdapter(checkList);


        rcvValues.setAdapter(filterNormalAdapter);
    }

    private void Expandable(JSONObject jsonObject) throws JSONException {
        List<ChildExp> childList;
        List<MultiCheckGroup> multiCheckGroups = new ArrayList<>();

        JSONArray jsAryItems = jsonObject.getJSONArray("items");

        for (int i = 0; i < jsAryItems.length(); i++) {
            JSONObject jsObjGroup = jsAryItems.getJSONObject(i);
            String titleGroup = jsObjGroup.getString("title");
            JSONArray jsAryChild = jsObjGroup.getJSONArray("child");

            childList = new ArrayList<>();

            for (int j = 0; j < jsAryChild.length(); j++) {
                JSONObject jsObjChild = jsAryChild.getJSONObject(j);
                String titleChild = jsObjChild.getString("title");

                ChildExp child = new ChildExp();
                child.setData(titleChild, false);
                childList.add(child);

            }
            MultiCheckGroup makeSingleCheckChild = new MultiCheckGroup(titleGroup, childList, R.drawable.ic_location_name);
            multiCheckGroups.add(makeSingleCheckChild);

        }
        MultiCheckGenreAdapter multiCheckGenreAdapter = new MultiCheckGenreAdapter(multiCheckGroups);
        rcvValues.setAdapter(multiCheckGenreAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


    @Override
    public void OnSuccessFilter(ArrayList<Filter> filterList) {
        if (filterList != null) {

            FilterOptionAdapter adapter = new FilterOptionAdapter(filterList);

            adapter.setOnItemClicked(FilterActivity.this);

            rcvOptions.setAdapter(adapter);

        }
    }

    @Override
    public void OnErrorFiler(Object error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(Filter filter) {
        parseJson(filter.getJsonObject());
    }
}
