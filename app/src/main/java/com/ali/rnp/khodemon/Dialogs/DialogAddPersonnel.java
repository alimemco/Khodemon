package com.ali.rnp.khodemon.Dialogs;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.Activities.ChoosePersonnelActivity;


public class DialogAddPersonnel extends DialogFragment implements
        View.OnClickListener {

    private MyButton chooseBTN;
    private MyButton addBTN;
    private Context context;

    private int LOCATION_ID;


    public DialogAddPersonnel() {
        // Required empty public constructor
    }

    public static DialogAddPersonnel newInstance(int LOCATION_ID) {

        Bundle args = new Bundle();
        args.putInt(ProvidersApp.KEY_LOCATION_ID,LOCATION_ID);

        DialogAddPersonnel fragment = new DialogAddPersonnel();
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view =  inflater.inflate(R.layout.fragment_dialog_add_personnel, container, false);
       initViews(view);
        return view;
    }

    private void initViews(View view) {
        chooseBTN = view.findViewById(R.id.dialog_add_personnel_choose);
        addBTN = view.findViewById(R.id.dialog_add_personnel_new);

        chooseBTN.setOnClickListener(this);
        addBTN.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.dialog_add_personnel_choose:
                Intent i = new Intent(getActivity(),ChoosePersonnelActivity.class);
                i.putExtra(ProvidersApp.KEY_LOCATION_ID,LOCATION_ID);
                startActivity(i);
                dismiss();



                break;

            case R.id.dialog_add_personnel_new:
                Toast.makeText(context, "New Person", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
