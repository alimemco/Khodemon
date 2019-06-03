package com.ali.rnp.khodemon.Views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.Activities.AddRule;

public class FragmentAdd extends Fragment implements View.OnClickListener {


    private Context context;
    private ConstraintLayout locationConstraintLayout;
    private ConstraintLayout peopleConstraintLayout;


    public FragmentAdd() {

    }


    public static FragmentAdd newInstance(String groupName) {
        return new FragmentAdd();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        if (getArguments() != null) {
            groupName = getArguments().getString(ARG_PARAM1);
        }
*/


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add, container, false);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {
        locationConstraintLayout = rootView.findViewById(R.id.fragment_add_location_constraintLayout);
        peopleConstraintLayout = rootView.findViewById(R.id.fragment_add_people_constraintLayout);

        locationConstraintLayout.setOnClickListener(this);
        peopleConstraintLayout.setOnClickListener(this);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.fragment_add_location_constraintLayout:

                Intent intentLocation = new Intent(context, AddRule.class);
                intentLocation.putExtra(ProvidersApp.GROUP_NAME, ProvidersApp.GROUP_NAME_LOCATION);
                startActivity(intentLocation);

                break;

            case R.id.fragment_add_people_constraintLayout:

                Intent intentPeople = new Intent(context, AddRule.class);
                intentPeople.putExtra(ProvidersApp.GROUP_NAME, ProvidersApp.GROUP_NAME_PEOPLE);
                startActivity(intentPeople);

                break;
        }

    }
}
