package com.ali.rnp.khodemon.Views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.R;


public class FragmentAddLevelTwo extends Fragment {




    public FragmentAddLevelTwo() {

    }

    public static FragmentAddLevelTwo newInstance(String param1, String param2) {
        return new FragmentAddLevelTwo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_level_two, container, false);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }




}
