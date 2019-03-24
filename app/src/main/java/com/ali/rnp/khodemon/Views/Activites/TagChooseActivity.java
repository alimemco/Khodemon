package com.ali.rnp.khodemon.Views.Activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ali.rnp.khodemon.ExpandableRecylerView.Genre;
import com.ali.rnp.khodemon.ExpandableRecylerView.expand.ExpandActivity;
import com.ali.rnp.khodemon.ExpandableRecylerView.expand.GenreAdapter;
import com.ali.rnp.khodemon.ExpandableRecylerView.multicheck.MultiCheckActivity;
import com.ali.rnp.khodemon.ExpandableRecylerView.multitype.MultiTypeActivity;
import com.ali.rnp.khodemon.ExpandableRecylerView.multitypeandcheck.MultiTypeCheckGenreActivity;
import com.ali.rnp.khodemon.ExpandableRecylerView.singlecheck.SingleCheckActivity;
import com.ali.rnp.khodemon.R;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TagChooseActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_choose);

        //TODO Library Expandable RecyclerView api level 16

        Button expand = (Button) findViewById(R.id.expand_button);
        expand.setOnClickListener(this);
   //     expand.setOnClickListener(navigateTo(ExpandActivity.class));

        Button multiSelect = (Button) findViewById(R.id.multi_check_button);
        multiSelect.setOnClickListener(this);
     //   multiSelect.setOnClickListener(navigateTo(MultiCheckActivity.class));

        Button singleSelect = (Button) findViewById(R.id.single_check_button);
        singleSelect.setOnClickListener(this);
     //   singleSelect.setOnClickListener(navigateTo(SingleCheckActivity.class));

        Button mixedSelect = (Button) findViewById(R.id.mixedtype_button);
        mixedSelect.setOnClickListener(this);
      //  mixedSelect.setOnClickListener(navigateTo(MultiTypeActivity.class));

        Button mixedTypeAndCheck = (Button) findViewById(R.id.mixedtype_check_button);
        mixedTypeAndCheck.setOnClickListener(this);
      //  mixedTypeAndCheck.setOnClickListener(navigateTo(MultiTypeCheckGenreActivity.class));



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.expand_button:
                startActivity(new Intent(TagChooseActivity.this,ExpandActivity.class));
                break;

            case R.id.multi_check_button:
                startActivity(new Intent(TagChooseActivity.this,MultiCheckActivity.class));
                break;


            case R.id.single_check_button:
                startActivity(new Intent(TagChooseActivity.this,SingleCheckActivity.class));
                break;


            case R.id.mixedtype_button:
                startActivity(new Intent(TagChooseActivity.this,MultiTypeActivity.class));
                break;


            case R.id.mixedtype_check_button:
                startActivity(new Intent(TagChooseActivity.this,MultiTypeCheckGenreActivity.class));
                break;
        }
    }


}
