package com.ali.rnp.khodemon.BottomSheet;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.Activities.ChoosePersonnelActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class BottomSheetAddPersonnel extends BottomSheetDialogFragment implements
        View.OnClickListener {


    private MyTextView addTV;
    private MyTextView chooseTV;
    private int LOCATION_ID;

    public BottomSheetAddPersonnel() {
    }

    public static BottomSheetAddPersonnel newInstance(int LOCATION_ID) {

        Bundle args = new Bundle();
        args.putInt(ProvidersApp.KEY_LOCATION_ID,LOCATION_ID);
        BottomSheetAddPersonnel fragment = new BottomSheetAddPersonnel();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            LOCATION_ID = getArguments().getInt(ProvidersApp.KEY_LOCATION_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bottom_sheet_add_personnel, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        addTV = view.findViewById(R.id.bottom_sheet_add_personnel_addTV);
        chooseTV = view.findViewById(R.id.bottom_sheet_add_personnel_chooseTV);

        addTV.setOnClickListener(this);
        chooseTV.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bottom_sheet_add_personnel_addTV:
                Toast.makeText(getActivity(), "ADD", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bottom_sheet_add_personnel_chooseTV:
                Intent i = new Intent(getActivity(), ChoosePersonnelActivity.class);
                i.putExtra(ProvidersApp.KEY_LOCATION_ID,LOCATION_ID);
                if (getActivity() != null)
                    getActivity().startActivityForResult(i,ProvidersApp.REQUEST_CODE_CHOOSE_PERSONNEL);
                dismiss();
                break;
        }
    }
}
