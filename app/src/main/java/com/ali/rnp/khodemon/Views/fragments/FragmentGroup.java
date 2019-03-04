package com.ali.rnp.khodemon.Views.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.LocationAdapter;
import com.ali.rnp.khodemon.Adapter.LocationPeopleAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.Activites.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

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

                apiService.getGroupItems(jsonObjectGroup, new ApiService.OnGroupItemReceived() {
                    @Override
                    public void onItemGroupReceived(List<LocationPeople> locationPeopleList) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (locationPeopleList != null ){
                            if (groupName.equals(ApiService.LOCATION_GROUP_NAME)){
                                LocationAdapter locationAdapter = new LocationAdapter(getContext());
                                locationAdapter.setListDataForAdapter(locationPeopleList);
                                recyclerView.setAdapter(locationAdapter);

                            }else if (groupName.equals(ApiService.PEOPLE_GROUP_NAME)){
                                LocationPeopleAdapter locationPeopleAdapter = new LocationPeopleAdapter(getContext());
                                locationPeopleAdapter.setListDataForAdapter(locationPeopleList);
                                recyclerView.setAdapter(locationPeopleAdapter);
                            }




                        }else {
                            Toast.makeText(getContext(), "Error Connection", Toast.LENGTH_SHORT).show();

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
