package com.ali.rnp.khodemon.Views.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnCheckChildClickListener;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity implements
        FilterOptionAdapter.OnItemClicked,
        ApiService.OnGetFilterOptions,
        View.OnClickListener,
        OnCheckChildClickListener,
        FilterNonExpandAdapter.OnCheckChildNonExpand,
        TextWatcher {

    private static final String TAG = "FilterActivityApp";
    private Toolbar toolbar;
    private RecyclerView rcvOptions;
    private RecyclerView rcvValues;
    private ArrayList<CheckModel> models;
    private FilterNonExpandAdapter filterNonExpandAdapter;
    private MultiCheckGenreAdapter multiCheckGenreAdapter;
    private StateAdapter state;
    private int optionPosition;
    private int lastOptionPosition = -1;
    private ArrayList<String> filtered;
    private ArrayList<Filter> filterList;
    private FilterOptionAdapter filterOptionAdapter;

    // private List<MultiCheckGroup> multiCheckGroups;

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
        MyButton filterBtn = findViewById(R.id.activity_filter_filter_btn);

        clearBtn.setOnClickListener(this);
        filterBtn.setOnClickListener(this);

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
        filterNonExpandAdapter.setOnCheckChildNonExpand(this);
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
        multiCheckGenreAdapter.setChildClickListener(this);
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

        filterNonExpandAdapter.setOnCheckChildNonExpand(this);
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
            this.filterList = new ArrayList<>();
            this.filterList.addAll(filterList);

            filterOptionAdapter = new FilterOptionAdapter(filterList);

            filterOptionAdapter.setOnItemClicked(FilterActivity.this);

            rcvOptions.setAdapter(filterOptionAdapter);

        }
    }

    @Override
    public void OnErrorFiler(Object error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.activity_filter_clear_btn:
                filterList.get(optionPosition).setFiltered(new ArrayList<>());
                filterOptionAdapter.changedItemValue(filterList);

                if (state == StateAdapter.EXPANDABLE) {

                    multiCheckGenreAdapter.clearChoices();

                } else {

                    filterNonExpandAdapter.clearChoices();
                }
                break;

            case R.id.activity_filter_filter_btn:
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra(ProvidersApp.KEY_FILTER_LIST, filterList);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }


    @Override
    public void onItemClicked(int position) {


        optionPosition = position;
/*
        if (lastOptionPosition != -1) {
            List grp = multiCheckGenreAdapter.getGroups();
            filterList.get(lastOptionPosition).setStateList(grp);

            if (filterList.get(position).getStateList() != null) {
                Log.i(TAG, "onItemClicked: " + filterList.get(position).getStateList().size());
              //  multiCheckGenreAdapter = new MultiCheckGenreAdapter(filterList.get(position).getStateList());


            }


        }*/


        if (filterList.get(position).getFiltered() == null) {
            filtered = new ArrayList<>();
        } else {
            filtered = filterList.get(position).getFiltered();
        }


        parseJson(filterList.get(position).getJsonObject());


    }


    @Override
    public void onCheckChildCLick(View v, boolean checked, CheckedExpandableGroup group, int childIndex) {

        ChildExp child = (ChildExp) group.getItems().get(childIndex);
        changeValueFiltered(checked, child.getName());

    }

    @Override
    public void OnCheckNonExpand(boolean checked, String title) {
        changeValueFiltered(checked, title);
    }


    private void changeValueFiltered(boolean checked, String title) {
        if (checked) {

            filtered.add(title);
            filterList.get(optionPosition).setFiltered(filtered);

            filterOptionAdapter.changedItemValue(filterList);
        } else {

            filtered.remove(title);
            filterList.get(optionPosition).setFiltered(filtered);

            filterOptionAdapter.changedItemValue(filterList);
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);


    }




    private enum StateAdapter {
        EXPANDABLE,
        NON_EXPANDABLE
    }

}
