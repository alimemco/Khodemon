package com.ali.rnp.khodemon;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

    boolean isSelected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        TextView txt = findViewById(R.id.testActivity_txt);

        isSelected = false;

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelected = !isSelected;
                txt.setSelected(isSelected);
            }
        });




    }
}