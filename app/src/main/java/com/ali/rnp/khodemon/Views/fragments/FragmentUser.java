package com.ali.rnp.khodemon.Views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ali.rnp.khodemon.Interface.OnLoginListener;
import com.ali.rnp.khodemon.MyApplication;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.UtilsApp.UtilsApp;
import com.ali.rnp.khodemon.Views.Activities.MainActivity;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentUser extends Fragment implements
        View.OnClickListener,
        OnLoginListener {


    
    private MainActivity myContext;
    private FragmentManager fragmentManager;

    private FragmentLogin fragmentLogin;
    private FragmentRegister fragmentRegister;
    private FragmentUserInfo fragmentUserInfo;

    private CardView cardLogReg;

    private ConstraintLayout rootLayout;

    private OnLoginListener onLoginListener;


    private static final String TAG = "FragmentUser";
    
    

    public FragmentUser() {

    }

    public void setOnLoginListener(OnLoginListener onLoginListener) {
        this.onLoginListener = onLoginListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentLogin = new FragmentLogin();
        fragmentRegister = FragmentRegister.newInstance();
        //fragmentUserInfo = FragmentUserInfo.newInstance("testUserFra");

        fragmentLogin.setOnLoginListener(this);

        fragmentManager = myContext.getSupportFragmentManager();

        replaceFragment(fragmentLogin);


    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);

        cardLogReg= rootView.findViewById(R.id.fragment_user_cardView_logReg);

        rootLayout = rootView.findViewById(R.id.fragment_user_constraintLayout);

        SetupTabLayout(rootView);

        UtilsApp.startAnimationViewsFadeVisible(rootLayout,cardLogReg);

        return rootView;
    }

    private void SetFontToTabLayout(TabLayout tabLayout) {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(MyApplication.getIranSansBold(getContext()));
                }
            }
        }
    }

    private void SetupTabLayout(View rootView) {
        TabLayout tabLayout = rootView.findViewById(R.id.fragment_user_tab_layout);
        SetFontToTabLayout(tabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        replaceFragment(fragmentLogin);
                        UtilsApp.startAnimationViewsFadeVisible(rootLayout,cardLogReg);
                        break;


                    case 1:
                        replaceFragment(fragmentRegister);
                        UtilsApp.startAnimationViewsFadeVisible(rootLayout,cardLogReg);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        myContext = (MainActivity) context;
        

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

        }

    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_user_fragment_container,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onLogin(String userName) {
      //  FragmentUserInfo fragmentUserInfo = FragmentUserInfo.newInstance(userName);
      //  replaceFragment(fragmentUserInfo);

        if (onLoginListener != null)
            onLoginListener.onLogin(userName);



    }





}
