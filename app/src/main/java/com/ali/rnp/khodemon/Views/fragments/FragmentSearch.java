package com.ali.rnp.khodemon.Views.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.Adapter.SearchAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.MyLibrary.MyEditText;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.UtilsApp.UtilsApp;
import com.ali.rnp.khodemon.Views.Activities.ChooseCategoryActivity;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;


public class FragmentSearch extends Fragment implements TextWatcher, View.OnClickListener {

    RecyclerView rcvSearch;
    ApiService apiService;
    SearchAdapter searchAdapter;
    MaterialProgressBar progressBar;
    MyButton chooseCategory;
    String category;

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
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));

        }
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
        chooseCategory.setOnClickListener(this);
        chooseCity.setOnClickListener(this);

    }

    private void initRcv() {
        apiService = new ApiService(getContext());
        searchAdapter = new SearchAdapter();

        LinearLayoutManager ln = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);

        rcvSearch.setLayoutManager(ln);
        rcvSearch.setAdapter(searchAdapter);
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

        if (!s.toString().equals("")){


            try {
                JSONObject jsonObject = new JSONObject();

                if (category != null && category.equals("")){
                    jsonObject.put(ProvidersApp.KEY_CATEGORY, category);
                }

                jsonObject.put(ProvidersApp.KEY_KEYWORD, s.toString());



                apiService.search(jsonObject, (statusCode, locationPeopleList, error) -> {

                    progressBar.setVisibility(View.GONE);

                    if (statusCode == ProvidersApp.STATUS_CODE_SUCCESSFULLY){

                        if (locationPeopleList != null){
                            searchAdapter.setData(locationPeopleList,s.toString());
                        }else {
                            searchAdapter.setIsEmpty();
                        }
                    }else {
                        String msg = UtilsApp.statusCodeToError(statusCode,error);
                        Log.i(TAG, "onSearch Error: "+msg);
                        searchAdapter.setIsEmpty();
                    }
                });
            } catch (JSONException e) {
               // e.printStackTrace();
            }

        }else {
            searchAdapter.setIsEmpty();
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

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

        if (resultCode == Activity.RESULT_OK && requestCode == ProvidersApp.REQUEST_CODE_CHOOSE_CATEGORY){

            if (data != null){
                category = data.getStringExtra(ProvidersApp.KEY_CATEGORY);
                chooseCategory.setText(category);

            }
        }
    }
}
