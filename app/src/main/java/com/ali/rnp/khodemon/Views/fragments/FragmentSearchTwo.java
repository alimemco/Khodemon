package com.ali.rnp.khodemon.Views.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.BottomSheet.SortBottomSheet;
import com.ali.rnp.khodemon.DataModel.ChipModel;
import com.ali.rnp.khodemon.DataModel.Filter;
import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.MyLibrary.MyEditText;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Search.ChildModel;
import com.ali.rnp.khodemon.Search.GroupModel;
import com.ali.rnp.khodemon.Search.SearchAdapter;
import com.ali.rnp.khodemon.SharedPrefManager;
import com.ali.rnp.khodemon.Views.Activities.FilterActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentSearchTwo extends Fragment implements
        View.OnClickListener,
        TextWatcher,
        ApiService.OnSearchCategory,
        SearchAdapter.OnChildClickListener {

    private static final String TAG = "FragmentSearchTwo";

    private ChipGroup chipGroup;
    private RecyclerView rcv;
    private ApiService apiService;
    private String typed = "";
    private SearchAdapter searchAdapter;
    private JSONObject jsonObject;

    private View view;


    public FragmentSearchTwo() {

    }

    public static FragmentSearchTwo newInstance() {

        Bundle args = new Bundle();

        FragmentSearchTwo fragment = new FragmentSearchTwo();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search_two, container, false);

        initViews();
        initRcv();
        initUserCity();

        return view;
    }

    private void initUserCity() {
        if (getContext() != null) {
            putIntoJson(null, new SharedPrefManager(getContext()).getSharedCity().getCity());
        }


    }

    private void initRcv() {
        rcv.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));
        searchAdapter = new SearchAdapter();
        searchAdapter.setOnChildClickListener(this);
        rcv.setAdapter(searchAdapter);
    }


    private void initViews() {

        MyEditText editText = view.findViewById(R.id.fragment_search_two_ediText);
        MyButton filterBtn = view.findViewById(R.id.fragment_search_two_filterBtn);
        MyButton sortBtn = view.findViewById(R.id.fragment_search_two_sortBtn);
        chipGroup = view.findViewById(R.id.fragment_search_two_chipGroup);
        rcv = view.findViewById(R.id.fragment_search_two_rcv);

        filterBtn.setOnClickListener(this);
        sortBtn.setOnClickListener(this);
        apiService = new ApiService(view.getContext());
        jsonObject = new JSONObject();
        editText.addTextChangedListener(this);

    }

    private void getResult() {

        if (jsonObject != null)
            apiService.searchCategory(jsonObject, this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_search_two_filterBtn:

                if (getActivity() != null)
                    getActivity().startActivityForResult(new Intent(getActivity(), FilterActivity.class), ProvidersApp.REQUEST_CODE_CHOOSE_CATEGORY);


                break;

            case R.id.fragment_search_two_sortBtn:

                SortBottomSheet sortBottomSheet = SortBottomSheet.newInstance();
                sortBottomSheet.setOnClickBottomSheet(title -> {
                    Toast.makeText(getContext(), title, Toast.LENGTH_SHORT).show();
                    sortBottomSheet.dismiss();
                });

                sortBottomSheet.show(getChildFragmentManager(), "SortBottomSheet");
                //TODO Add dialog

                Log.i(TAG, "onClick: " + jsonObject.toString());

                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {


        typed = s.toString();

        searchAdapter.setTyped(typed);

        if (!typed.equals("")) {

            putIntoJson(s.toString(), null);

            getResult();
            searchAdapter.isSearching();

        } else {
            searchAdapter.isEmpty();
        }

    }


    private void putIntoJson(String keyword, String cityUser) {
        try {

            if (keyword != null) {
                jsonObject.put(ProvidersApp.KEY_KEYWORD, keyword);
            }

            if (cityUser != null) {
                jsonObject.put(ProvidersApp.KEY_CITY_USER, cityUser);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void putFilterIntoJson(ArrayList<Filter> filterList) {

        try {
            JSONArray jsonAryFilter = new JSONArray();
            JSONObject jsonObjPer;
            JSONArray jsonAryValue;

            for (int i = 0; i < filterList.size(); i++) {

                Filter filter = filterList.get(i);

                String tag = filter.getTag();

                jsonAryValue = new JSONArray();
                jsonObjPer = new JSONObject();

                if (filter.getFiltered() != null) {

                    for (int j = 0; j < filter.getFiltered().size(); j++) {

                        jsonAryValue.put(j, filter.getFiltered().get(j).getTitle());

                    }

                    jsonObjPer.put(ProvidersApp.KEY_KEY, tag);
                    jsonObjPer.put(tag, jsonAryValue);

                }

                if (jsonObjPer.length() > 0) {
                    jsonAryFilter.put(jsonObjPer);
                }
            }

            jsonObject.put(ProvidersApp.KEY_FILTER, jsonAryFilter);

        } catch (JSONException e) {
            Log.i(TAG, "putFilterIntoJson: " + e.toString());
        }

    }

    @Override
    public void onChildClick(ChildModel childModel) {
        Toast.makeText(view.getContext(), childModel.getName() + " " + childModel.getId(), Toast.LENGTH_SHORT).show();
/*
        Intent intent = new Intent(context, DetailActivity.class);
        //   intent.putExtra(ProvidersApp.KEY_POST_ID,postId);
        intent.putExtra(ProvidersApp.KEY_LOCATION_PEOPLE,locationPeopleList.get(position));
        context.startActivity(intent);


        */

//TODO chasnge child to Liocatopn people

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == ProvidersApp.REQUEST_CODE_CHOOSE_CATEGORY) {

            if (data != null) {

                ArrayList<Filter> filterList = data.getParcelableArrayListExtra(ProvidersApp.KEY_FILTER_LIST);

                if (filterList != null) {

                    chipGroup.removeAllViews();

                    createFilterChip(filterList);
                    putFilterIntoJson(filterList);

                    getResult();

                }

            }
        }
    }

    private void createFilterChip(ArrayList<Filter> filterList) {

        for (int i = 0; i < filterList.size(); i++) {
            Filter filter = filterList.get(i);

            if (filter.getFiltered() != null) {
                ArrayList<ChipModel> filtered = filter.getFiltered();

                if (filtered != null) {
                    for (int j = 0; j < filtered.size(); j++) {

                        createChip(filtered.get(j));

                    }
                }
            }
        }
    }

    private void createChip(ChipModel chipModel) {

        Chip chip = new Chip(view.getContext());
        chip.setText(chipModel.getTitle());

        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(v -> {

            chipGroup.removeView(chip);

            removeFromJson(chipModel.getTitle());

            getResult();

        });

        chipGroup.addView(chip);
    }


    @Override
    public void OnSuccessSearch(ArrayList<GroupModel> groupModels) {

        if (groupModels != null && !typed.equals("")) {

            searchAdapter.setData(groupModels);

        } else {
            searchAdapter.isEmpty();
        }

    }

    @Override
    public void OnErrorSearch(Object error) {
        searchAdapter.isEmpty();
    }


    private void removeFromJson(String title) {
        try {

            JSONArray newJsAry;
            JSONArray jsonAryFilter = jsonObject.getJSONArray("filter");

            for (int i = 0; i < jsonAryFilter.length(); i++) {
                newJsAry = new JSONArray();
                JSONObject jsObjPer = jsonAryFilter.getJSONObject(i);

                String key = jsObjPer.getString("key");
                JSONArray jsAryValue = jsObjPer.getJSONArray(key);


                for (int j = 0; j < jsAryValue.length(); j++) {
                    String value = jsAryValue.getString(j);
                    if (!title.equals(value)) {
                        newJsAry.put(value);
                    }
                }

                jsObjPer.put(key, newJsAry);

            }


        } catch (JSONException e) {
            Log.i(TAG, "error removeFromJson: " + e);
        }


    }

}
