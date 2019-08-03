package com.ali.rnp.khodemon.Views.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;

import com.ali.rnp.khodemon.Adapter.SearchAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.MyLibrary.MyEditText;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.UtilsApp.UtilsApp;
import com.ali.rnp.khodemon.Views.Activities.ChooseCategoryActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;


public class FragmentSearch extends Fragment implements TextWatcher,
        View.OnClickListener,
        ApiService.OnReceivedSearch {

    private RecyclerView rcvSearch;
    private ApiService apiService;
    private SearchAdapter searchAdapter;
    private MaterialProgressBar progressBar;
    private MyButton chooseCategory;
    private String category;
    private String typed;
    private JSONObject jsonObject;

    private Context context;

    private static final String TAG = "FragmentSearch";

    public FragmentSearch() {

    }

    public static FragmentSearch newInstance() {

        Bundle args = new Bundle();

        FragmentSearch fragment = new FragmentSearch();
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(context).inflateTransition(android.R.transition.move));

        }

        category = "";
        typed = "";
        jsonObject = new JSONObject();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initViews(view);
        initRcv();

        return view;
    }

    private void initViews(View view) {

        MyEditText edtSearch = view.findViewById(R.id.fragment_search_editText);
        chooseCategory = view.findViewById(R.id.fragment_search_chooseCategory_button);
        MyButton chooseCity = view.findViewById(R.id.fragment_search_chooseCity_button);

        rcvSearch = view.findViewById(R.id.fragment_search_rcvRes);
        progressBar = view.findViewById(R.id.fragment_search_progressBar);
        progressBar.setVisibility(View.GONE);

        edtSearch.addTextChangedListener(this);
        chooseCity.setOnClickListener(this);

        chooseCategory.setOnClickListener(this);
        //  chooseCategory.setDrawableClickListener(this);

    }

    private void initRcv() {
        apiService = new ApiService(context);
        searchAdapter = new SearchAdapter();

        LinearLayoutManager ln = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);

        rcvSearch.setLayoutManager(ln);
        rcvSearch.setAdapter(searchAdapter);

        apiService.getSearchSuggestion(this);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {


        progressBar.setVisibility(View.VISIBLE);
        typed = s.toString();

        if (!typed.equals("")) {

            sendJson(true);

        } else if (!category.equals("")) {

            sendJson(true);

        } else {
            searchAdapter.setIsEmpty();
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.fragment_search_chooseCategory_button:

                    if (getActivity() != null)
                        getActivity().startActivityForResult(new Intent(getActivity(), ChooseCategoryActivity.class), ProvidersApp.REQUEST_CODE_CHOOSE_CATEGORY);


                break;

            case R.id.fragment_search_chooseCity_button:

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == ProvidersApp.REQUEST_CODE_CHOOSE_CATEGORY) {

            if (data != null) {
                category = data.getStringExtra(ProvidersApp.KEY_CATEGORY);

                checkCategoryExist();


            }
        }
    }

    @Override
    public void onSearch(int statusCode, ArrayList<LocationPeople> locationPeopleList, String error) {
        progressBar.setVisibility(View.GONE);

        if (statusCode == ProvidersApp.STATUS_CODE_SUCCESSFULLY) {

            if (locationPeopleList != null) {
                searchAdapter.setData(locationPeopleList, typed);
            } else {
                searchAdapter.setIsEmpty();
            }
        } else {
            String msg = UtilsApp.statusCodeToError(statusCode, error);
            Log.i(TAG, "onSearch Error: " + msg);
            searchAdapter.setIsEmpty();
        }
    }

    private void sendJson(boolean setNew) {

        try {
            if (setNew) {
                jsonObject = new JSONObject();
            }

            if (!category.equals("")) {

                jsonObject.put(ProvidersApp.KEY_CATEGORY, category);
            }

            if (!typed.equals("")) {
                jsonObject.put(ProvidersApp.KEY_KEYWORD, typed);
            }

            apiService.search(jsonObject, this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void checkCategoryExist() {

        if (category.equals("")) {
            chooseCategory.setText(R.string.chooseCategory);
            chooseCategory.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_add, 0);

            sendJson(true);
        } else {

            progressBar.setVisibility(View.VISIBLE);

            chooseCategory.setText(category);
            chooseCategory.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.ic_false_circle, 0);

            sendJson(false);
        }


    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
/*
    @Override
    public void onClick(DrawablePosition target) {
        category = "";
        checkCategoryExist();
    }*/

}
