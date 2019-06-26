package com.ali.rnp.khodemon;

import android.os.Bundle;

import com.ali.rnp.khodemon.RcvHeader.ChildModel;
import com.ali.rnp.khodemon.RcvHeader.CustomAdapter;
import com.ali.rnp.khodemon.RcvHeader.HeaderModel;
import com.ali.rnp.khodemon.RcvHeader.ListItem;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TestActivity extends AppCompatActivity {


    private static final String TAG = "TestActivityExample";

    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;

    private String[] vehicleTypes = new String[]{"ماشین", "دوچرخه",
            "هواپیما","ماشین قدیمی"};

    private ArrayList<ListItem> listItemArrayList;

    private String[] childNames = new String[]{"Range Rover", "Lamborghini",
            "Rolls Royce","Ferrari","Harley davidson","Ducati","BMW","Honda","Boeing","Airbus","Royal Air","Space X","Horse","Elephant","Camel","Donkey"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        recyclerView = findViewById(R.id.testActivity_rcv);

        listItemArrayList = new ArrayList<>();
        populateList();

        customAdapter = new CustomAdapter(listItemArrayList);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                RecyclerView.VERTICAL, false));





    }

    private void populateList(){

        int headerdone = 0, childdone = 0;

        for(int i = 0; i < 20; i++){

            if(i == 0 || i == 5 | i == 10 | i == 15){
                HeaderModel header = new HeaderModel();
                header.setHeader(vehicleTypes[headerdone]);
                listItemArrayList.add(header);
                headerdone = headerdone + 1;
            }else {
                ChildModel child = new ChildModel();
                child.setChild(childNames[childdone]);
                listItemArrayList.add(child);
                childdone = childdone + 1;
            }
        }

    }





}