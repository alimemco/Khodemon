package com.ali.rnp.khodemon.Dialogs;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.fragments.FragmentDialog;


public class DialogNumber extends FragmentDialog {


    public DialogNumber() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (getDialog() != null){
            if (getDialog().getWindow()!=null){
                getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_background_dialog_fragment);
            }
        }
        return inflater.inflate(R.layout.fragment_dialog_number, container, false);
    }

}
