package com.ali.rnp.khodemon.Views.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FragmentBottomSheetCall extends BottomSheetDialogFragment {


    public FragmentBottomSheetCall() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_bottom_sheet_call, container, false);
    }

}
