package com.ali.rnp.khodemon.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.HoursAdapter;
import com.ali.rnp.khodemon.DataModel.HourDays;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.fragments.FragmentDialog;

import java.util.List;

import androidx.annotation.NonNull;


public class DialogChooseOtherDay extends FragmentDialog {

    private CheckBox checkBox;
    private Context context;

    private OnSelectCheckBoxDialog onSelectCheckBoxDialog;



    public DialogChooseOtherDay() {
        // Required empty public constructor
    }


    public void setOnSelectCheckBoxDialog(OnSelectCheckBoxDialog onSelectCheckBoxDialog){
        this.onSelectCheckBoxDialog = onSelectCheckBoxDialog;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_choose_other_day, container, false);

        initView(rootView);

        if (getDialog() != null){
            if (getDialog().getWindow()!=null){
                getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_background_dialog_fragment);
            }
        }
        return rootView;
    }

    private void initView(View rootView) {
        checkBox = rootView.findViewById(R.id.dialog_choose_other_day_checkBox);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (onSelectCheckBoxDialog != null){
                    onSelectCheckBoxDialog.OnSelected();
                }
                dismiss();
            }
        });



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public interface OnSelectCheckBoxDialog{
        void OnSelected();
    }
}
