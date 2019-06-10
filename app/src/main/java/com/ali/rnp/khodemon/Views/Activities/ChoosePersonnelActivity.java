package com.ali.rnp.khodemon.Views.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.ChoosePersonnelAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.Dialogs.ConfirmDialog;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;

import java.util.ArrayList;

public class ChoosePersonnelActivity extends AppCompatActivity implements
        ChoosePersonnelAdapter.OnItemClickListener,
        ApiService.OnGetPersonList,
        ApiService.OnAddPersonnel {

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
        apiService.getPersonList(this);

    }




    //choose personnel Adapter
    @Override
    public void onItemClick(LocationPeople locationPeople) {
       // Toast.makeText(this, "Add This Person "+PEOPLE_ID, Toast.LENGTH_SHORT).show();
       // int PEOPLE_ID = locationPeople.getId();
        showConfirmDialog(locationPeople);

        showDialog(locationPeople);


    }

    private void showDialog(LocationPeople locationPeople) {
        int PEOPLE_ID = locationPeople.getId();

        String q = "آیا می خواهید " +
                locationPeople.getName() +
                " را به پرسنل این مکان اضافه کنید ؟";

        ConfirmDialog dialog = ConfirmDialog.newInstance(q);
        dialog.show(getSupportFragmentManager(),"tag");

    }

    private void showConfirmDialog(LocationPeople locationPeople) {
        int PEOPLE_ID = locationPeople.getId();

        StringBuilder question = new StringBuilder();
        question.append("آیا می خواهید ");
        question.append(locationPeople.getName());
        question.append(" را به پرسنل این مکان اضافه کنید ؟");


        new AlertDialog.Builder(this)
                .setTitle(locationPeople.getName())
                .setMessage(question)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                   ApiService apiService = new ApiService(this);
                   apiService.addPersonnel(LOCATION_ID,PEOPLE_ID,ChoosePersonnelActivity.this);
                })
                .setNegativeButton(android.R.string.no, (dialog, which) -> {
                    dialog.dismiss();
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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
            Toast.makeText(this, "با موفقیت اضافه شد", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.putExtra(ProvidersApp.KEY_LOCATION_ID,LOCATION_ID);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }else  if (error != null){
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }
}
