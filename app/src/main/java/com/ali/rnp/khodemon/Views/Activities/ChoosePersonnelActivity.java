package com.ali.rnp.khodemon.Views.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.ChoosePersonnelAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.Dialogs.ConfirmDialog;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.UtilsApp.UtilsApp;

import java.util.ArrayList;

public class ChoosePersonnelActivity extends AppCompatActivity implements
        ChoosePersonnelAdapter.OnItemClickListener,
        ApiService.OnGetPersonList,
        ApiService.OnAddPersonnel,
        ConfirmDialog.OnClickButtonDialog{

    private RecyclerView rcv;
    private Toolbar toolbar;
    private ConfirmDialog dialog;
    private int LOCATION_ID;
    private int PEOPLE_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_personnel);

        initView();
        initRCV();
        initToolbar();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.activity_choose_personnel_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){

            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);


        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void initView() {
        rcv = findViewById(R.id.activity_choose_personnel_rcv);

        if (getIntent() != null ){
            LOCATION_ID =  getIntent().getIntExtra(ProvidersApp.KEY_LOCATION_ID,0);
        }
    }

    private void initRCV() {
        ApiService apiService = new ApiService(this);
        apiService.getPersonList(this);

    }


    //choose personnel Adapter
    @Override
    public void onItemClick(LocationPeople locationPeople) {

        showDialog(locationPeople);


    }

    private void showDialog(LocationPeople locationPeople) {
        PEOPLE_ID = locationPeople.getId();

        String question =
                locationPeople.getName()
                        +"\n"+
                        " را اضافه می کنید ؟";

        dialog = new ConfirmDialog.Builder()
                .setQuestion(question)
                .setLocationPeople(locationPeople)
                .setPositiveListener(this)
                .setNegativeButton(this)
                .create();
        dialog.show(getSupportFragmentManager(),"tag");


    }

    @Override
    public void onReceived(int successCode, ArrayList<LocationPeople> locationPeopleList, String error) {

        String msg = "";
        switch (successCode){
            case ProvidersApp.STATUS_CODE_SUCCESSFULLY:

                if (locationPeopleList != null){
                    LinearLayoutManager ln = new LinearLayoutManager(ChoosePersonnelActivity.this,RecyclerView.VERTICAL,false);

                    rcv.setLayoutManager(ln);
                    ChoosePersonnelAdapter personnelAdapter = new ChoosePersonnelAdapter( locationPeopleList);
                    personnelAdapter.setOnItemClickListener(ChoosePersonnelActivity.this);
                    rcv.setAdapter(personnelAdapter);
                }

                break;

            case ProvidersApp.STATUS_CODE_VOLLEY_ERROR:
                msg = successCode+" | "+error ;
                break;

            case ProvidersApp.STATUS_CODE_JSON_EXCEPTION_ERROR:
                msg = successCode+" | "+error ;
                break;

            case ProvidersApp.STATUS_CODE_SERVER_ERROR:
                msg = successCode+" | "+error ;
                break;

        }

        if (successCode!= ProvidersApp.STATUS_CODE_SUCCESSFULLY){
            Toast.makeText(this, msg , Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAdded(int successCode, String error) {
        if (successCode == ProvidersApp.STATUS_CODE_SUCCESSFULLY){
            Toast.makeText(ChoosePersonnelActivity.this, "با موفقیت اضافه شد", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.putExtra(ProvidersApp.KEY_LOCATION_ID,LOCATION_ID);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }else  if (error != null){
            String msg = UtilsApp.statusCodeToError(successCode,error);
            Toast.makeText(ChoosePersonnelActivity.this, msg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClickDialog(Dialog dialog, int which) {

        if (which == ConfirmDialog.BUTTON_POSITIVE){
            ApiService apiService = new ApiService(ChoosePersonnelActivity.this);
            apiService.addPersonnel(LOCATION_ID, PEOPLE_ID,this);
        }else if (which == ConfirmDialog.BUTTON_NEGATIVE){
            dialog.dismiss();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dialog != null)
            dialog.dismiss();
    }
}