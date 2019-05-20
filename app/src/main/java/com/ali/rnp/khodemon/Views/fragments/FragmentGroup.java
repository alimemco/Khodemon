package com.ali.rnp.khodemon.Views.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.LocationAdapter;
import com.ali.rnp.khodemon.Adapter.PeopleAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.R;
import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FragmentGroup extends Fragment {

    private String groupName;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;


    public FragmentGroup() {

    }


    public static FragmentGroup newInstance() {
        FragmentGroup fragment = new FragmentGroup();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            groupName = getArguments().getString(FragmentHome.GROUP_KEY);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_group, container, false);

        initViews(rootView);

        setupRecyclerView();

        return rootView;
    }

    private void setupRecyclerView() {

        final JSONObject jsonObjectGroup = new JSONObject();
        try {
            jsonObjectGroup.put("group",groupName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ApiService apiService = new ApiService(getContext());
                int groupInt = 0;
                if (groupName.equals(ApiService.GROUP_NAME_LOCATION)){
                    groupInt = ApiService.LOCATION_GROUP_KEY;
                }else if (groupName.equals(ApiService.GROUP_NAME_PEOPLE)){
                    groupInt = ApiService.PEOPLE_GROUP_KEY;
                }
                apiService.getGroupItems(jsonObjectGroup,groupInt ,new ApiService.OnGroupItemReceived() {
                    @Override
                    public void onItemGroupReceived(ArrayList<LocationPeople> locationPeopleList, VolleyError error) {


                        progressBar.setVisibility(View.INVISIBLE);
                        Activity activity = getActivity();

                        if (activity != null){

                            if (locationPeopleList != null && error == null ){
                                if (groupName.equals(ApiService.GROUP_NAME_LOCATION)){
                                    LocationAdapter locationAdapter = new LocationAdapter(getContext());
                                    locationAdapter.setListDataForAdapter(locationPeopleList);
                                    recyclerView.setAdapter(locationAdapter);

                                }else if (groupName.equals(ApiService.GROUP_NAME_PEOPLE)){
                                    PeopleAdapter peopleAdapter = new PeopleAdapter(getContext());
                                    peopleAdapter.setListDataForAdapter(locationPeopleList);
                                    recyclerView.setAdapter(peopleAdapter);
                                }

                            }else if (error != null){
                                if (error instanceof NoConnectionError){
                                    String errorMsg = getResources().getString(R.string.no_internet_error_msg);
                                    Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();

                                } else {

                                    Toast.makeText(getContext(), "ERROR "+error.toString(), Toast.LENGTH_SHORT).show();
                                }

                            }else {
                                Toast.makeText(getContext(), "خطای نامشخص ", Toast.LENGTH_SHORT).show();

                            }


                        }



                    }
                });



            }
        },1);
    }

    private void initViews(View rootView) {
        recyclerView = rootView.findViewById(R.id.fragment_group_recyclerView);
        progressBar = rootView.findViewById(R.id.fragment_group_progressBar);

    }

}
