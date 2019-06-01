package com.ali.rnp.khodemon.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.InfoAdapter;
import com.ali.rnp.khodemon.Adapter.ScaleAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.Info;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;

import java.util.ArrayList;

public class ScaleActivity extends AppCompatActivity implements ApiService.OnGetInfo {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale);

        int post_id = getIntent().getIntExtra((ProvidersApp.KEY_POST_ID),1);

        ApiService apiService = new ApiService(this);
        apiService.getInfo(post_id,this);
    }



    @Override
    public void OnGetInfo(int statusCode, ArrayList<Info> infoList, String error) {
        switch (statusCode) {
            case ProvidersApp.STATUS_CODE_SUCCESSFULLY:

                if (infoList != null) {
                    RecyclerView ScaleRcv = findViewById(R.id.activity_scale_rcv);
                    LinearLayoutManager ln = new LinearLayoutManager(ScaleActivity.this, RecyclerView.VERTICAL, false);
                    ScaleAdapter scaleAdapter = new ScaleAdapter(infoList);
                    ScaleRcv.setLayoutManager(ln);
                    ScaleRcv.setAdapter(scaleAdapter);

                }
                break;

            case ProvidersApp.STATUS_CODE_JSON_EXCEPTION_ERROR:
            case ProvidersApp.STATUS_CODE_VOLLEY_ERROR:
            case ProvidersApp.STATUS_CODE_SERVER_ERROR:

                Toast.makeText(this, statusCode + " -> " + error, Toast.LENGTH_LONG).show();
                break;

        }
    }
}
