package com.ali.rnp.khodemon.Views.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.MyLibrary.MyEditText;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.Activities.HoursChooseActivity;

import java.text.DecimalFormat;


public class FragmentAddLevelThree extends Fragment
implements TextWatcher, View.OnClickListener {

    private MyTextView dimenTypeTextView;
    public static MyEditText dimenEditText;
    public static MyEditText phoneEditText;
    public static MyEditText sinceEditText;
    private CardView chooseHoursCardView;
    private MyTextView chooseHoursTextView;

    private Context context;


    public FragmentAddLevelThree() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_level_three, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        dimenTypeTextView = rootView.findViewById(R.id.fragment_add_level_three_dimenType_TextView);
        dimenEditText = rootView.findViewById(R.id.fragment_add_level_three_demens_EditText);
        phoneEditText = rootView.findViewById(R.id.fragment_add_level_three_EditText_phone);
        sinceEditText = rootView.findViewById(R.id.fragment_add_level_three_EditText_since);
        chooseHoursCardView = rootView.findViewById(R.id.fragment_add_level_three_cardView_chooseHours);
        chooseHoursTextView = rootView.findViewById(R.id.fragment_add_level_three_MyTextView_chooseHours);

        chooseHoursCardView.setOnClickListener(this);
        dimenEditText.addTextChangedListener(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (!s.toString().equals("") && s.toString().length() < 9){
            dimenTypeTextView.setVisibility(View.VISIBLE);
            String dimenType = splitDigits(Integer.parseInt(s.toString()))+"  "+"متر مربع";
            dimenTypeTextView.setText(dimenType);
        }else {
            dimenTypeTextView.setVisibility(View.INVISIBLE);
        }


    }

    private static String splitDigits(int number) {
        return new DecimalFormat("###,###,###").format(number);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_add_level_three_cardView_chooseHours:
                if (getActivity() != null){
                    getActivity().startActivityForResult(new Intent(context, HoursChooseActivity.class), ProvidersApp.REQUEST_CODE_CHOOSE_HOURS_FRG_ADD_LVL_THREE);
                }
                break;
        }
    }
}
