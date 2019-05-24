package com.ali.rnp.khodemon.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.ChoosePersonnelAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;

import java.util.ArrayList;

public class ChoosePersonnelActivity extends AppCompatActivity implements
        ChoosePersonnelAdapter.OnItemClickListener {

    private RecyclerView rcv;
    private int LOCATION_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_personnel);

        initView();

        initRCV();
    }

    private void initView() {
         rcv = findViewById(R.id.activity_choose_personnel_rcv);

         if (getIntent() != null ){
            LOCATION_ID =  getIntent().getIntExtra(ProvidersApp.KEY_LOCATION_ID,0);
         }
    }

    private void initRCV() {
        ApiService apiService = new ApiService(this);
        apiService.getPersonnel(LOCATION_ID, new ApiService.OnPersonnelReceived() {
            @Override
            public void onItemReceived(int status, ArrayList<LocationPeople> locationPeopleList, String error) {

                String msg = "known";

                switch (status){
                    case ProvidersApp.KEY_SUCCESS:

                        if (locationPeopleList != null ) {


                            LinearLayoutManager ln = new LinearLayoutManager(ChoosePersonnelActivity.this,RecyclerView.VERTICAL,false);

                            rcv.setLayoutManager(ln);
                            ChoosePersonnelAdapter personnelAdapter = new ChoosePersonnelAdapter( locationPeopleList);
                            personnelAdapter.setOnItemClickListener(ChoosePersonnelActivity.this);
                            rcv.setAdapter(personnelAdapter);

                        }

                        break;

                    case ProvidersApp.KEY_EMPTY_DATA:

                        msg = "هیچ پرسنلی یافت نشد";

                        break;

                    case ProvidersApp.KEY_JSON_EXCEPTION:
                        msg = error;

                        break;

                    case ProvidersApp.KEY_VOLLEY_ERROR:
                        msg = error;

                        break;
                }

                if (status != ProvidersApp.KEY_SUCCESS)
                    Toast.makeText(ChoosePersonnelActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });


    }




    //choose personnel Adapter
    @Override
    public void onItemClick(int PEOPLE_ID) {
        Toast.makeText(this, "Add This Person "+PEOPLE_ID, Toast.LENGTH_SHORT).show();

    }
}
