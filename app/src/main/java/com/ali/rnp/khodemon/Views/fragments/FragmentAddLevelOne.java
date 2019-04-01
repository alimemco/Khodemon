package com.ali.rnp.khodemon.Views.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.UriAdapter;
import com.ali.rnp.khodemon.GifSizeFilter;
import com.ali.rnp.khodemon.Glide4Engine;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnSelectedListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FragmentAddLevelOne extends Fragment implements
        UriAdapter.OnItemChooseRecyclerClicked {

    private Context context;
    private RecyclerView recyclerViewImages;
    private CardView chooseTagCardView;
    public static MyTextView chooseTagTextView;
    public static UriAdapter mAdapter;
    private static final int REQUEST_CODE_CHOOSE = 23;
    public static final int REQUEST_CODE_CHOOSE_TAG = 414;
    public static final int PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 900;
    private Spinner spinnerOwner;
    private static final String TAG = "FragmentAddLevelOne";

    private OnViewClickListener onViewClickListener;

    private String[] chooseOwnerModel = {"انتخاب کنید", "مالک", "فروشنده", "زیر مجموعه", "هیچکدام"};


    public FragmentAddLevelOne() {

    }

    @SuppressLint("ValidFragment")
    public FragmentAddLevelOne(OnViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_add_level_one, container, false);
        initViews(rootView);
        setupSpinner();
        return rootView;
    }

    private void setupSpinner() {


        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, chooseOwnerModel);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOwner.setAdapter(adapter);


        spinnerOwner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
               // Toast.makeText(context, "U Choose : " + chooseOwnerModel[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


    }

    private void initViews(View rootView) {
        recyclerViewImages = rootView.findViewById(R.id.fragment_add_one_recycler_view);
        spinnerOwner = rootView.findViewById(R.id.fragment_add_level_one_spinner);


        chooseTagCardView = rootView.findViewById(R.id.add_level_one_cardView_tag);
        chooseTagTextView = rootView.findViewById(R.id.fragment_add_level_one_MyTextView_chooseTag);


        chooseTagCardView.setOnClickListener(v -> {
            onViewClickListener.viewClickedFrgOne();
        });

        recyclerViewImages.setLayoutManager(new GridLayoutManager(context, 3));
        //recyclerViewImages.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        recyclerViewImages.setAdapter(mAdapter = new UriAdapter(context));


        Uri path = Uri.parse("android.resource://com.ali.rnp.khodemon/" + R.drawable.add_photo);
        List<Uri> uriPath = new ArrayList<>();
        uriPath.add(path);
        mAdapter.setData(uriPath, true, this);
    }

    private void injectPhoto() {


        Matisse.from(getActivity())
                .choose(MimeType.ofImage(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.ali.rnp.khodemon.fileprovider"))
                .maxSelectable(10)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(
                        getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
//                                            .imageEngine(new GlideEngine())  // for glide-V3
                .imageEngine(new Glide4Engine())    // for glide-V4
                .setOnSelectedListener(new OnSelectedListener() {
                    @Override
                    public void onSelected(
                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e(TAG, "onSelected: pathList=" + pathList);

                    }
                })
                /* .originalEnable(true)
                 .maxOriginalSize(10)
                 .autoHideToolbarOnSingleTap(true)
                 .setOnCheckedListener(new OnCheckedListener() {
                     @Override
                     public void onCheck(boolean isChecked) {
                         // DO SOMETHING IMMEDIATELY HERE
                         Log.e(TAG, "onCheck: isChecked=" + isChecked);
                     }
                 })*/
                .forResult(REQUEST_CODE_CHOOSE);

    }

    private void getNeedPermissions() {
        if (getContext() != null) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED ||

                    ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED ||

                    ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA},
                            PERMISSIONS_REQUEST_EXTERNAL_STORAGE);
                }

            } else {
                // Permission has already been granted
                injectPhoto();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_REQUEST_EXTERNAL_STORAGE: {

                if (grantResults.length > 2
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                ) {
                    // permission was granted, yay!
                    injectPhoto();

                } else {
                    // permission denied
                    Toast.makeText(getContext(), "Permissions is Denied !", Toast.LENGTH_SHORT).show();
                }
                return;
            }


        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void onRecyclerClicked() {
        //Choose photo from gallery
        //   injectPhoto();

        getNeedPermissions();

    }

    public interface OnViewClickListener {
        void viewClickedFrgOne();
    }


}
