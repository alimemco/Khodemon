package com.ali.rnp.khodemon.Views.fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.MyLibrary.MyEditText;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.ProgressBarAnimation;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;


public class FragmentRegister extends Fragment {


    private OnFragmentInteractionListener mListener;
    private MyTextView nameTxt;
    private MyTextView familyTxt;
    private MyTextView userTxt;
    private MyTextView userTxtExist;
    private MyTextView userTxtError;
    private MyTextView nameTxtError;
    private MyTextView passTxtError;
    private MyTextView passwordTxt;
    private MyTextView questionTxt;
    private MyTextView questionLinkTxt;

    private MyEditText nameEdTxt;
    private MyEditText familyEdTxt;
    private MyEditText userEdTxt;
    private MyEditText passwordEdTxt;

    private ScrollView scrollView;

    private MyButton registerBtn;

    private ViewGroup rootLayout;
    private MaterialProgressBar progressBarPass;

    int currentProgress=0;
    int securityLevelLength =0;
    int securityLevelChar =0;
    int securityLevelNumber =0;
    int securityLevelLetter =0;


    private static final String TAG = "FragmentRegister";
    private static final String FIRST_NAME_REGISTER = "first_name";
    private static final String LAST_NAME_REGISTER = "last_name";
    private static final String USER_NAME_REGISTER = "user_name";
    private static final String USER_PASS_REGISTER = "user_pass";


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

        initViews(rootView);

        Utils.startAnimationViewsSlide(rootLayout,
                nameTxt, familyTxt, userTxt, passwordTxt, questionTxt, questionLinkTxt,
                nameEdTxt, familyEdTxt, userEdTxt, passwordEdTxt,
                registerBtn);

        passwordSecurityCheck();


        passwordEdTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus || passwordEdTxt.isFocused()){
                    progressBarPass.setVisibility(View.VISIBLE);
                    scrollView.fullScroll(View.FOCUS_DOWN);
                }else {
                    scrollView.scrollTo(0,0);
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.isConnectedToNetwork(getContext())) {

                    validateFields();

                } else {
                    Toast.makeText(getContext(), "Error Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return rootView;
    }

    private void passwordSecurityCheck() {



        passwordEdTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int level = checkSecurityLevel(s.toString());



                ProgressBarAnimation anim = new ProgressBarAnimation(progressBarPass, currentProgress, level);
                anim.setDuration(700);
                progressBarPass.startAnimation(anim);

                changeProgressColor(level);

                currentProgress = level;

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void changeProgressColor(int level) {
        if (level >=0 && level <= 20){
            progressBarPass.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.red500)));

        }else if (level >=21 && level <= 40){
            progressBarPass.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.orange_400)));

        }else if (level >=41 && level <= 60){
            progressBarPass.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.yellow_400)));

        }else if (level >=61 && level <= 80){
            progressBarPass.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.green_light_400)));

        }else if (level >=81 && level <= 100){
            progressBarPass.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.green_dark_500)));

        }
    }


    private static int checkSecurityLevel(String input) {
        String specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";
        char currentCharacter;
        int numberPresent = 0;
        int upperCasePresent = 0;
        int lowerCasePresent = 0;
        int lengthPresent = 0;
        int specialCharacterPresent = 0;

        for (int i = 0; i < input.length(); i++) {
            currentCharacter = input.charAt(i);
            if (Character.isDigit(currentCharacter)) {
                numberPresent = 20;
            } else if (Character.isUpperCase(currentCharacter)) {
                upperCasePresent = 20;
            } else if (Character.isLowerCase(currentCharacter)) {
                lowerCasePresent = 20;
            } else if (specialChars.contains(String.valueOf(currentCharacter))) {
                specialCharacterPresent = 20;
            }
        }

        if (input.length() >= 8){
            lengthPresent = 20 ;
        }

        return
                numberPresent + upperCasePresent + lowerCasePresent + specialCharacterPresent +lengthPresent;
    }
    private void validateFields() {

        if (!nameEdTxt.getText().toString().equals("") &&
                !familyEdTxt.getText().toString().equals("") &&
                !userEdTxt.getText().toString().equals("") &&
                !passwordEdTxt.getText().toString().equals("")
                ) {

            invisibleTextErrors(passTxtError, userTxtError, nameTxtError);
            sendRegInfoToServer();


        } else {
            if (nameEdTxt.getText().toString().equals("") || familyEdTxt.getText().toString().equals("")) {

                Utils.startAnimationViewsFade(rootLayout, nameTxtError);
            } else {
                nameTxtError.setVisibility(View.INVISIBLE);
            }


            if (userEdTxt.getText().toString().equals("")) {
                Utils.startAnimationViewsFade(rootLayout, userTxtError);
            } else {
                userTxtError.setVisibility(View.INVISIBLE);
            }

            if (passwordEdTxt.getText().toString().equals("")) {

                Utils.startAnimationViewsFade(rootLayout, passTxtError);
            } else {
                passTxtError.setVisibility(View.INVISIBLE);
            }
        }


    }

    private void invisibleTextErrors(View... views) {
        for (View view : views) {
            view.setVisibility(View.INVISIBLE);
        }
    }


    private void sendRegInfoToServer() {
        JSONObject jsonObjectUserRegInfo = new JSONObject();
        try {
            jsonObjectUserRegInfo.put(FIRST_NAME_REGISTER, nameEdTxt.getText().toString());
            jsonObjectUserRegInfo.put(LAST_NAME_REGISTER, familyEdTxt.getText().toString());
            jsonObjectUserRegInfo.put(USER_NAME_REGISTER, userEdTxt.getText().toString());
            jsonObjectUserRegInfo.put(USER_PASS_REGISTER, passwordEdTxt.getText().toString());

            ApiService apiService = new ApiService(getContext());
            apiService.registerUser(jsonObjectUserRegInfo, new ApiService.OnRegisterCompleted() {
                @Override
                public void onRegisterStatusReceived(int status) {

                    switch (status) {
                        case 0:
                            Utils.startAnimationViewsFade(rootLayout, userTxtExist);
                            break;

                        case 1:
                            Toast.makeText(getContext(), "error in Server", Toast.LENGTH_SHORT).show();
                            break;

                        case ApiService.STATUS_REGISTER_ERROR:
                            Toast.makeText(getContext(), "error in lib", Toast.LENGTH_SHORT).show();
                            break;

                        case 29:
                            Toast.makeText(getContext(), "Done :)", Toast.LENGTH_SHORT).show();
                            break;


                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initViews(View rootView) {
        nameTxt = rootView.findViewById(R.id.fragment_register_txt_name);
        familyTxt = rootView.findViewById(R.id.fragment_register_txt_family);
        userTxt = rootView.findViewById(R.id.fragment_register_txt_user);

        passwordTxt = rootView.findViewById(R.id.fragment_register_txt_password);
        questionTxt = rootView.findViewById(R.id.fragment_register_txt_question);
        questionLinkTxt = rootView.findViewById(R.id.fragment_register_txt_questionLink);

        userTxtExist = rootView.findViewById(R.id.fragment_register_txt_user_exist);
        userTxtError = rootView.findViewById(R.id.fragment_register_txt_user_error);

        nameTxtError = rootView.findViewById(R.id.fragment_register_txt_name_error);
        passTxtError = rootView.findViewById(R.id.fragment_register_txt_password_error);


        registerBtn = rootView.findViewById(R.id.fragment_register_button);

        nameEdTxt = rootView.findViewById(R.id.fragment_register_editText_name);
        familyEdTxt = rootView.findViewById(R.id.fragment_register_editText_family);
        userEdTxt = rootView.findViewById(R.id.fragment_register_editText_username);
        passwordEdTxt = rootView.findViewById(R.id.fragment_register_editText_password);

        progressBarPass = rootView.findViewById(R.id.fragment_register_pass_progressbar);

        scrollView = rootView.findViewById(R.id.fragment_register_scrollView);


        rootLayout = rootView.findViewById(R.id.fragment_register_constraintLayout);




        passwordEdTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    registerBtn.performClick();
                    return true;
                }
                return false;
            }
        });
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
