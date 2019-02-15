package com.ali.rnp.khodemon.Views.fragments;

import androidx.fragment.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;


import android.util.Log;
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

    private MyButton registerBtn;

    private ViewGroup rootLayout;

    private static final String TAG = "FragmentRegister";
    private static final String FIRST_NAME_REGISTER="first_name";
    private static final String LAST_NAME_REGISTER="last_name";
    private static final String USER_NAME_REGISTER="user_name";
    private static final String USER_PASS_REGISTER="user_pass";


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
                nameTxt  , familyTxt  , userTxt  , passwordTxt  , questionTxt, questionLinkTxt,
                nameEdTxt, familyEdTxt, userEdTxt, passwordEdTxt,
                registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.isConnectedToNetwork(getContext())){

                    validateFields();

                }else {
                    Toast.makeText(getContext(), "Error Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return rootView;
    }

    private void validateFields() {

        if (!nameEdTxt.getText().toString().equals("") &&
                !familyEdTxt.getText().toString().equals("") &&
                !userEdTxt.getText().toString().equals("") &&
                !passwordEdTxt.getText().toString().equals("")
                ){

            invisibleTextErrors(passTxtError,userTxtError,nameTxtError);
            sendRegInfoToServer();


        }else {
            if (nameEdTxt.getText().toString().equals("") || familyEdTxt.getText().toString().equals("") ){

                Utils.startAnimationViewsFade(rootLayout,nameTxtError);
            }else {
                nameTxtError.setVisibility(View.INVISIBLE);
            }


            if (userEdTxt.getText().toString().equals("")){
                Utils.startAnimationViewsFade(rootLayout,userTxtError);
            }else {
                userTxtError.setVisibility(View.INVISIBLE);
            }

            if (passwordEdTxt.getText().toString().equals("") ){

                Utils.startAnimationViewsFade(rootLayout,passTxtError);
            }else {
                passTxtError.setVisibility(View.INVISIBLE);
            }
        }



    }

    private void invisibleTextErrors(View... views) {
        for (View view : views){
            view.setVisibility(View.INVISIBLE);
        }
    }


    private void sendRegInfoToServer() {
        JSONObject jsonObjectUserRegInfo = new JSONObject();
        try {
            jsonObjectUserRegInfo.put(FIRST_NAME_REGISTER,nameEdTxt.getText().toString());
            jsonObjectUserRegInfo.put(LAST_NAME_REGISTER,familyEdTxt.getText().toString());
            jsonObjectUserRegInfo.put(USER_NAME_REGISTER,userEdTxt.getText().toString());
            jsonObjectUserRegInfo.put(USER_PASS_REGISTER,passwordEdTxt.getText().toString());

            ApiService apiService = new ApiService(getContext());
            apiService.registerUser(jsonObjectUserRegInfo, new ApiService.OnRegisterCompleted() {
                @Override
                public void onRegisterStatusReceived(int status) {

                    switch (status){
                        case 0:
                           Utils.startAnimationViewsFade(rootLayout,userTxtExist);
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


        rootLayout = rootView.findViewById(R.id.fragment_register_constraintLayout);
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
