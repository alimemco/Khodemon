package com.ali.rnp.khodemon.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.fragments.FragmentDialog;

public class DialogError extends FragmentDialog {

    private View view;

    private String errorTxt;


    public static DialogError newInstance(String error) {

        Bundle args = new Bundle();
        args.putString(ProvidersApp.KEY_BUNDLE_ERROR, error);
        DialogError fragment = new DialogError();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.errorTxt = getArguments().getString(ProvidersApp.KEY_BUNDLE_ERROR);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_error, container, false);

        changeDialogSize();
        initViews();

        return view;


    }

    private void changeDialogSize() {
        if (getDialog() != null && getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_background_dialog_fragment);


    }

    private void initViews() {
        MyTextView errorTv = view.findViewById(R.id.dialog_error_txt_error);
        MyButton closeBtn = view.findViewById(R.id.dialog_error_btn_close);

        errorTv.setText(errorTxt);
        closeBtn.setOnClickListener(v -> dismiss());
    }
}
