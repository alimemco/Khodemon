package com.ali.rnp.khodemon.Views.Activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.rnp.khodemon.Adapter.FilterNonExpandAdapter;
import com.ali.rnp.khodemon.Adapter.FilterOptionAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.CheckModel;
import com.ali.rnp.khodemon.DataModel.Filter;
import com.ali.rnp.khodemon.ExpandableSingleItems.ChildExp;
import com.ali.rnp.khodemon.MultiCheckExpand.MultiCheckGenreAdapter;
import com.ali.rnp.khodemon.MultiCheckExpand.MultiCheckGroup;
import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.MyLibrary.MyEditText;
import com.ali.rnp.khodemon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity implements
        FilterOptionAdapter.OnItemClicked,
        ApiService.OnGetFilterOptions,
        View.OnClickListener,
        TextWatcher {

    private Toolbar toolbar;
    private RecyclerView rcvOptions;
    private RecyclerView rcvValues;
    private ArrayList<CheckModel> models;

    private FilterNonExpandAdapter filterNonExpandAdapter;
    private MultiCheckGenreAdapter multiCheckGenreAdapter;

    private StateAdapter state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        initViews();
        initToolbar();
        initSearch();

        ApiService apiService = new ApiService(this);
        apiService.getFilterOptions(FilterActivity.this);
    }

    private void initSearch() {

        MyEditText searchEdTxt = findViewById(R.id.activity_filter_search);

        searchEdTxt.addTextChangedListener(this);
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
        MyButton clearBtn = findViewById(R.id.activity_filter_clear_btn);

        clearBtn.setOnClickListener(this);

        rcvOptions.setLayoutManager(new LinearLayoutManager(this));
        rcvValues.setLayoutManager(new LinearLayoutManager(this));

    }

    private void parseJson(JSONObject jsonObject) {

        try {

            boolean hasChild = jsonObject.getBoolean("hasChild");

            if (hasChild) {

                Expandable(jsonObject);
                state = StateAdapter.EXPANDABLE;

            } else {

                NonExpandable(jsonObject);
                state = StateAdapter.NON_EXPANDABLE;

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void NonExpandable(JSONObject jsonObject) throws JSONException {
        ArrayList<CheckModel> checkList = new ArrayList<>();
        models = new ArrayList<>();

        JSONArray jsAryItems = jsonObject.getJSONArray("items");

        for (int i = 0; i < jsAryItems.length(); i++) {
            JSONObject jsObjItem = jsAryItems.getJSONObject(i);

            checkList.add(new CheckModel(jsObjItem.getString("title")));
        }


        filterNonExpandAdapter = new FilterNonExpandAdapter(checkList);
        models.addAll(checkList);

        rcvValues.setAdapter(filterNonExpandAdapter);


    }

    private void Expandable(JSONObject jsonObject) throws JSONException {

        List<ChildExp> childList;
        List<MultiCheckGroup> multiCheckGroups = new ArrayList<>();

        models = new ArrayList<>();

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

                models.add(new CheckModel(titleChild));

            }


            MultiCheckGroup makeSingleCheckChild = new MultiCheckGroup(titleGroup, childList, R.drawable.ic_location_name);
            multiCheckGroups.add(makeSingleCheckChild);

        }
        multiCheckGenreAdapter = new MultiCheckGenreAdapter(multiCheckGroups);
        rcvValues.setAdapter(multiCheckGenreAdapter);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String typed = s.toString();

        FilterNonExpandAdapter filterNonExpandAdapter;

        if (!typed.equals("")) {

            filterNonExpandAdapter = new FilterNonExpandAdapter(filterSearch(typed));

        } else {

            filterNonExpandAdapter = new FilterNonExpandAdapter(models);

        }

        rcvValues.setAdapter(filterNonExpandAdapter);

    }

    private ArrayList<CheckModel> filterSearch(String text) {

        ArrayList<CheckModel> filtered = new ArrayList<>();

        for (CheckModel s : models) {

            if (s.getTitle().contains(text)) {
                filtered.add(s);
            }
        }

        return filtered;
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_filter_clear_btn) {

            if (state == StateAdapter.EXPANDABLE) {

                multiCheckGenreAdapter.clearChoices();

            } else {
                filterNonExpandAdapter.clearChoices();
            }

        }
    }

    private enum StateAdapter {
        EXPANDABLE,
        NON_EXPANDABLE
    }


}
