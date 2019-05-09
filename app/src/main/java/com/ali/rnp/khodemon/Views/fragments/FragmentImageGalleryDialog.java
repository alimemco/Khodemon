package com.ali.rnp.khodemon.Views.fragments;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ali.rnp.khodemon.Adapter.ImageGalleryAdapter;
import com.ali.rnp.khodemon.DataModel.PictureUpload;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class FragmentImageGalleryDialog extends FragmentDialog {


   // private ArrayList<String> imgAddressList;
    private ArrayList<PictureUpload> pictureUploadList;
    private int position;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;


    public FragmentImageGalleryDialog() {

    }

    public static FragmentImageGalleryDialog newInstance(int position, ArrayList<PictureUpload> pictureUploadList) {

        Bundle args = new Bundle();
        args.putInt(ProvidersApp.KEY_BUNDLE_POSITION,position);
        args.putParcelableArrayList(ProvidersApp.KEY_BUNDLE_IMG_LIST,pictureUploadList);
        FragmentImageGalleryDialog fragment = new FragmentImageGalleryDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);

        if (getArguments() != null){


            pictureUploadList = getArguments().getParcelableArrayList(ProvidersApp.KEY_BUNDLE_IMG_LIST);
            position = getArguments().getInt(ProvidersApp.KEY_BUNDLE_POSITION);


        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            if (dialog.getWindow() != null)
            dialog.getWindow().setLayout(width, height);
        }
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_gallery_dialog, container, false);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {


        mPager = rootView.findViewById(R.id.fragment_image_gallery_dialog_viewPager);

        if (getActivity() != null && pictureUploadList != null){
            mPagerAdapter = new ImageGalleryAdapter(getChildFragmentManager(),pictureUploadList);

            mPager.setAdapter(mPagerAdapter);
            mPager.setCurrentItem(position);
        }


    }

}
