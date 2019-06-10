package com.ali.rnp.khodemon.Dialogs;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.Activities.ChoosePersonnelActivity;
import com.ali.rnp.khodemon.Views.fragments.FragmentDialog;

public class ConfirmDialog extends FragmentDialog {

    private MyTextView questionTv, answerOkTv , answerCancelTv ;
    private String questionStr;

    public ConfirmDialog() {
    }

    public static ConfirmDialog newInstance(String question) {

        Bundle args = new Bundle();
        args.putString(ProvidersApp.KEY_BUNDLE_QUESTION,question);
        ConfirmDialog fragment = new ConfirmDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
        questionStr = getArguments().getString(ProvidersApp.KEY_BUNDLE_QUESTION);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm_dialog, container, false);
        initViews(view);

        if (getDialog() != null && getDialog().getWindow()!=null)
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_background_dialog_fragment);

        return view;
    }

    private void initViews(View view) {
        questionTv = view.findViewById(R.id.fragment_confirm_question);
        answerOkTv = view.findViewById(R.id.fragment_confirm_answer_ok);
        answerCancelTv = view.findViewById(R.id.fragment_confirm_answer_cancel);

        questionTv.setText(questionStr);
    }
/*
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_confirm_answer_ok:
                ApiService apiService = new ApiService(getContext());
                apiService.addPersonnel(LOCATION_ID, PEOPLE_ID, new ApiService.OnAddPersonnel() {
                    @Override
                    public void onAdded(int successCode, String error) {

                        if (successCode == ProvidersApp.STATUS_CODE_SUCCESSFULLY){
                            Toast.makeText(getContext(), "با موفقیت اضافه شد", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent();
                            intent.putExtra(ProvidersApp.KEY_LOCATION_ID,LOCATION_ID);
                            setResult(Activity.RESULT_OK,intent);
                            finish();
                        }else  if (error != null){
                            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                        }

                    }
                });
                break;

            case R.id.fragment_confirm_answer_cancel:
                dismiss();
                break;
        }
    }

    */
}
