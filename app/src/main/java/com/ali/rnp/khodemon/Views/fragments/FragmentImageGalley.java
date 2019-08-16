package com.ali.rnp.khodemon.Views.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ali.rnp.khodemon.DataModel.PictureUpload;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
/*
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;
*/

public class FragmentImageGalley extends Fragment {

    // private ImageViewTouch imageView;
    private ImageView imageView;

    //TODO ImageView Removed for fix bug

    // private ImageView imageView;
    // private String IMG_ADDRESS = "";
    private ArrayList<PictureUpload> pictureUploadList;
    private int position;


    public FragmentImageGalley() {

    }

    public static FragmentImageGalley newInstance(int position, ArrayList<PictureUpload> pictureUploadList) {

        Bundle args = new Bundle();
        args.putInt(ProvidersApp.KEY_BUNDLE_POSITION, position);
        args.putParcelableArrayList(ProvidersApp.KEY_BUNDLE_IMG_LIST, pictureUploadList);
        FragmentImageGalley fragment = new FragmentImageGalley();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pictureUploadList = getArguments().getParcelableArrayList(ProvidersApp.KEY_BUNDLE_IMG_LIST);
            position = getArguments().getInt(ProvidersApp.KEY_BUNDLE_POSITION);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_galley, container, false);

        imageView = rootView.findViewById(R.id.fragment_image_gallery_imageView_show);

        if (!pictureUploadList.get(position).getPic_address().equals("")) {

            boolean isLarge;
            isLarge = pictureUploadList.get(position).getWidth() >= 1000;
            String IMG_ADDRESS ;

            IMG_ADDRESS = (isLarge) ?
                    pictureUploadList.get(position).getThumb_1000()
                    : pictureUploadList.get(position).getPic_address();


            Picasso.get()
                    .load(pictureUploadList.get(position).getThumb_150())
                    .placeholder(R.drawable.holder_banner)
                    .into(imageView, new Callback() {


                        @Override
                        public void onSuccess() {
                            Picasso.get()
                                    .load(IMG_ADDRESS)
                                    .placeholder(imageView.getDrawable())
                                    .into(imageView);

                            //   imageView.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
//TODO Change it
                        }

                        @Override
                        public void onError(Exception e) {

                        }

                    });

        }


        return rootView;
    }


}
