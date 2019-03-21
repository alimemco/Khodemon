package com.ali.rnp.khodemon.Views.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.UriAdapter;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.Providers;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.fragments.FragmentAddLevelOne;
import com.ali.rnp.khodemon.Views.fragments.FragmentAddLevelTwo;
import com.zhihu.matisse.Matisse;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class AddRule extends AppCompatActivity implements FragmentAddLevelOne.OnNextButtonClicked{

    private RecyclerView recyclerView;
    private UriAdapter mAdapter;
    private MaterialProgressBar progressBar;
    private MyTextView titleToolbarTextView;
    private MyTextView levelToolbarTextView;

    private FragmentAddLevelOne fragmentAddLevelOne;
    private FragmentAddLevelTwo fragmentAddLevelTwo;
    private FragmentManager fragmentManager;

    private String groupName;

    private static final String TAG = "AddRule";

    private static final int REQUEST_CODE_CHOOSE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rule);

        initViews();

        groupName = getIntent().getStringExtra(Providers.GROUP_NAME_KEY);
        if (groupName!=null)
        Toast.makeText(this, groupName, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Null Bood", Toast.LENGTH_SHORT).show();



        Toolbar toolbar = findViewById(R.id.add_rule_toolbar);
        setSupportActionBar(toolbar);





        setupFragments();



    }

    private void initViews() {
        progressBar = findViewById(R.id.activity_add_rule_progressBar);
        titleToolbarTextView = findViewById(R.id.add_rule_toolbar_textView_title);
        levelToolbarTextView = findViewById(R.id.add_rule_toolbar_textView_level);


    }


    private void setupFragments() {
        fragmentManager = getSupportFragmentManager();

        fragmentAddLevelOne = new FragmentAddLevelOne(this);
        fragmentAddLevelTwo = new FragmentAddLevelTwo();


        replaceNewFragment(fragmentAddLevelOne);
    }

    private void replaceNewFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.add_fragment_container, fragment);

        if (!(fragment instanceof FragmentAddLevelOne)){
            transaction.addToBackStack("FragmentAddLevelOne");
        }

        transaction.commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {

          //  FragmentAddLevelOne.mAdapter.setData(Matisse.obtainResult(data), Matisse.obtainPathResult(data));
            FragmentAddLevelOne.mAdapter.setData(Matisse.obtainResult(data), false,null);

        }

    }


    @Override
    public void onNextClicked(int level) {
        int allLevels=4;

        String levelTxt = " مرحله "+level+" از "+allLevels;
        levelToolbarTextView.setText(levelTxt);
        progressBar.setProgress(level*(100/allLevels));

        switch (level){
            case 2:
                replaceNewFragment(fragmentAddLevelTwo);
                break;
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        if (getVisibleFragment() != null){
            Fragment fragment = getVisibleFragment();
            if (fragment instanceof FragmentAddLevelOne){

                onNextClicked(1);

            }else if (fragment instanceof FragmentAddLevelTwo){




            }else {

            }
        }

    }

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for(Fragment fragment : fragments){
            if(fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }
}
