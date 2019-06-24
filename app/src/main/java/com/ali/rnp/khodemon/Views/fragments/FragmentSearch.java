package com.ali.rnp.khodemon.Views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ali.rnp.khodemon.Adapter.SearchAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.MyLibrary.MyEditText;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.UtilsApp.UtilsApp;

import java.util.ArrayList;


public class FragmentSearch extends Fragment implements TextWatcher {

    MyEditText edtSearch;
    RecyclerView rcvSearch;
    ApiService apiService;
    SearchAdapter searchAdapter;

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

        edtSearch = view.findViewById(R.id.fragment_search_editText);
        rcvSearch = view.findViewById(R.id.fragment_search_rcvRes);
        
        edtSearch.addTextChangedListener(this);

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
        if (!s.toString().equals("")){
            Log.i(TAG, "onTextChanged: "+s);
            apiService.search(s.toString(), (statusCode, locationPeopleList, error) -> {

                if (statusCode == ProvidersApp.STATUS_CODE_SUCCESSFULLY){

                    if (locationPeopleList != null){
                        searchAdapter.setData(locationPeopleList,s.toString());
                    }
                }else {
                    String msg = UtilsApp.statusCodeToError(statusCode,error);
                    Log.i(TAG, "onSearch Error: "+msg);
                    searchAdapter.setIsEmpty();
                    Log.i(TAG, "onTextChanged: SetEmpty");
                }
            });



        }else {
            searchAdapter.setIsEmpty();
            Log.i(TAG, "onTextChanged: last Else SetEmpty");
        }
    }
}
