package com.ali.rnp.khodemon.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.Activities.MainActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;



public class DialogCompleteAdd extends DialogFragment {

    private MyButton button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_complete_add, container,
                false);

        button = rootView.findViewById(R.id.dialog_complete_add_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if (getDialog() != null){
            if (getDialog().getWindow()!=null){
                getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_background_dialog_fragment);

            }

        }


        return rootView;
    }





    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        startActivity(new Intent(getContext(), MainActivity.class));
        if (getActivity() != null ){
            getActivity().finish();
        }
        super.onDismiss(dialog);


    }


}