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

import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.MyLibrary.MyEditText;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Search.ChildModel;
import com.ali.rnp.khodemon.Search.GroupModel;
import com.ali.rnp.khodemon.Search.SearchAdapter;
import com.ali.rnp.khodemon.Views.Activities.ChooseCategoryActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class FragmentSearchTwo extends Fragment implements
        View.OnClickListener,
        TextWatcher,
        ApiService.OnSearchCategory,
        SearchAdapter.OnChildClickListener {

    private static final String TAG = "FragmentSearchTwo";

    private ChipGroup chipGroup;
    private RecyclerView rcv;
    private ApiService apiService;
    private SearchAdapter searchAdapter;
    private JSONObject jsonObject;
    private MaterialProgressBar progressBar;

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

        return view;
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
        progressBar = view.findViewById(R.id.fragment_search_two_progressBar);

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
            putJsonCategory(null);

            getResult();

        });

        chipGroup.addView(categoryChip);



    }

    private void getResult() {

        apiService.searchCategory(jsonObject, this);

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_search_two_filterBtn:

                if (getActivity() != null)
                    getActivity().startActivityForResult(new Intent(getActivity(), ChooseCategoryActivity.class), ProvidersApp.REQUEST_CODE_CHOOSE_CATEGORY);


                break;

            case R.id.fragment_search_two_sortBtn:

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


        String typed = s.toString();

        searchAdapter.setTyped(typed);

        if (!typed.equals("")) {

            putJsonKeyword(s.toString());

            getResult();

        } else {
            Toast.makeText(view.getContext(), "isEmptyInput", Toast.LENGTH_SHORT).show();
        }

    }

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
    }

    @Override
    public void OnSuccessSearch(ArrayList<GroupModel> groupModels) {
        progressBar.setVisibility(View.INVISIBLE);

        if (groupModels != null) {
            //searchAdapter.setData(groupModels,false);
            searchAdapter.setData(groupModels);
        }
    }

    @Override
    public void OnErrorSearch(Object error) {
        progressBar.setVisibility(View.INVISIBLE);

        // Toast.makeText(view.getContext(), error.toString(), Toast.LENGTH_SHORT).show();

        //searchAdapter.setData(emptySearch("Sea"),true);
        // searchAdapter.setData(emptySearch("Sea"));
        searchAdapter.isEmpty();


    }

    private ArrayList<GroupModel> emptySearch(String title) {
        ArrayList<GroupModel> groupModels = new ArrayList<>();
        ArrayList<ChildModel> childModels = new ArrayList<>();

        //ChildModel childModel =
        childModels.add(new ChildModel.Builder().setName("موردی یافت نشد").create());

        // GroupModel groupModel = ;
        groupModels.add(new GroupModel(title, childModels));

        return groupModels;
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

                putJsonCategory(category);
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
}
