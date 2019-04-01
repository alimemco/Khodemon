package com.ali.rnp.khodemon.Views.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.ali.rnp.khodemon.ExpandableTags.Expert;
import com.ali.rnp.khodemon.ExpandableTags.SingleCheckGroupingAdapter;
import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.fragments.FragmentDialog;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnCheckChildClickListener;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.ali.rnp.khodemon.ExpandableTags.TagsDataFactory.makeSingleCheckTags;


public class TagChooseActivity extends AppCompatActivity {

    private SingleCheckGroupingAdapter adapter;
    private MyButton chooseButton;
    private Intent intent;
    private String tagLocPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_choose);

        chooseButton = findViewById(R.id.activity_tag_choose_button);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_tag_choose_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        adapter = new SingleCheckGroupingAdapter(makeSingleCheckTags(),this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setChildClickListener(new OnCheckChildClickListener() {
            @Override
            public void onCheckChildCLick(View v, boolean checked, CheckedExpandableGroup group, int childIndex) {
                adapter.clearChoices();
                group.checkChild(childIndex);
                Expert expert = (Expert) group.getItems().get(childIndex);

                tagLocPic = expert.getName();

              }
        });



        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              //  showDialog();
                if (!tagLocPic.equals("")){
                    intent = new Intent();
                    intent.putExtra(AddRule.KEY_CHOOSE_EXPERT, tagLocPic);

                    setResult(Activity.RESULT_OK, intent);
                }


              finish();
            }
        });


    }


    public void showDialog() {
        boolean isLargeLayout = false ;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentDialog fragmentDialog = new FragmentDialog();



        if (isLargeLayout) {
            // The device is using a large layout, so show the fragment as a dialog
            fragmentDialog.show(fragmentManager, "dialog");
        } else {
            // The device is smaller, so show the fragment fullscreen
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            // For a little polish, specify a transition animation
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity
            transaction.add(android.R.id.content, fragmentDialog)
                    .addToBackStack(null).commit();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fragmentDialog.dismiss();
            }
        },3000);
    }


}
