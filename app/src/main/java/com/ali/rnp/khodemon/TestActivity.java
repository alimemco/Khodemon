package com.ali.rnp.khodemon;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ali.rnp.khodemon.Views.CustomViews.CustomLoadingDialog;
import com.roger.catloadinglibrary.CatLoadingView;


public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private Button catBtn,customBtn;
    private CatLoadingView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mView = new CatLoadingView();

        catBtn = findViewById(R.id.test_btn1);
        customBtn = findViewById(R.id.testBtn2);

        catBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.setText("test");
                mView.setCanceledOnTouchOutside(true);
                mView.show(getSupportFragmentManager(),"tag");
            }
        });

        customBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomLoadingDialog cdd=new CustomLoadingDialog(TestActivity.this);

                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                cdd.show();
            }
        });

    }

    @Override
    public void onClick(View v) {

    }


}