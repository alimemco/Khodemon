package com.ali.rnp.khodemon.Views.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.Category;
import com.ali.rnp.khodemon.ExpandableSingleItems.AdapterSingleExp;
import com.ali.rnp.khodemon.ExpandableSingleItems.ChildExp;
import com.ali.rnp.khodemon.ExpandableSingleItems.SingleCheckItemsExp;
import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.android.volley.VolleyError;

import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ChooseCategoryActivity extends AppCompatActivity {

    //private List<Category> categoryList;
    //private List<SingleCheckItemsExp> makeSingleCheckParentList;
    private AdapterSingleExp adapterSingleExp;
    private RecyclerView recyclerView;
    private String category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        MyButton chooseButton = findViewById(R.id.activity_choose_category_button);

        initToolbar();
        setupRecyclerView();



        chooseButton.setOnClickListener(v -> {

            if (category != null && !category.equals("")){
                Intent intent = new Intent();
                intent.putExtra(ProvidersApp.KEY_CATEGORY, category);
                setResult(Activity.RESULT_OK,intent);
            }

          finish();
        });


    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.activity_choose_category_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.activity_choose_category_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ApiService apiService = new ApiService(ChooseCategoryActivity.this);

        apiService.getCategory(new ApiService.OnCategoryReceived() {
            @Override
            public void onReceived(List<SingleCheckItemsExp> makeSingleCheckParent, List<Category> tags, VolleyError error) {

                if (makeSingleCheckParent != null && tags != null && error == null ){

                   // categoryList = new ArrayList<>();
                   // categoryList.addAll(tags);

                   // makeSingleCheckParentList = new ArrayList<>();
                  //  makeSingleCheckParentList.addAll(makeSingleCheckParent);

                    setupRecView(makeSingleCheckParent);

                }else  if (error != null){
                    Toast.makeText(ChooseCategoryActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                }
            }

            private void setupRecView(List<SingleCheckItemsExp> makeSingleCheckParent) {


                adapterSingleExp = new AdapterSingleExp(makeSingleCheckParent, ChooseCategoryActivity.this);
                recyclerView.setAdapter(adapterSingleExp);

                adapterSingleExp.setChildClickListener((v, checked, group, childIndex) -> {
                    adapterSingleExp.clearChoices();
                    group.checkChild(childIndex);
                    ChildExp childExp = (ChildExp) group.getItems().get(childIndex);
                     category = childExp.getName();

                });
            }
        });
    }



}
