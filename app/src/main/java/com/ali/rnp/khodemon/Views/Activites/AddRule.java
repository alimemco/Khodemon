package com.ali.rnp.khodemon.Views.Activites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.UriAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.ProgressBarAnimation;
import com.ali.rnp.khodemon.Providers;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.TestActivity;
import com.ali.rnp.khodemon.UtilsApp.UploadPhotosAsync;
import com.ali.rnp.khodemon.Views.fragments.FragmentAddLevelOne;
import com.ali.rnp.khodemon.Views.fragments.FragmentAddLevelTwo;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zhihu.matisse.Matisse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class AddRule extends AppCompatActivity implements
        FragmentAddLevelOne.OnNextButtonClicked {

    private RecyclerView recyclerView;
    private UriAdapter mAdapter;
    private MaterialProgressBar progressBar;
    private MyTextView titleToolbarTextView;
    private MyTextView levelToolbarTextView;

    private FragmentAddLevelOne fragmentAddLevelOne;
    private FragmentAddLevelTwo fragmentAddLevelTwo;
    private FragmentManager fragmentManager;

    private String groupName;

    private int currentPhoto = 1 ;
    private int allPhoto = 1 ;



    private static final String TAG = "AddRuleApp";

    private static final int REQUEST_CODE_CHOOSE = 23;


    private final int GALLERY = 1;

    JSONObject jsonObject;
    RequestQueue rQueue;

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


/*
        Toolbar toolbar = findViewById(R.id.add_rule_toolbar);
        setSupportActionBar(toolbar);

*/



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


            FragmentAddLevelOne.mAdapter.setData(Matisse.obtainResult(data), false,null);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            List<Uri> contentURIs = Matisse.obtainResult(data);

                            int allPhoto = contentURIs.size();

                            try {

                                for (int i = 0; i < contentURIs.size(); i++) {
                                    Bitmap  bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), contentURIs.get(i));
                                    //uploadImage(bitmap);
                                    ApiService apiService = new ApiService(AddRule.this);
                                    apiService.uploadImage(bitmap, currentPhoto, allPhoto, new ApiService.OnUploadedPhoto() {
                                        @Override
                                        public void OnUploadPhoto(int currentPhotoNum,VolleyError error) {
                                            if (currentPhotoNum != -1 && error == null){
                                               // int progress = (currentPhoto*(100/allPhoto));
                                                int progress = ((currentPhoto*100)/allPhoto);

                                                progressBar.setProgress( progress );
                                                levelToolbarTextView.setText(String.valueOf(progress));
                                                Log.i(TAG, "\n " +
                                                        "current: "+currentPhoto+"\n"+
                                                        "all: "+currentPhoto+"\n"+
                                                        "progress : "+progress);
                                                if (currentPhoto == allPhoto) {
                                                    Toast.makeText(AddRule.this, "Completed", Toast.LENGTH_SHORT).show();
                                                    currentPhoto = 1;
                                                }
                                                currentPhoto++;
                                            }else if (error != null){
                                                titleToolbarTextView.setText(error.toString());
                                            }

                                        }
                                    });

                                }




                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }
                    },1000);



        }

    }






    @Override
    public void onNextClicked(int level) {
        int allLevels=4;
/*
        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, currentProgress, level*(100/allLevels);
        anim.setDuration(700);
        progressBarPass.startAnimation(anim);
*/
/*
        String levelTxt = " مرحله "+level+" از "+allLevels;
        levelToolbarTextView.setText(levelTxt);
        progressBar.setProgress(level*(100/allLevels));
*/


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
