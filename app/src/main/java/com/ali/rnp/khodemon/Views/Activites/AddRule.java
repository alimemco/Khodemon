package com.ali.rnp.khodemon.Views.Activites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.UriAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.MyApplication;
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
import com.shuhart.stepview.StepView;
import com.zhihu.matisse.Matisse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class AddRule extends AppCompatActivity implements
        FragmentAddLevelOne.OnNextButtonClicked,
        FragmentAddLevelOne.OnChooseButtonClicked {

    private RecyclerView recyclerView;
    private UriAdapter mAdapter;

    private MyTextView titleToolbarTextView;
    private MyTextView levelToolbarTextView;
    private MyTextView percentageToolbarTextView;

    private ImageView photosImageView;

    private StepView stepView;



    private MaterialProgressBar materialProgressBar;

    private FragmentAddLevelOne fragmentAddLevelOne;
    private FragmentAddLevelTwo fragmentAddLevelTwo;
    private FragmentManager fragmentManager;

    private String groupName;

    private Intent dataFromMatisse;

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
        initStepView();
/*
        groupName = getIntent().getStringExtra(Providers.GROUP_NAME_KEY);
        if (groupName!=null)
        Toast.makeText(this, groupName, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Null Bood", Toast.LENGTH_SHORT).show();


*/



        setupFragments();




    }

    private void initStepView() {
        stepView = findViewById(R.id.step_view);

        stepView.getState()
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .commit();

        stepView.done(false);


        stepView.setOnStepClickListener(new StepView.OnStepClickListener() {
            @Override
            public void onStepClick(int step) {
                Toast.makeText(AddRule.this, "step: "+step, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {

        titleToolbarTextView = findViewById(R.id.add_rule_toolbar_textView_title);
        levelToolbarTextView = findViewById(R.id.add_rule_toolbar_textView_level);
        percentageToolbarTextView= findViewById(R.id.add_rule_toolbar_textView_percentage);
        photosImageView = findViewById(R.id.add_rule_photo_imageView);

        materialProgressBar = findViewById(R.id.add_rule_progressBar);


        
        materialProgressBar.setVisibility(View.INVISIBLE);
        levelToolbarTextView.setText(String.valueOf(0));



    }


    private void setupFragments() {
        fragmentManager = getSupportFragmentManager();

        fragmentAddLevelOne = new FragmentAddLevelOne(this,this);
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

            List<Uri> contentURIs = Matisse.obtainResult(data);
            levelToolbarTextView.setText(String.valueOf(contentURIs.size()));


            dataFromMatisse = data;


        }else if (requestCode == 555 && resultCode == RESULT_OK){
            String title = data.getStringExtra("title");
            Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
        }

    }






    @Override
    public void onNextClicked(int level) {

        stepView.go(level-1, true);

        switch (level){
            case 2:
                replaceNewFragment(fragmentAddLevelTwo);

                if (dataFromMatisse!=null){
                uploadImage();
            }

                break;
        }



    }

    private void uploadImage() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                List<Uri> contentURIs = Matisse.obtainResult(dataFromMatisse);

                int allPhoto = contentURIs.size();

                materialProgressBar.setVisibility(View.VISIBLE);
                String imagesCount =
                        0+"/"+allPhoto;

                levelToolbarTextView.setText(imagesCount);
                percentageToolbarTextView.setText("0%");

                try {

                    for (int i = 0; i < contentURIs.size(); i++) {
                        Bitmap  bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), contentURIs.get(i));

                        ApiService apiService = new ApiService(AddRule.this);
                        apiService.uploadImage(bitmap, currentPhoto, allPhoto, new ApiService.OnUploadedPhoto() {
                            @Override
                            public void OnUploadPhoto(int currentPhotoNum,VolleyError error) {
                                if (currentPhotoNum != -1 && error == null){

                                    int progress = ((currentPhoto*100)/allPhoto);

                                    String imagesCount = currentPhoto+"/"+allPhoto;

                                    String percentage = progress+"%";
                                    levelToolbarTextView.setText(imagesCount);
                                    percentageToolbarTextView.setText(percentage);

                                    if (currentPhoto == allPhoto) {
                                       levelToolbarTextView.setText(String.valueOf(allPhoto));
                                        percentageToolbarTextView.setText("");
                                        materialProgressBar.setVisibility(View.INVISIBLE);

                                        DrawableCompat.setTint(photosImageView.getDrawable(), ContextCompat.getColor(AddRule.this, R.color.colorPrimary));
                                        levelToolbarTextView.setTextColor(ContextCompat.getColor(AddRule.this,R.color.colorPrimary));
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
        },1);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();



        if (getVisibleFragment() != null){
            Fragment fragment = getVisibleFragment();
            if (fragment instanceof FragmentAddLevelOne){

                onNextClicked(1);
                if (dataFromMatisse != null){
                    FragmentAddLevelOne.mAdapter.setData(Matisse.obtainResult(dataFromMatisse), false,null);
                }
                

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


    @Override
    public void onChooseClicked() {
        startActivityForResult(new Intent(AddRule.this,TagChooseActivity.class),555);

    }
}
