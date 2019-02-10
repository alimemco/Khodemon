package com.ali.rnp.khodemon.Views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.MyLibrary.MyEditText;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Utils;


public class FragmentRegister extends Fragment {


    private OnFragmentInteractionListener mListener;
    private MyTextView nameTxt;
    private MyTextView familyTxt;
    private MyTextView userTxt;
    private MyTextView passwordTxt;
    private MyTextView questionTxt;
    private MyTextView questionLinkTxt;

    private MyEditText nameEdTxt;
    private MyEditText familyEdTxt;
    private MyEditText userEdTxt;
    private MyEditText passwordEdTxt;

    private MyButton registerBtn;

    private ViewGroup rootLayout;


    public FragmentRegister() {

    }

    public static FragmentRegister newInstance() {
        FragmentRegister fragment = new FragmentRegister();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        nameTxt = rootView.findViewById(R.id.fragment_register_txt_name);
        familyTxt = rootView.findViewById(R.id.fragment_register_txt_family);
        userTxt = rootView.findViewById(R.id.fragment_register_txt_user);
        passwordTxt = rootView.findViewById(R.id.fragment_register_txt_password);
        questionTxt = rootView.findViewById(R.id.fragment_register_txt_question);
        questionLinkTxt = rootView.findViewById(R.id.fragment_register_txt_questionLink);

        registerBtn = rootView.findViewById(R.id.fragment_register_button);

        nameEdTxt = rootView.findViewById(R.id.fragment_register_editText_name);
        familyEdTxt = rootView.findViewById(R.id.fragment_register_editText_family);
        userEdTxt = rootView.findViewById(R.id.fragment_register_editText_username);
        passwordEdTxt = rootView.findViewById(R.id.fragment_register_editText_password);

        rootLayout = rootView.findViewById(R.id.fragment_register_constraintLayout);



        Utils.startAnimationViewsSlide(rootLayout,
                nameTxt  , familyTxt  , userTxt  , passwordTxt  , questionTxt, questionLinkTxt,
                nameEdTxt, familyEdTxt, userEdTxt, passwordEdTxt,
                registerBtn);

        return rootView;
    }





    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
