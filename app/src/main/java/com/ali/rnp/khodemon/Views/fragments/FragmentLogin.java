package com.ali.rnp.khodemon.Views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.MyLibrary.MyEditText;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Utils;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;


public class FragmentLogin extends Fragment {

    private OnFragmentInteractionListener mListener;

    private MyTextView usernameTxt;
    private MyTextView passwordTxt;
    private MyTextView questionTxt;

    private MyEditText usernameEdTxt;
    private MyEditText passwordEdTxt;

    private MyButton loginBtn;

    private ConstraintLayout rootlayout;

    public FragmentLogin() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        loginBtn = rootView.findViewById(R.id.fragment_login_button_login);
        usernameTxt = rootView.findViewById(R.id.fragment_login_textView_username);
        passwordTxt = rootView.findViewById(R.id.fragment_login_textView_password);
        questionTxt = rootView.findViewById(R.id.fragment_login_textView_question);
        usernameEdTxt = rootView.findViewById(R.id.fragment_login_editText_username);
        passwordEdTxt = rootView.findViewById(R.id.fragment_login_editText_password);

        rootlayout = rootView.findViewById(R.id.fragment_login_constraintLayout);

        Utils.startAnimationViewsSlide(rootlayout,
                loginBtn,
                usernameTxt, passwordTxt, questionTxt,
                usernameEdTxt, passwordEdTxt);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
