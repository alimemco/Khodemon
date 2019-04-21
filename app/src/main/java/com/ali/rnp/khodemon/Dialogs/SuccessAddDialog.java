package com.ali.rnp.khodemon.Dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.Activities.MainActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

public class SuccessAddDialog extends DialogFragment {

    public static String TAG = "SuccessAddDialog";
    private MyButton button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.dialog_complete_add, container, false);

        initViews(rootView);
        initToolbar(rootView);

        return rootView;
    }

    private void initToolbar(View rootView) {
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.quantum_ic_cloud_off_vd_theme_24);
        toolbar.setNavigationOnClickListener(view1 -> Toast.makeText(getContext(), "Toast", Toast.LENGTH_SHORT).show());
        toolbar.setTitle("مکان با موفقیت ثبت شد");
    }

    private void initViews(View rootView) {


        button = rootView.findViewById(R.id.dialog_complete_add_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null)
                getActivity().finish();

                dismiss();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            if (dialog.getWindow() != null){
                dialog.getWindow().setLayout(width, height);
            }

        }
    }

    @Override
    public void dismiss() {

        super.dismiss();
        startActivity(new Intent(getContext(), MainActivity.class));
    }
}