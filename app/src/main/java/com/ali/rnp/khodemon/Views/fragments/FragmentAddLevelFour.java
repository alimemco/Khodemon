package com.ali.rnp.khodemon.Views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.MyLibrary.MyEditText;
import com.ali.rnp.khodemon.R;


public class FragmentAddLevelFour extends Fragment {

    private Context context;
    public static MyEditText telegramEditText;
    public static MyEditText instagramEditText;
    public static MyEditText siteEditText;
    public static MyEditText descriptionEditText;

    public FragmentAddLevelFour() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_level_four, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {

        telegramEditText = rootView.findViewById(R.id.fragment_add_level_four_EditText_telegram);
        instagramEditText = rootView.findViewById(R.id.fragment_add_level_four_EditText_instagram);
        siteEditText = rootView.findViewById(R.id.fragment_add_level_four_EditText_site);
        descriptionEditText = rootView.findViewById(R.id.fragment_add_level_four_EditText_description);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       this.context = context;
    }


}
