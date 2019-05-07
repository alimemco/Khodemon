package com.ali.rnp.khodemon.Views.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.squareup.picasso.Picasso;


public class FragmentImageGalley extends Fragment {

    private ImageView imageView;
    private String IMG_ADDRESS = "";


    public FragmentImageGalley() {

    }

    public static FragmentImageGalley newInstance(String img) {

        Bundle args = new Bundle();
        args.putString(ProvidersApp.KEY_BUNDLE_IMG,img);
        FragmentImageGalley fragment = new FragmentImageGalley();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            IMG_ADDRESS = getArguments().getString(ProvidersApp.KEY_BUNDLE_IMG);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_galley, container, false);

        imageView = rootView.findViewById(R.id.fragment_image_gallery_imageView_show);

        if (!IMG_ADDRESS.equals("")){
            Picasso.get().load(IMG_ADDRESS).placeholder(R.drawable.holder_banner).into(imageView);

        }

        return rootView;
    }

}
