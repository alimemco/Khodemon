package com.ali.rnp.khodemon.Views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ali.rnp.khodemon.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ScreenSlidePageFragment extends Fragment {

    ImageView imageView;
    private int PAGE_NUMBER = -1;
    private String IMG_ADDRESS = "";

    public static ScreenSlidePageFragment newInstance(int position,String img) {

        Bundle args = new Bundle();
        args.putInt("pos",position);
        args.putString("img",img);
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            PAGE_NUMBER = getArguments().getInt("pos");
            IMG_ADDRESS = getArguments().getString("img");
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

        return rootView;
    }
}
