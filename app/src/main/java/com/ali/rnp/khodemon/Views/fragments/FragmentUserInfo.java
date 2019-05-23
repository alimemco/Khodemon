package com.ali.rnp.khodemon.Views.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.DataModel.User;
import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.SharedPrefManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class FragmentUserInfo extends Fragment implements View.OnClickListener {

    private MyTextView userNameTV;
    private MyButton logoutBTN;

    private SharedPrefManager sharedPrefManager;
    private OnLogOut onLogOut;

    private String userName;


    public FragmentUserInfo() {
        // Required empty public constructor
    }

    public static FragmentUserInfo newInstance(String userName) {

        Bundle args = new Bundle();
        args.putString(ProvidersApp.KEY_USERNAME,userName);
        FragmentUserInfo fragment = new FragmentUserInfo();
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnLogOutListener(OnLogOut onLogOut){
        this.onLogOut = onLogOut;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            userName = getArguments().getString(ProvidersApp.KEY_USERNAME);
        }

        setUserToSharedPref();


    }

    private void setUserToSharedPref() {
        if (getContext() != null){
            User user = new User();
            user.setUserName(userName);
            sharedPrefManager = new SharedPrefManager(getContext());
            sharedPrefManager.addUser(user);
        }
     }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        userNameTV = view.findViewById(R.id.fragment_user_info_userName);
        logoutBTN = view.findViewById(R.id.fragment_user_info_logOut);

        userNameTV.setText(userName);

        logoutBTN.setOnClickListener(this);
    }

    public interface OnLogOut{
        void OnLoginOut();
    }

    @Override
    public void onClick(View v) {

        sharedPrefManager.logOutUser();
        if (onLogOut != null){
            onLogOut.OnLoginOut();
        }


    }
}
