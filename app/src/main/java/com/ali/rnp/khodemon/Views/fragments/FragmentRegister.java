package com.ali.rnp.khodemon.Views.fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.ScrollView;
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

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;


public class FragmentRegister extends Fragment {



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

    private MyTextView lowerCaseTextCheck;
    private MyTextView upperCaseTextCheck;
    private MyTextView lengthTextCheck;
    private MyTextView numberTextCheck;
    private MyTextView charTextCheck;

    private ImageView lowerCaseImageCheck;
    private ImageView upperCaseImageCheck;
    private ImageView lengthImageCheck;
    private ImageView numberImageCheck;
    private ImageView charImageCheck;


    private MyTextView passLevelTxt;

    private MyEditText nameEdTxt;
    private MyEditText familyEdTxt;
    private MyEditText userEdTxt;
    private MyEditText passwordEdTxt;

    private ScrollView scrollView;

    private MyButton registerBtn;

    private ViewGroup rootLayout;
    private MaterialProgressBar progressBarPass;

    private int numberPresent = 0;
    private int upperCasePresent = 0;
    private int lowerCasePresent = 0;
    private int lengthPresent = 0;
    private int specialCharacterPresent = 0;

    private int currentProgress = 0;


    private static final String FIRST_NAME_REGISTER = "first_name";
    private static final String LAST_NAME_REGISTER = "last_name";
    private static final String USER_NAME_REGISTER = "user_name";
    private static final String USER_PASS_REGISTER = "user_pass";


    public FragmentRegister() {

    }

    static FragmentRegister newInstance() {
        return new FragmentRegister();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        initViews(rootView);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        Utils.startAnimationViewsSlide(rootLayout,
                nameTxt, familyTxt, userTxt, passwordTxt, questionTxt, questionLinkTxt,
                nameEdTxt, familyEdTxt, userEdTxt, passwordEdTxt,
                registerBtn);

        passwordSecurityCheck();


        passwordEdTxt.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus || passwordEdTxt.isFocused()) {
                progressBarPass.setVisibility(View.VISIBLE);
                scrollView.fullScroll(View.FOCUS_DOWN);
                passLevelTxt.setVisibility(View.VISIBLE);
                passTxtError.setVisibility(View.INVISIBLE);


            } else {
                scrollView.scrollTo(0, 0);
            }
        });



        registerBtn.setOnClickListener(v -> {


            if (getContext() != null && Utils.isConnectedToNetwork(getContext())) {

                validateFields();

            } else {
                Toast.makeText(getContext(), "Error Connection", Toast.LENGTH_SHORT).show();
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

                if (level == 0) {

                    passLevelTxt.setVisibility(View.INVISIBLE);
                    progressBarPass.setVisibility(View.INVISIBLE);

                    setGonePassViews(lowerCaseImageCheck,
                            upperCaseImageCheck,
                            lengthImageCheck,
                            numberImageCheck,
                            charImageCheck,
                            lowerCaseTextCheck,
                            upperCaseTextCheck,
                            lengthTextCheck,
                            numberTextCheck,
                            charTextCheck

                    );


                } else {

                    setVisibilityPassViews(lowerCaseImageCheck,
                            upperCaseImageCheck,
                            lengthImageCheck,
                            numberImageCheck,
                            charImageCheck,
                            lowerCaseTextCheck,
                            upperCaseTextCheck,
                            lengthTextCheck,
                            numberTextCheck,
                            charTextCheck,
                            progressBarPass,
                            passLevelTxt);
                }

                ProgressBarAnimation anim = new ProgressBarAnimation(progressBarPass, currentProgress, level);
                anim.setDuration(700);
                progressBarPass.startAnimation(anim);

                changeProgressColor(level);

                currentProgress = level;

                setCheckImageView(lowerCasePresent, lowerCaseImageCheck);
                setCheckImageView(upperCasePresent, upperCaseImageCheck);
                setCheckImageView(lengthPresent, lengthImageCheck);
                setCheckImageView(numberPresent, numberImageCheck);
                setCheckImageView(specialCharacterPresent, charImageCheck);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setCheckImageView(int levelPassed, ImageView imageView) {
        if (levelPassed == 20) {
            imageView.setImageResource(R.drawable.ic_check);
        } else {
            imageView.setImageResource(R.drawable.ic_check_box_empty);
        }
    }

    private void changeProgressColor(int level) {

        if (level >= 0 && level <= 20) {
            changeColorAndTextLevel(R.color.red_level, "بسیار ضعیف");

        } else if (level >= 21 && level <= 40) {
            changeColorAndTextLevel(R.color.orange_level, "ضعیف");

        } else if (level >= 41 && level <= 60) {
            changeColorAndTextLevel(R.color.blue_level, "خوب");

        } else if (level >= 61 && level <= 80) {
            changeColorAndTextLevel(R.color.green_light_level, "قوی");

        } else if (level >= 81 && level <= 100) {
            changeColorAndTextLevel(R.color.green_dark_level, "کم نظیر :)");
        }
    }

    private void changeColorAndTextLevel(@ColorRes int colorLevel, CharSequence textLevel) {
        assert getContext() != null ;
        progressBarPass.setSupportProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), colorLevel)));
        passLevelTxt.setText(textLevel);
    }


    private int checkSecurityLevel(String input) {
        String specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";
        char currentCharacter;

        numberPresent = 0;
        upperCasePresent = 0;
        lowerCasePresent = 0;
        lengthPresent = 0;
        specialCharacterPresent = 0;

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

        if (input.length() >= 8) {
            lengthPresent = 20;
        }


        return
                numberPresent + upperCasePresent + lowerCasePresent + specialCharacterPresent + lengthPresent;
    }

    private void validateFields() {

        if (nameEdTxt.getText() != null &&
                familyEdTxt.getText() != null &&
                userEdTxt.getText() != null &&
                passwordEdTxt.getText() != null &&

                !nameEdTxt.getText().toString().equals("") &&
                !familyEdTxt.getText().toString().equals("") &&
                !userEdTxt.getText().toString().equals("") &&
                !passwordEdTxt.getText().toString().equals("")
        ) {

            invisibleTextErrors(passTxtError, userTxtError, nameTxtError);
            sendRegInfoToServer();


        } else {
            if (nameEdTxt.getText() != null &&
                    nameEdTxt.getText().toString().equals("") || familyEdTxt.getText().toString().equals("")) {

                Utils.startAnimationViewsFadeVisible(rootLayout, nameTxtError);
            } else {
                nameTxtError.setVisibility(View.INVISIBLE);
            }


            if (userEdTxt.getText().toString().equals("")) {
                Utils.startAnimationViewsFadeVisible(rootLayout, userTxtError);
            } else {
                userTxtError.setVisibility(View.INVISIBLE);
            }

            if (passwordEdTxt.getText().toString().equals("")) {

                Utils.startAnimationViewsFadeVisible(rootLayout, passTxtError);

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
            assert nameEdTxt.getText() != null &&
                    familyEdTxt.getText() != null &&
                    userEdTxt.getText() != null &&
                    passwordEdTxt.getText() != null ;

                jsonObjectUserRegInfo.put(FIRST_NAME_REGISTER, nameEdTxt.getText().toString());
                jsonObjectUserRegInfo.put(LAST_NAME_REGISTER, familyEdTxt.getText().toString());
                jsonObjectUserRegInfo.put(USER_NAME_REGISTER, userEdTxt.getText().toString());
                jsonObjectUserRegInfo.put(USER_PASS_REGISTER, passwordEdTxt.getText().toString());



            ApiService apiService = new ApiService(getContext());
            apiService.registerUser(jsonObjectUserRegInfo, status -> {

                switch (status) {
                    case 0:
                        Utils.startAnimationViewsFadeVisible(rootLayout, userTxtExist);
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

        passLevelTxt = rootView.findViewById(R.id.fragment_register_txt_passLevel);


        registerBtn = rootView.findViewById(R.id.fragment_register_button);

        nameEdTxt = rootView.findViewById(R.id.fragment_register_editText_name);
        familyEdTxt = rootView.findViewById(R.id.fragment_register_editText_family);
        userEdTxt = rootView.findViewById(R.id.fragment_register_editText_username);
        passwordEdTxt = rootView.findViewById(R.id.fragment_register_editText_password);

        progressBarPass = rootView.findViewById(R.id.fragment_register_pass_progressbar);

        scrollView = rootView.findViewById(R.id.fragment_register_scrollView);

        lowerCaseImageCheck = rootView.findViewById(R.id.fragment_register_check_lowerCase_imageView);
        upperCaseImageCheck = rootView.findViewById(R.id.fragment_register_check_upperCase_imageView);
        lengthImageCheck = rootView.findViewById(R.id.fragment_register_check_lentgh_imageView);
        numberImageCheck = rootView.findViewById(R.id.fragment_register_check_number_imageView);
        charImageCheck = rootView.findViewById(R.id.fragment_register_check_char_imageView);

        lowerCaseTextCheck = rootView.findViewById(R.id.fragment_register_check_lowerCase_txt);
        upperCaseTextCheck = rootView.findViewById(R.id.fragment_register_check_upperCase_txt);
        lengthTextCheck = rootView.findViewById(R.id.fragment_register_check_length_txt);
        numberTextCheck = rootView.findViewById(R.id.fragment_register_check_number_txt);
        charTextCheck = rootView.findViewById(R.id.fragment_register_check_char_txt);

        setGonePassViews(lowerCaseImageCheck,
                upperCaseImageCheck,
                lengthImageCheck,
                numberImageCheck,
                charImageCheck,
                lowerCaseTextCheck,
                upperCaseTextCheck,
                lengthTextCheck,
                numberTextCheck,
                charTextCheck,
                progressBarPass,
                passLevelTxt);


        rootLayout = rootView.findViewById(R.id.fragment_register_constraintLayout);


        passwordEdTxt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                registerBtn.performClick();
                return true;
            }
            return false;
        });
    }

    private void setGonePassViews(View... views) {

        for (View view : views) {
            view.setVisibility(View.GONE);
        }

    }

    private void setVisibilityPassViews(View... views) {

        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);


    }





}
