package com.ali.rnp.khodemon.Views.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.ali.rnp.khodemon.Dialogs.SortDialog;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentSearchTwo extends Fragment implements
        View.OnClickListener,
        TextWatcher,
        ApiService.OnSearchCategory,
        SearchAdapter.OnChildClickListener {

    private static final String TAG = "FragmentSearchTwo";

    private static final int keywordKey = 0;
    private static final int categoryKey = 1;
    private static final int cityUserKey = 2;
    private static final int cityFilterKey = 3;

    private ChipGroup chipGroup;
    private RecyclerView rcv;
    private ApiService apiService;
    private String typed;
    private SearchAdapter searchAdapter;
    private JSONObject jsonObject;

    private Chip categoryChip;

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
            putIntoJson(null, null, new SharedPrefManager(getContext()).getSharedCity().getCity(), null);
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

    private void addCategoryChip(String name) {

        categoryChip = new Chip(view.getContext());
        categoryChip.setText(name);

        categoryChip.setCloseIconVisible(true);
        categoryChip.setOnCloseIconClickListener(v -> {
            chipGroup.removeView(categoryChip);

            // removeJsonCategory();
            removeJson(categoryKey);

            getResult();

        });

        chipGroup.addView(categoryChip);


    }

    private void getResult() {

        apiService.searchCategory(jsonObject, this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_search_two_filterBtn:

                if (getActivity() != null)
                    // getActivity().startActivityForResult(new Intent(getActivity(), ChooseCategoryActivity.class), ProvidersApp.REQUEST_CODE_CHOOSE_CATEGORY);
                    getActivity().startActivityForResult(new Intent(getActivity(), FilterActivity.class), ProvidersApp.REQUEST_CODE_CHOOSE_CATEGORY);


                break;

            case R.id.fragment_search_two_sortBtn:

                SortDialog dialog = new SortDialog();

                dialog.show(getChildFragmentManager(), "SortDialog");
              /*  dialog.getAdapter().setOnItemSortClick(new SortAdapter.OnItemSortClick() {
                    @Override
                    public void OnSortClick(String name) {
                        Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });*/
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

            putIntoJson(s.toString(), null, null, null);

            getResult();
            searchAdapter.isSearching();

        } else {
            searchAdapter.isEmpty();
        }

    }
/*
    private void putJsonKeyword(String typed) {

        try {
            jsonObject.put(ProvidersApp.KEY_KEYWORD, typed);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void putJsonCategory(String category) {

        try {
            jsonObject.put(ProvidersApp.KEY_CATEGORY, category);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

    private void putIntoJson(String keyword, String category, String cityUser, String cityFilter) {
        try {

            if (keyword != null) {
                jsonObject.put(ProvidersApp.KEY_KEYWORD, keyword);
            }

            if (category != null) {
                jsonObject.put(ProvidersApp.KEY_CATEGORY, category);
            }

            if (cityUser != null) {
                jsonObject.put(ProvidersApp.KEY_CITY_USER, cityUser);
            }

            if (cityFilter != null) {
                jsonObject.put(ProvidersApp.KEY_CITY_FILTER, cityFilter);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void removeJson(int toRemove) {
        try {
            switch (toRemove) {
                case keywordKey:
                    jsonObject.put(ProvidersApp.KEY_KEYWORD, null);
                    break;

                case categoryKey:
                    jsonObject.put(ProvidersApp.KEY_CATEGORY, null);
                    break;

                case cityUserKey:
                    jsonObject.put(ProvidersApp.KEY_CITY_USER, null);
                    break;

                case cityFilterKey:
                    jsonObject.put(ProvidersApp.KEY_CITY_FILTER, null);
                    break;

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onChildClick(ChildModel childModel) {
        Toast.makeText(view.getContext(), childModel.getName(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK && requestCode == ProvidersApp.REQUEST_CODE_CHOOSE_CATEGORY) {

            if (data != null) {
                String category = data.getStringExtra(ProvidersApp.KEY_CATEGORY);

                putIntoJson(null, category, null, null);
                categoryCheck(category);
                getResult();
            }
        }
    }

    private void categoryCheck(String category) {

        if (!category.equals("")) {

            chipGroup.removeView(categoryChip);

            addCategoryChip(category);
        }

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
}
