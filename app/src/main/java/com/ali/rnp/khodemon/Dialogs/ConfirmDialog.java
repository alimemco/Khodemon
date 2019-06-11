package com.ali.rnp.khodemon.Dialogs;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.UtilsApp.UtilsApp;
import com.ali.rnp.khodemon.Views.fragments.FragmentDialog;

import java.io.Serializable;
import de.hdodenhof.circleimageview.CircleImageView;

public class ConfirmDialog extends FragmentDialog {

    private String questionStr;
    private LocationPeople locationPeople;
    private OnClickButtonDialog positiveListener;
    private OnClickButtonDialog negativeListener;

    public static int BUTTON_POSITIVE = -1;
    public static int BUTTON_NEGATIVE = -2;

    public ConfirmDialog() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            questionStr = getArguments().getString(ProvidersApp.KEY_BUNDLE_QUESTION);
            locationPeople = getArguments().getParcelable(ProvidersApp.KEY_LOCATION_PEOPLE);
            positiveListener = (OnClickButtonDialog) getArguments().getSerializable(ProvidersApp.KEY_BUNDLE_POSITIVE_BUTTON);
            negativeListener = (OnClickButtonDialog) getArguments().getSerializable(ProvidersApp.KEY_BUNDLE_NEGATIVE_BUTTON);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm_dialog, container, false);
        initViews(view);

        if (getDialog() != null && getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_background_dialog_fragment);

        return view;
    }

    private void initViews(View view) {
        MyTextView questionTv = view.findViewById(R.id.fragment_confirm_question);
        MyButton answerOkTv = view.findViewById(R.id.fragment_confirm_answer_ok);
        MyButton answerCancelTv = view.findViewById(R.id.fragment_confirm_answer_cancel);
        CircleImageView imageView = view.findViewById(R.id.fragment_confirm_dialog_image);

        questionTv.setText(questionStr);

        if (locationPeople != null)
        UtilsApp.getImage(locationPeople, imageView);

        answerOkTv.setOnClickListener(v -> {
            if (positiveListener != null){
                positiveListener.onClickDialog(getDialog(),BUTTON_POSITIVE);
            }
        });

        answerCancelTv.setOnClickListener(v -> {
            if (negativeListener != null){
                negativeListener.onClickDialog(getDialog(),BUTTON_NEGATIVE);
            }
        });
    }



    public static class Builder {

        private String questionStr;
        private LocationPeople locationPeople;
        private OnClickButtonDialog positiveListener;
        private OnClickButtonDialog negativeListener;


        public Builder setQuestion(String question) {
            this.questionStr = question;
            return this;
        }

        public Builder setLocationPeople(LocationPeople locationPeople) {
            this.locationPeople = locationPeople;
            return this;
        }

        public Builder setPositiveListener(OnClickButtonDialog listener) {
            this.positiveListener = listener;
            return this;
        }

        public Builder setNegativeButton(OnClickButtonDialog listener) {
            this.negativeListener = listener;
            return this;
        }

        public ConfirmDialog create() {

            Bundle args = new Bundle();
            args.putString(ProvidersApp.KEY_BUNDLE_QUESTION, this.questionStr);
            args.putParcelable(ProvidersApp.KEY_LOCATION_PEOPLE, this.locationPeople);
            args.putSerializable(ProvidersApp.KEY_BUNDLE_POSITIVE_BUTTON, this.positiveListener);
            args.putSerializable(ProvidersApp.KEY_BUNDLE_NEGATIVE_BUTTON, this.negativeListener);
            ConfirmDialog fragment = new ConfirmDialog();
            fragment.setArguments(args);
            return fragment;

        }

    }

    public interface OnClickButtonDialog extends Serializable {
        void onClickDialog(Dialog dialog,int which);
    }
}
