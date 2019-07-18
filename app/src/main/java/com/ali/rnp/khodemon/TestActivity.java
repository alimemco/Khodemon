package com.ali.rnp.khodemon;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import com.ali.rnp.khodemon.Search.ChildModel;
import com.ali.rnp.khodemon.Search.GroupModel;
import com.ali.rnp.khodemon.Search.SearchAdapter;
import com.ali.rnp.khodemon.UtilsApp.UtilsApp;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class TestActivity extends AppCompatActivity
implements SearchAdapter.OnChildClickListener{

    MaterialButton btn;
    boolean isCheckedChip;

    private String[] groupNames  = new String[]{"پیشنهادی", "در شهر من" ,"جدیدترین ها","پر بازدید ترین ها"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        initRcv();




        Chip chip = findViewById(R.id.test_chips);
        btn = findViewById(R.id.test_btn);
        isCheckedChip = chip.isChecked();


                chip.setOnClickListener(v -> {

                    chip.setCloseIconVisible(true);
                    isCheckedChip = !isCheckedChip;
                    chip.setChecked(isCheckedChip);

                    if (isCheckedChip){
                        animateExpand(btn);
                    }else {
                        animateCollapse(btn);
                    }
                });

        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chip.setCloseIconVisible(false);
            }
        });


    }

    private void initRcv() {

        RecyclerView rcv = findViewById(R.id.testRcv);
        LinearLayoutManager lm = new LinearLayoutManager(TestActivity.this,RecyclerView.VERTICAL,false);

       // SearchAdapter searchAdapter = new SearchAdapter(makeList());
       // searchAdapter.setOnChildClickListener(this);


       // rcv.setLayoutManager(lm);
       // rcv.setAdapter(searchAdapter);
    }

    private ArrayList<GroupModel> makeList() {

        ArrayList<GroupModel> groupModels = new ArrayList<>();
        ArrayList<ChildModel> childModels = null;

        for (int i = 0; i < groupNames.length; i++) {
            childModels = new ArrayList<>();

            for (int j = 0; j < UtilsApp.randomInteger(2,6); j++) {


                ChildModel childModel = new ChildModel("نام","دسته بندی");
                childModels.add(childModel);


            }

            GroupModel groupModel = new GroupModel(groupNames[i],childModels);
            groupModels.add(groupModel);

        }
        return groupModels;

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_navigation_test, menu);
        return true;
    }


    private void animateCollapse(View view) {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        view.setAnimation(rotate);
    }

    private void animateExpand(View view) {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        view.setAnimation(rotate);
    }

    @Override
    public void onChildClick(ChildModel childModel) {
        Toast.makeText(this, childModel.getName(), Toast.LENGTH_SHORT).show();
    }
}