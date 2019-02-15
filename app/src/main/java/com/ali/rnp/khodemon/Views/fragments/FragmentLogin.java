package com.ali.rnp.khodemon.Views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.MyLibrary.MyEditText;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Utils;

import org.json.JSONException;
import org.json.JSONObject;

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

    private static final String USER_NAME_LOGIN = "user_name";
    private static final String USER_PASS_LOGIN = "user_pass";

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

        initViews(rootView);

        Utils.startAnimationViewsSlide(rootlayout,
                loginBtn,
                usernameTxt, passwordTxt, questionTxt,
                usernameEdTxt, passwordEdTxt);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.isConnectedToNetwork(getContext())) {
                    validateFieldsLogin();
                } else {
                    Toast.makeText(getContext(), "connectionError", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return rootView;
    }

    private void validateFieldsLogin() {
        if (!usernameEdTxt.getText().toString().equals("") &&
                !passwordEdTxt.getText().toString().equals("")) {


            JSONObject jsonObjectLogin = new JSONObject();
            try {
                jsonObjectLogin.put(USER_NAME_LOGIN, usernameEdTxt.getText().toString());
                jsonObjectLogin.put(USER_PASS_LOGIN, passwordEdTxt.getText().toString());

                ApiService apiService = new ApiService(getContext());

                apiService.loginUser(jsonObjectLogin, new ApiService.OnLoginCompleted() {
                    @Override
                    public void onLoginStatusReceived(int status) {
                        switch (status) {
                            case ApiService.STATUS_Login_ERROR:
                                Toast.makeText(getContext(),"Error Login",Toast.LENGTH_SHORT).show();
                                break;

                            case 1:
                                //user Not Found.
                                Toast.makeText(getContext(),"User Not Found",Toast.LENGTH_SHORT).show();

                                break;


                            case 2:
                                Toast.makeText(getContext(),"wrong pass",Toast.LENGTH_SHORT).show();

                                //wrong pass
                                break;

                            case 29:
                                Toast.makeText(getContext(),"success",Toast.LENGTH_SHORT).show();

                                //success
                                break;
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

        rootlayout = rootView.findViewById(R.id.fragment_login_constraintLayout);

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
