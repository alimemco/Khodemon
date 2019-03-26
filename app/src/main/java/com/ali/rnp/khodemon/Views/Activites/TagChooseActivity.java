package com.ali.rnp.khodemon.Views.Activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ali.rnp.khodemon.ExpandableRecylerView.singlecheck.SingleCheckGenreAdapter;
import com.ali.rnp.khodemon.ExpandableTags.Expert;
import com.ali.rnp.khodemon.ExpandableTags.SingleCheckGroupingAdapter;
import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.fragments.FragmentAddLevelOne;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnCheckChildClickListener;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablerecyclerview.listeners.OnGroupClickListener;

import afu.org.checkerframework.checker.oigj.qual.O;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.ali.rnp.khodemon.ExpandableRecylerView.GenreDataFactory.makeSingleCheckGenres;
import static com.ali.rnp.khodemon.ExpandableTags.TagsDataFactory.makeSingleCheckTags;


public class TagChooseActivity extends AppCompatActivity {

    private SingleCheckGroupingAdapter adapter;
    private MyButton chooseButton;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_choose);

        chooseButton = findViewById(R.id.activity_tag_choose_button);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_tag_choose_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        adapter = new SingleCheckGroupingAdapter(makeSingleCheckTags());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setChildClickListener(new OnCheckChildClickListener() {
            @Override
            public void onCheckChildCLick(View v, boolean checked, CheckedExpandableGroup group, int childIndex) {
                adapter.clearChoices();
                group.checkChild(childIndex);
                Expert expert = (Expert) group.getItems().get(childIndex);
                Toast.makeText(TagChooseActivity.this, expert.getName(), Toast.LENGTH_SHORT).show();
            }
        });



        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



               // finish();
            }
        });


    }

/*
    @Override
    public void OnItemSelect(String title) {
        Toast.makeText(TagChooseActivity.this, title, Toast.LENGTH_SHORT).show();

        intent = new Intent();
        intent.putExtra("title", title);
        setResult(Activity.RESULT_OK, intent);
    }*/
}
