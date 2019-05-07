package com.ali.rnp.khodemon.Views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.Activities.DetailActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ScreenSlidePageFragment extends Fragment {

    private ImageView imageView;
    private String IMG_ADDRESS = "";
    private ArrayList<String> imgAddressList;
    private int position;

    public static ScreenSlidePageFragment newInstance(int position, ArrayList<String> imgAddressList) {


        Bundle args = new Bundle();
        args.putInt(ProvidersApp.KEY_BUNDLE_POSITION,position);
        args.putString(ProvidersApp.KEY_BUNDLE_IMG,imgAddressList.get(position));
        args.putStringArrayList(ProvidersApp.KEY_BUNDLE_IMG_LIST,imgAddressList);
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            IMG_ADDRESS = getArguments().getString(ProvidersApp.KEY_BUNDLE_IMG);
            imgAddressList = getArguments().getStringArrayList(ProvidersApp.KEY_BUNDLE_IMG_LIST);
            position = getArguments().getInt(ProvidersApp.KEY_BUNDLE_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_screen_slide_page,container,false);
        imageView = rootView.findViewById(R.id.fragment_screen_slider_page_imageView);

        if (!IMG_ADDRESS.equals("")){
            Picasso.get().load(IMG_ADDRESS).placeholder(R.drawable.holder_banner).into(imageView);

        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentImageGalleryDialog dialog = FragmentImageGalleryDialog.newInstance(position,imgAddressList);
                if (getActivity() != null){
                        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                        dialog.show(ft,"full");

                }


            }
        });

        return rootView;
    }
}
