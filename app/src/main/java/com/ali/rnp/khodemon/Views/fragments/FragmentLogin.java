package com.ali.rnp.khodemon.Views.fragments;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.Interface.OnLoginListener;
import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.MyLibrary.MyEditText;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.UtilsApp.UtilsApp;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;


public class FragmentLogin extends Fragment {


    private MyTextView usernameTxt;
    private MyTextView passwordTxt;
    private MyTextView questionTxt;

    private MyEditText usernameEdTxt;
    private MyEditText passwordEdTxt;
    private ImageView eyeImageView;
    private MaterialProgressBar progressBarLogin;

    private MyButton loginBtn;
    private MaterialProgressBar materialProgressBar;

    private boolean visiblePass = false;


    private ConstraintLayout rootLayout;

    private static final String USER_NAME_LOGIN = "user_name";
    private static final String USER_PASS_LOGIN = "user_pass";

    private OnLoginListener onLoginListener;

    public FragmentLogin() {

    }



    public void setOnLoginListener(OnLoginListener onLoginListener){
        this.onLoginListener = onLoginListener;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        initViews(rootView);

        UtilsApp.startAnimationViewsSlide(rootLayout,
                loginBtn,
                usernameTxt, passwordTxt, questionTxt,
                usernameEdTxt, passwordEdTxt,
                eyeImageView);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getContext() != null;
                if (UtilsApp.isConnectedToNetwork(getContext())) {

                    validateFieldsLogin();
                } else {
                    Toast.makeText(getContext(), "connectionError", Toast.LENGTH_SHORT).show();
                }

            }
        });


        eyeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (visiblePass){

                    passwordEdTxt.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);

                    eyeImageView.setImageResource(R.drawable.ic_eye_visible_dark);

                }else {
                    passwordEdTxt.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    eyeImageView.setImageResource(R.drawable.ic_eye_visible_light);

                }
                visiblePass = !visiblePass;

            }
        });

        return rootView;
    }

    private void validateFieldsLogin() {

        if (!usernameEdTxt.getText().toString().equals("") &&
                !passwordEdTxt.getText().toString().equals("")) {
            progressBarLogin.setVisibility(View.VISIBLE);
            loginBtn.setText("");

            materialProgressBar.setVisibility(View.VISIBLE);

            JSONObject jsonObjectLogin = new JSONObject();
            try {
                jsonObjectLogin.put(USER_NAME_LOGIN, usernameEdTxt.getText().toString());
                jsonObjectLogin.put(USER_PASS_LOGIN, passwordEdTxt.getText().toString());

                ApiService apiService = new ApiService(getContext());

                apiService.loginUser(jsonObjectLogin, new ApiService.OnLoginCompleted() {
                    @Override
                    public void onLoginStatusReceived(boolean isSuccess, int code, String username, String error) {
                        materialProgressBar.setVisibility(View.INVISIBLE);
                        loginBtn.setText(getResources().getText(R.string.login));
                        progressBarLogin.setVisibility(View.INVISIBLE);

                        if (isSuccess && code == 29 && onLoginListener != null){

                                onLoginListener.onLogin(username);
                                Toast.makeText(getContext(),"success",Toast.LENGTH_SHORT).show();

                        }else {
                            String status = "unknown";
                            switch (code) {
                                case ApiService.STATUS_Login_ERROR:
                                    status = "Error Login";
                                    break;

                                case 1:
                                    status = "User Not Found";
                                    break;

                                case 2:
                                    status = "wrong pass";
                                    break;
                                    }

                            Toast.makeText(getContext(), status, Toast.LENGTH_LONG).show();
                        }



                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            if (usernameEdTxt.getText().toString().equals("")) {
                usernameEdTxt.setHint("errorUser");
            }
            if (passwordEdTxt.getText().toString().equals("")) {
                passwordEdTxt.setHint("errorPass");
            }
        }

    }

    private void initViews(View rootView) {
        loginBtn = rootView.findViewById(R.id.fragment_login_button_login);
        usernameTxt = rootView.findViewById(R.id.fragment_login_textView_username);
        passwordTxt = rootView.findViewById(R.id.fragment_login_textView_password);
        questionTxt = rootView.findViewById(R.id.fragment_login_textView_question);
        usernameEdTxt = rootView.findViewById(R.id.fragment_login_editText_username);
        passwordEdTxt = rootView.findViewById(R.id.fragment_login_editText_password);
        eyeImageView = rootView.findViewById(R.id.fragment_login_imageView_eye);

        materialProgressBar = rootView.findViewById(R.id.fragment_login_materialProgressBar);
        progressBarLogin = rootView.findViewById(R.id.fragment_login_progressLogin);

        progressBarLogin.setVisibility(View.INVISIBLE);
        progressBarLogin.bringToFront();

        rootLayout = rootView.findViewById(R.id.fragment_login_constraintLayout);



    }


}
