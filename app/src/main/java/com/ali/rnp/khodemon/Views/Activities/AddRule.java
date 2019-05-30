package com.ali.rnp.khodemon.Views.Activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.UriAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.PictureUpload;
import com.ali.rnp.khodemon.Dialogs.DialogCompleteAdd;
import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.UtilsApp.Utils;
import com.ali.rnp.khodemon.Views.fragments.FragmentAddLevelFour;
import com.ali.rnp.khodemon.Views.fragments.FragmentAddLevelOne;
import com.ali.rnp.khodemon.Views.fragments.FragmentAddLevelThree;
import com.ali.rnp.khodemon.Views.fragments.FragmentAddLevelTwo;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.model.LatLng;
import com.shuhart.stepview.StepView;
import com.zhihu.matisse.Matisse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class AddRule extends AppCompatActivity implements
        View.OnClickListener,
        FragmentAddLevelOne.OnViewClickListener,
        ApiService.OnAddPictures {

    private RecyclerView recyclerView;
    private UriAdapter mAdapter;

    private MyTextView titleToolbarTextView;
    private MyTextView levelToolbarTextView;
    private MyTextView percentageToolbarTextView;
    private MyButton nextLevelButton;


    private JSONObject jsonObjectLocation;


    private ImageView photosImageView;

    private StepView stepView;


    private MaterialProgressBar materialProgressBarToolbar;

    private FragmentAddLevelOne fragmentAddLevelOne;
    private FragmentAddLevelTwo fragmentAddLevelTwo;
    private FragmentAddLevelThree fragmentAddLevelThree;
    private FragmentAddLevelFour fragmentAddLevelFour;
    private FragmentManager fragmentManager;

    private String groupName;

    private Intent dataFromMatisse;

    private int currentPhoto = 1;
    private int allPhoto = 1;
    private boolean isUploadPhotosSuccess = false;
    private boolean inProgressUpload = false;

    private List<String> imageUrlList;
    private List<PictureUpload> pictureUploadList;

    private String originalPhotoUrl;
    private String originalPhotoUrlThumb="LOCALE";

    private static final String TAG = "AddRuleApp";

    private static final int REQUEST_CODE_CHOOSE = 23;
    //private static final int REQUEST_CODE_CHOOSE_EXPERT = 6363;
    // private static final int REQUEST_CODE_CHOOSE_LOCATION_MAP = 7259;

    // public static final String KEY_CHOOSE_TAGS_FRG_ADD_LVL_ONE = "Expert";
    private int STEP_LEVEL_ONE = 0;
    private int STEP_LEVEL_TWO = 1;
    private int STEP_LEVEL_THREE = 2;
    private int STEP_LEVEL_FOUR = 3;


    // private final int GALLERY = 1;

    //JSONObject jsonObject;
    //RequestQueue rQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rule);

        initViews();
        initStepView();

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
                Toast.makeText(AddRule.this, "step: " + step, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {

        titleToolbarTextView = findViewById(R.id.add_rule_toolbar_textView_title);
        levelToolbarTextView = findViewById(R.id.add_rule_toolbar_textView_level);
        percentageToolbarTextView = findViewById(R.id.add_rule_toolbar_textView_percentage);
        photosImageView = findViewById(R.id.add_rule_photo_imageView);
        nextLevelButton = findViewById(R.id.activity_add_rule_nextButton);

        materialProgressBarToolbar = findViewById(R.id.add_rule_progressBar);


        nextLevelButton.setOnClickListener(this);


        materialProgressBarToolbar.setVisibility(View.INVISIBLE);
        levelToolbarTextView.setText(String.valueOf(0));

        DrawableCompat.setTint(photosImageView.getDrawable(), ContextCompat.getColor(AddRule.this, R.color.dark_gray));

        jsonObjectLocation = new JSONObject();


        groupName = getIntent().getStringExtra(ProvidersApp.GROUP_NAME_KEY);
        try {
            jsonObjectLocation.put(ProvidersApp.KEY_JSON_OBJECT_GROUP_NAME, groupName);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    private void setupFragments() {
        fragmentManager = getSupportFragmentManager();

        fragmentAddLevelOne = new FragmentAddLevelOne(this);
        fragmentAddLevelTwo = new FragmentAddLevelTwo();
        fragmentAddLevelThree = new FragmentAddLevelThree();
        fragmentAddLevelFour = new FragmentAddLevelFour();

        replaceNewFragment(fragmentAddLevelOne);

    }

    private void replaceNewFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.add_fragment_container, fragment);
        transaction.addToBackStack(fragment.toString());
        transaction.commit();


    }


    private void uploadImage() {

        imageUrlList = new ArrayList<>();
        pictureUploadList = new ArrayList<>();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                isUploadPhotosSuccess = true;
                List<Uri> contentURIs = Matisse.obtainResult(dataFromMatisse);

                int allPhoto = contentURIs.size();

                materialProgressBarToolbar.setVisibility(View.VISIBLE);
                String imagesCount =
                        0 + "/" + allPhoto;

                levelToolbarTextView.setText(imagesCount);
                percentageToolbarTextView.setText("0%");

                try {
                    //int pic_id = -2 ;
                    for (int i = 0; i < contentURIs.size(); i++) {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), contentURIs.get(i));


                        PictureUpload pictureUpload = new PictureUpload();
                        pictureUpload.setPic_id(i);
                        pictureUpload.setPic_name(getFileName(contentURIs.get(i)));
                        pictureUpload.setIs_original(i == 0);

                        pictureUploadList.add(pictureUpload);
/*
                        String originalImageName = "";

                        if (i == 0) {
                            originalImageName = imageName;
                        }
*/

                        ApiService apiService = new ApiService(AddRule.this);
                        // String finalOriginalImageName = originalImageName;
                        apiService.uploadImage(bitmap, pictureUploadList.get(i).getPic_name(), pictureUploadList.get(i).getPic_id(), groupName, currentPhoto, allPhoto, new ApiService.OnUploadedPhoto() {
                            @Override
                            public void OnUploadPhoto(PictureUpload pictureUploaded, int currentPhotoNum, VolleyError error) {

                                inProgressUpload = false;
                                imageUrlList.add(pictureUploaded.getPic_address());


                                if (currentPhotoNum != -1 && error == null) {

                                    String urlCheck = isOriginalImage(pictureUploaded.getPic_address(), pictureUploadList.get(0).getPic_name());
                                    if (!urlCheck.equals("")) {
                                        originalPhotoUrl = pictureUploaded.getPic_address();
                                        originalPhotoUrlThumb = pictureUploaded.getThumb_150();
                                      //  Toast.makeText(AddRule.this, originalPhotoUrlThumb, Toast.LENGTH_SHORT).show();

                                    }
                                    int progress = ((currentPhoto * 100) / allPhoto);

                                    String imagesCount = currentPhoto + "/" + allPhoto;

                                    //  String percentage = progress + "%";
                                    StringBuilder percentage = new StringBuilder();
                                    percentage.append(progress);
                                    percentage.append("%");
                                    levelToolbarTextView.setText(imagesCount);
                                    percentageToolbarTextView.setText(percentage);


                                    pictureUpload.setPic_address(pictureUploaded.getPic_address());
                                    pictureUpload.setWidth(pictureUploaded.getWidth());
                                    pictureUpload.setHeight(pictureUploaded.getHeight());
                                    pictureUpload.setThumb_150(pictureUploaded.getThumb_150());
                                    pictureUpload.setThumb_1000(pictureUploaded.getThumb_1000());

                                    pictureUploadList.set(pictureUploaded.getPic_id(), pictureUpload);


                                    if (currentPhoto == allPhoto) {
                                        levelToolbarTextView.setText(String.valueOf(allPhoto));
                                        percentageToolbarTextView.setText("");
                                        materialProgressBarToolbar.setVisibility(View.INVISIBLE);

                                        DrawableCompat.setTint(photosImageView.getDrawable(), ContextCompat.getColor(AddRule.this, R.color.colorPrimary));
                                        levelToolbarTextView.setTextColor(ContextCompat.getColor(AddRule.this, R.color.colorPrimary));

                                        isUploadPhotosSuccess = true;

                                        try {


                                            jsonObjectLocation.put(ProvidersApp.KEY_JSON_OBJECT_LOCATION_ORIGINAL_IMAGE, originalPhotoUrl);
                                            jsonObjectLocation.put(ProvidersApp.KEY_JSON_OBJECT_LOCATION_THUMB_PIC, originalPhotoUrlThumb);

                                            Log.i(TAG, "jsonObjectLocation: "+jsonObjectLocation.toString());



                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }

                                    currentPhoto++;
                                } else if (error != null) {
                                    Toast.makeText(AddRule.this, error.toString(), Toast.LENGTH_LONG).show();
                                }

                            }
                        });

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }, 1);
    }

    public String getFileName(Uri uri) {
        String result = null;
        String timeCu = String.valueOf(Calendar.getInstance().getTimeInMillis());
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }

        return timeCu + "-" + result;
    }

    private String isOriginalImage(String url, String fileName) {

        url = url.substring(url.lastIndexOf("/") + 1);
        if (url.startsWith(fileName) && !fileName.equals("")) {
            return url;
        } else {
            return "";
        }


    }

    @Override
    public void onBackPressed() {
        if (getVisibleFragment() != null) {
            Fragment fragment = getVisibleFragment();
            if (fragment instanceof FragmentAddLevelOne) {

                finish();

            } else if (fragment instanceof FragmentAddLevelTwo) {

                levelBackManager(1);
                if (dataFromMatisse != null) {
                    FragmentAddLevelOne.mAdapter.setData(Matisse.obtainResult(dataFromMatisse), false, null);
                }

            } else if (fragment instanceof FragmentAddLevelThree) {
                levelBackManager(2);
            } else if (fragment instanceof FragmentAddLevelFour) {
                levelBackManager(3);
            }

        }

        super.onBackPressed();

    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    private void fragmentAddLevelManager() throws JSONException {


        if (getVisibleFragment() != null) {
            Fragment fragment = getVisibleFragment();
            if (fragment instanceof FragmentAddLevelOne) {

                replaceNewFragment(fragmentAddLevelTwo);

                stepView.go(STEP_LEVEL_TWO, true);

                if (dataFromMatisse != null &&
                        !inProgressUpload &&
                        !isUploadPhotosSuccess && Utils.isConnectedToNetwork(this)
                ) {
                    uploadImage();
                }


                if (FragmentAddLevelOne.ownerSeller.getText() != null &&
                        !FragmentAddLevelOne.ownerSeller.getText().toString().equals("")) {
                    jsonObjectLocation.put(ProvidersApp.KEY_JSON_OBJECT_LOCATION_OWNER_SELLER_NAME, FragmentAddLevelOne.ownerSeller.getText().toString());

                }


            } else if (fragment instanceof FragmentAddLevelTwo) {
                replaceNewFragment(fragmentAddLevelThree);

                stepView.go(STEP_LEVEL_THREE, true);


                if (FragmentAddLevelTwo.addressEdiText.getText() != null &&
                        !FragmentAddLevelTwo.addressEdiText.getText().toString().equals("")) {
                    jsonObjectLocation.put(ProvidersApp.KEY_JSON_OBJECT_LOCATION_ADDRESS, FragmentAddLevelTwo.addressEdiText.getText().toString());

                }


            } else if (fragment instanceof FragmentAddLevelThree) {
                replaceNewFragment(fragmentAddLevelFour);

                stepView.go(STEP_LEVEL_FOUR, true);


                if (FragmentAddLevelOne.nameLocation.getText() != null &&
                        FragmentAddLevelOne.ownerSeller.getText() != null) {
                    jsonObjectLocation.put(ProvidersApp.KEY_JSON_OBJECT_LOCATION_NAME, FragmentAddLevelOne.nameLocation.getText().toString());
                    //jsonObjectLocation.put(ProvidersApp.KEY_JSON_OBJECT_LOCATION_OWNER_SELLER, FragmentAddLevelOne.ownerSeller.getText().toString());
                    jsonObjectLocation.put(ProvidersApp.KEY_JSON_OBJECT_LOCATION_TAG, FragmentAddLevelOne.chooseTagTextView.getText().toString());

                }

                if (FragmentAddLevelThree.dimenEditText.getText() != null &&
                        !FragmentAddLevelThree.dimenEditText.getText().toString().equals("")) {
                    jsonObjectLocation.put(ProvidersApp.KEY_JSON_OBJECT_LOCATION_DIMEN, FragmentAddLevelThree.dimenEditText.getText().toString());
                }

                if (FragmentAddLevelThree.phoneEditText.getText() != null &&
                        !FragmentAddLevelThree.phoneEditText.getText().toString().equals("")) {
                    jsonObjectLocation.put(ProvidersApp.KEY_JSON_OBJECT_LOCATION_PHONE, FragmentAddLevelThree.phoneEditText.getText().toString());
                }

                if (FragmentAddLevelThree.sinceEditText.getText() != null &&
                        !FragmentAddLevelThree.sinceEditText.getText().toString().equals("")) {
                    jsonObjectLocation.put(ProvidersApp.KEY_JSON_OBJECT_LOCATION_SINCE, FragmentAddLevelThree.sinceEditText.getText().toString());
                }


            } else if (fragment instanceof FragmentAddLevelFour) {


                if (jsonObjectLocation != null) {
                    ApiService apiService = new ApiService(AddRule.this);
                    apiService.addLocation(jsonObjectLocation, new ApiService.OnAddLocationPeople() {
                        @Override
                        public void OnAdded(int last_id, String error) {
                            if (last_id >= 0 && error == null && pictureUploadList != null) {

                                // Toast.makeText(AddRule.this, result, Toast.LENGTH_LONG).show();
                                // Log.i(TAG, "OnAdded: RES "+result);

                                for (int i = 0; i < pictureUploadList.size(); i++) {

                                    try {
                                        JSONObject jsonObjectPic = new JSONObject();
                                        jsonObjectPic.put(ProvidersApp.KEY_PICTURE_UPLOAD_POST_ID, last_id);
                                        jsonObjectPic.put(ProvidersApp.KEY_PICTURE_UPLOAD_IS_ORIGINAL, pictureUploadList.get(i).getIs_original());
                                        jsonObjectPic.put(ProvidersApp.KEY_PICTURE_UPLOAD_PIC_ID, pictureUploadList.get(i).getPic_id());
                                        jsonObjectPic.put(ProvidersApp.KEY_PICTURE_UPLOAD_PIC_ADDRESS, pictureUploadList.get(i).getPic_address());

                                        jsonObjectPic.put(ProvidersApp.KEY_PICTURE_UPLOAD_WIDTH, pictureUploadList.get(i).getWidth());
                                        jsonObjectPic.put(ProvidersApp.KEY_PICTURE_UPLOAD_HEIGHT, pictureUploadList.get(i).getHeight());
                                        jsonObjectPic.put(ProvidersApp.KEY_PICTURE_UPLOAD_THUMB_150, pictureUploadList.get(i).getThumb_150());
                                        jsonObjectPic.put(ProvidersApp.KEY_PICTURE_UPLOAD_THUMB_1000, pictureUploadList.get(i).getThumb_1000());


                                        apiService.addPicture(jsonObjectPic, AddRule.this);


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }


                                DialogCompleteAdd dialog = new DialogCompleteAdd();
                                dialog.show(fragmentManager, "AddRule");

                            } else  if (error !=null){

                                Toast.makeText(AddRule.this, error, Toast.LENGTH_LONG).show();

                            } else {
                                DialogCompleteAdd dialog = new DialogCompleteAdd();
                                dialog.show(fragmentManager, "AddRule");
                             }
                        }
                    });
                }


            }
        }

    }

    private void levelBackManager(int level) {

        stepView.go(level - 1, true);

        switch (level) {
            case 1:
                // replaceNewFragment(fragmentAddLevelOne);

                break;

            case 2:
                //  replaceNewFragment(fragmentAddLevelTwo);
/*
                if (dataFromMatisse != null && !isUploadPhotosSuccess && Utils.isConnectedToNetwork(this)) {
                    uploadImage();
                }
*/
                break;


        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.activity_add_rule_nextButton:

                try {
                    fragmentAddLevelManager();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {

            FragmentAddLevelOne.mAdapter.setData(Matisse.obtainResult(data), false, null);

            List<Uri> contentURIs = Matisse.obtainResult(data);
            levelToolbarTextView.setText(String.valueOf(contentURIs.size()));


            dataFromMatisse = data;


        } else if (requestCode == ProvidersApp.REQUEST_CODE_CHOOSE_TAGS_FRG_ADD_LVL_ONE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(ProvidersApp.KEY_CHOOSE_TAGS_FRG_ADD_LVL_ONE);
            FragmentAddLevelOne.chooseTagTextView.setText(title);

        } else if (requestCode == ProvidersApp.REQUEST_CODE_CHOOSE_HOURS_FRG_ADD_LVL_THREE) {


        } else if (requestCode == ProvidersApp.REQUEST_CODE_CHOOSE_MAP_FRG_ADD_LVL_TWO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {


                double lat = extras.getDouble(ProvidersApp.KEY_CHOOSE_MAP_LATITUDE, 0.0);
                double lng = extras.getDouble(ProvidersApp.KEY_CHOOSE_MAP_LONGITUDE, 0.0);
                // Toast.makeText(this, "LOCATION -> "+lat+"\n"+lng, Toast.LENGTH_LONG).show();
                FragmentAddLevelTwo.selectedLatLong = new LatLng(lat, lng);

                String latLongCu = lat + "," + lng;

                try {
                    jsonObjectLocation.put(ProvidersApp.KEY_JSON_OBJECT_LOCATION_MAP, latLongCu);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        } else if (requestCode == ProvidersApp.REQUEST_CODE_CHOOSE_CITY_FRG_ADD_LVL_TWO && resultCode == Activity.RESULT_OK) {

            Bundle extras = data.getExtras();
            if (extras != null) {
                String cityName = extras.getString(ProvidersApp.KEY_CITY_NAME);
                String provinceName = extras.getString(ProvidersApp.KEY_PROVINCE_NAME);
                String name = provinceName + " ، " + cityName;
                FragmentAddLevelTwo.chooseCityTextView.setText(name);


                try {
                    jsonObjectLocation.put(ProvidersApp.KEY_JSON_OBJECT_LOCATION_PROVINCE, provinceName);
                    jsonObjectLocation.put(ProvidersApp.KEY_JSON_OBJECT_LOCATION_CITY, cityName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }

    }

    @Override
    public void viewClickedFrgOne() {
        startActivityForResult(new Intent(AddRule.this, TagChooseActivity.class), ProvidersApp.REQUEST_CODE_CHOOSE_TAGS_FRG_ADD_LVL_ONE);

    }


    @Override
    public void OnAddPicture(String result, VolleyError error) {
        if (result != null && error == null) {
            Log.i(TAG, "OnAddPicture: " + result);
        }
    }
}
