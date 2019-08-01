package com.ali.rnp.khodemon.BottomSheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ali.rnp.khodemon.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SortBottomSheet extends BottomSheetDialogFragment {

    public static SortBottomSheet newInstance() {

        Bundle args = new Bundle();

        SortBottomSheet fragment = new SortBottomSheet();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.btm_sht_sort, container, false);
        initViews();
        return view;
    }

    private void initViews() {

    }
}
