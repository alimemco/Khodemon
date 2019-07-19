package com.ali.rnp.khodemon.Views.Activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Search.ChildModel;
import com.ali.rnp.khodemon.Search.GroupModel;
import com.ali.rnp.khodemon.Search.SearchAdapter;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class testUnit extends AppCompatActivity implements SearchAdapter.OnChildClickListener{

    private EditText editText;
    private MaterialButton btn;
    private RecyclerView rcv;

    private String category = "";
    private String typed = "";
    private JSONObject jsonObject;
    private ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_unit);

        editText = findViewById(R.id.testUnitEditText);
        btn = findViewById(R.id.testUnitButton);
        rcv = findViewById(R.id.testUnitRcv);
        apiService = new ApiService(testUnit.this);
        jsonObject = new JSONObject();

        rcv.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                apiService.getAllItems(new ApiService.OnGetAllItems() {
                    @Override
                    public void OnSuccessSearch(ArrayList<LocationPeople> locationPeopleList) {
                        if (locationPeopleList != null){
                            com.ali.rnp.khodemon.Adapter.SearchAdapter searchAdapter =new com.ali.rnp.khodemon.Adapter.SearchAdapter();
                            //searchAdapter.setData(typed);
                            searchAdapter.setData(locationPeopleList,"");
                           // searchAdapter.setOnChildClickListener(testUnit.this);
                            rcv.setAdapter(searchAdapter);
                        }
                    }

                    @Override
                    public void OnErrorSearch(Object error) {
                        Toast.makeText(testUnit.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                typed = editable.toString();

                if (!typed.equals("")) {

                    sendJson();

                }



            }
        });

    }

    @Override
    public void onChildClick(ChildModel childModel) {
        Toast.makeText(this, childModel.getName(), Toast.LENGTH_SHORT).show();
    }

    private void sendJson() {

        try {

/*
            if (!category.equals("")) {

                jsonObject.put(ProvidersApp.KEY_CATEGORY, category);
            }*/


                jsonObject.put(ProvidersApp.KEY_KEYWORD, typed);


            apiService.searchCategory(jsonObject, new ApiService.OnSearchCategory() {
                @Override
                public void OnSuccessSearch(ArrayList<GroupModel> groupModels) {

                    // SearchAdapter searchAdapter = new SearchAdapter(testUnit.this,groupModels);
                    //searchAdapter.setData(typed);
                    // searchAdapter.setOnChildClickListener(testUnit.this);
                    // rcv.setAdapter(searchAdapter);
                }

                @Override
                public void OnErrorSearch(Object error) {
                    Toast.makeText(testUnit.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
