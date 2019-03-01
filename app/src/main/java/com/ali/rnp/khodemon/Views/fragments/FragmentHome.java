package com.ali.rnp.khodemon.Views.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.LocationPeopleAdapter;
import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.BannerSlider.MainSliderAdapter;
import com.ali.rnp.khodemon.BannerSlider.PicassoImageLoadingService;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.MyLibrary.MyEditText;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Utils;
import com.ali.rnp.khodemon.Views.Activites.MainActivity;

import java.util.List;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ss.com.bannerslider.Slider;


public class FragmentHome extends Fragment implements
        View.OnClickListener{



    private Slider slider;
    private ProgressBar progressBar;
    private CoordinatorLayout rootLayout;
    private MyEditText searchEditText;
    private RecyclerView recyclerView;

    private Context context;



    private static final String TAG = "FragmentHome";



    private OnFragmentInteractionListener mListener;

    public FragmentHome() {

    }

    @SuppressLint("ValidFragment")
    public FragmentHome(Context context) {
        this.context = context;
    }

    public static FragmentHome newInstance() {
        FragmentHome fragment = new FragmentHome();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        initViews(rootView);
        SetupBannerSlider(rootView);
        SetupRecyclerViewHomeItems();



        return rootView;
    }

    private void SetupRecyclerViewHomeItems() {

        recyclerView.setLayoutManager(new GridLayoutManager(context,1));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ApiService apiService = new ApiService(context);
                apiService.getHomeItems(new ApiService.OnHomeItemReceived() {
                    @Override
                    public void onItemReceived(List<LocationPeople> locationPeopleList) {

                        progressBar.setVisibility(View.INVISIBLE);

                        if (locationPeopleList != null ){
                            LocationPeopleAdapter locationPeopleAdapter = new LocationPeopleAdapter(context);
                            locationPeopleAdapter.setListDataForAdapter(locationPeopleList);
                            recyclerView.setAdapter(locationPeopleAdapter);
                            Log.i(TAG, "onItemReceived: yes");
                        }else {
                            Toast.makeText(context, "Error Con", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "onItemReceived: no");
                        }



                    }
                });

            }
        },1);
    }


    private void SetupBannerSlider(View rootView) {
        Slider.init(new PicassoImageLoadingService(getContext()));
        slider = rootView.findViewById(R.id.fragment_home_MainSlider);

        slider.setAdapter(new MainSliderAdapter());


    }


    @SuppressLint("ClickableViewAccessibility")
    private void initViews(View rootView) {

        recyclerView = rootView.findViewById(R.id.fragment_home_recyclerView_homeItems);
        rootLayout = rootView.findViewById(R.id.fragment_home_coordinatorLayout);
        searchEditText = rootView.findViewById(R.id.fragment_home_editText_search);

        progressBar = rootView.findViewById(R.id.fragment_home_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        },5000);


        searchEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getContext(), "test", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {


        }
    }
}
