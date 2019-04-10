package com.ali.rnp.khodemon.Views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.R;


public class FragmentAddLevelFour extends Fragment {

    private Context context;

    public FragmentAddLevelFour() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_level_four, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       this.context = context;
    }


}
