package com.ali.rnp.khodemon.Views.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.Activities.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FragmentBottomSheetCall extends BottomSheetDialogFragment implements
        View.OnClickListener {


    private String number;
    private MyTextView numberTV;
    private ImageView callIV;
    private ImageView messageIV;
    public FragmentBottomSheetCall() {
        // Required empty public constructor
    }

    public static FragmentBottomSheetCall newInstance(String number) {

        Bundle args = new Bundle();
        args.putString(ProvidersApp.KEY_PHONE,number);
        FragmentBottomSheetCall fragment = new FragmentBottomSheetCall();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            number = getArguments().getString(ProvidersApp.KEY_PHONE);
        }
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bottom_sheet_call,container,false);
        initViews(view);

        return view;
    }

    private void initViews(View view) {
        numberTV = view.findViewById(R.id.fragment_bottom_sheet_call_numberTV);
        callIV = view.findViewById(R.id.fragment_bottom_sheet_call_callIV);
        messageIV = view.findViewById(R.id.fragment_bottom_sheet_call_messageIV);

        callIV.setOnClickListener(this);
        messageIV.setOnClickListener(this);

        if (number != null  ) {
            if (!number.equals("")) {
                numberTV.setText(number);
            } else {
                numberTV.setText("هیچ شماره ای درج نشده!");
                callIV.setVisibility(View.INVISIBLE);
                messageIV.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_bottom_sheet_call_callIV:
                sendCall();
                break;

            case R.id.fragment_bottom_sheet_call_messageIV:

                sendSms();
                break;
        }
    }

    private void sendCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+number));
        startActivity(intent);
    }



    private void sendSms() {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.putExtra("address"  , number);



        try {
            startActivity(smsIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(),
                    "SMS failed.", Toast.LENGTH_SHORT).show();
        }
    }


}
