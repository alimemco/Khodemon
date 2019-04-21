package com.ali.rnp.khodemon.Views.Activities;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.City;
import com.ali.rnp.khodemon.DataModel.Tags;
import com.ali.rnp.khodemon.ExpandableSingleItems.AdapterSingleExp;
import com.ali.rnp.khodemon.ExpandableSingleItems.ChildExp;
import com.ali.rnp.khodemon.ExpandableSingleItems.SingleCheckItemsExp;
import com.ali.rnp.khodemon.ExpandableTags.Expert;
import com.ali.rnp.khodemon.ExpandableTags.SingleCheckGroupingAdapter;
import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.fragments.FragmentDialog;
import com.android.volley.VolleyError;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnCheckChildClickListener;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.ali.rnp.khodemon.ExpandableTags.TagsDataFactory.makeSingleCheckTags;


public class TagChooseActivity extends AppCompatActivity {

    //private SingleCheckGroupingAdapter adapter;
    private MyButton chooseButton;
    private Intent intent;

    private List<Tags> tagsList;
    private List<SingleCheckItemsExp> makeSingleCheckParentList;
    private AdapterSingleExp adapterSingleExp;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private String tagName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_choose);

        chooseButton = findViewById(R.id.activity_tag_choose_button);

        initToolbar();
        setupRecyclerView();



        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               //showDialog();
                if (tagName != null && !tagName.equals("")){
                    Intent intent = new Intent();
                    intent.putExtra(ProvidersApp.KEY_CHOOSE_TAGS_FRG_ADD_LVL_ONE,tagName);
                    setResult(Activity.RESULT_OK,intent);
                }


              finish();
            }
        });


    }

    private void initToolbar() {
        toolbar = findViewById(R.id.activity_tag_choose_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.activity_tag_choose_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ApiService apiService = new ApiService(TagChooseActivity.this);

        apiService.getTags(new ApiService.OnTagsReceived() {
            @Override
            public void onReceived(List<SingleCheckItemsExp> makeSingleCheckParent, List<Tags> tags, VolleyError error) {

                if (makeSingleCheckParent != null && tags != null && error == null ){

                    tagsList = new ArrayList<>();
                    tagsList.addAll(tags);

                    makeSingleCheckParentList = new ArrayList<>();
                    makeSingleCheckParentList.addAll(makeSingleCheckParent);

                    setupRecWithExpandable(makeSingleCheckParent);

                }else  if (error != null){
                    Toast.makeText(TagChooseActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                }
            }

            private void setupRecWithExpandable(List<SingleCheckItemsExp> makeSingleCheckParent) {


                adapterSingleExp = new AdapterSingleExp(makeSingleCheckParent, TagChooseActivity.this);
                recyclerView.setAdapter(adapterSingleExp);

                adapterSingleExp.setChildClickListener(new OnCheckChildClickListener() {
                    @Override
                    public void onCheckChildCLick(View v, boolean checked, CheckedExpandableGroup group, int childIndex) {
                        adapterSingleExp.clearChoices();
                        group.checkChild(childIndex);
                        ChildExp childExp = (ChildExp) group.getItems().get(childIndex);
                        // Toast.makeText(TagChooseActivity.this, childExp.getName(), Toast.LENGTH_SHORT).show();
                         tagName = childExp.getName();

                    }
                });
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
