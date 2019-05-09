package com.ali.rnp.khodemon.Views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ali.rnp.khodemon.DataModel.PictureUpload;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ScreenSlidePageFragment extends Fragment {

    private ImageView imageView;
    //private String IMG_ADDRESS = "";
    //private ArrayList<String> imgAddressList;
    private ArrayList<PictureUpload> pictureUploadList;
    private int position;

    public static ScreenSlidePageFragment newInstance(int position, ArrayList<PictureUpload> pictureUploadList) {


        Bundle args = new Bundle();
        args.putInt(ProvidersApp.KEY_BUNDLE_POSITION, position);
        //args.putString(ProvidersApp.KEY_BUNDLE_IMG,pictureUploadList.get(position).get);
        args.putParcelableArrayList(ProvidersApp.KEY_BUNDLE_IMG_LIST, pictureUploadList);
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // IMG_ADDRESS = getArguments().getString(ProvidersApp.KEY_BUNDLE_IMG);
            //IMG_ADDRESS = getArguments().getString(ProvidersApp.KEY_BUNDLE_IMG);
            position = getArguments().getInt(ProvidersApp.KEY_BUNDLE_POSITION);
            pictureUploadList = getArguments().getParcelableArrayList(ProvidersApp.KEY_BUNDLE_IMG_LIST);

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        imageView = rootView.findViewById(R.id.fragment_screen_slider_page_imageView);

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
                        }

                        @Override
                        public void onError(Exception e) {

                        }

                    });

        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentImageGalleryDialog dialog = FragmentImageGalleryDialog.newInstance(position, pictureUploadList);
                if (getActivity() != null) {
                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                    dialog.show(ft, "full");

                }


            }
        });

        return rootView;
    }
}
