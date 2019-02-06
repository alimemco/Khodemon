package com.ali.rnp.khodemon.Views.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.ali.rnp.khodemon.BannerSlider.MainSliderAdapter;
import com.ali.rnp.khodemon.BannerSlider.PicassoImageLoadingService;
import com.ali.rnp.khodemon.MyApplication;
import com.ali.rnp.khodemon.R;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.transition.TransitionManager;
import ss.com.bannerslider.Slider;


public class FragmentHome extends Fragment implements View.OnClickListener {


    private FrameLayout expertFrame;
    private FrameLayout locationFrame;
    private EditText searchEdTxt;
    private Slider slider;
    private CardView sliderCardView;
    private ConstraintLayout constraintLayout;
    private ConstraintLayout constraintLayoutTest;
    private boolean isConstraintOrg = true;

    private static final String TAG = "FragmentHome";

    private OnFragmentInteractionListener mListener;

    public FragmentHome() {

    }

    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        initViews(rootView);
        SetupBannerSlider(rootView);
        SetupOnClick();

        return rootView;
    }

    private void SetupOnClick() {
        locationFrame.setOnClickListener(this);
        expertFrame.setOnClickListener(this);
    }

    private void SetupBannerSlider(View rootView) {
        Slider.init(new PicassoImageLoadingService(getContext()));
        slider = rootView.findViewById(R.id.fragment_home_MainSlider);

        slider.setAdapter(new MainSliderAdapter());


    }

    @SuppressLint("ClickableViewAccessibility")
    private void initViews(View rootView) {
        searchEdTxt = rootView.findViewById(R.id.fragment_home_edTxt_search);
        expertFrame = rootView.findViewById(R.id.fragment_home_frame_expert);
        locationFrame = rootView.findViewById(R.id.fragment_home_frame_location);
        sliderCardView = rootView.findViewById(R.id.fragment_home_MainSliderCardView);
        constraintLayout = rootView.findViewById(R.id.fragment_home_constraintLayout);
        constraintLayoutTest = rootView.findViewById(R.id.fragment_home_constraintLayout_test);


        searchEdTxt.setTypeface(MyApplication.getIranSans(getContext()));


        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (searchEdTxt.isFocused()) {
                    searchEdTxt.clearFocus();
                }
                return false;
            }
        });

/*
        expertFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TransitionManager.beginDelayedTransition(constraintLayout);
                if (isConstraintOrg){
                    ConstraintSet constraintSetOrg = new ConstraintSet();
                    constraintSetOrg.clone(getContext(),R.layout.fragment_home);
                    constraintSetOrg.applyTo(constraintLayout);
                    searchEdTxt.clearFocus();

                }else {
                    ConstraintSet constraintSetNew = new ConstraintSet();
                    constraintSetNew.clone(getContext(),R.layout.trans_cons);
                    constraintSetNew.applyTo(constraintLayout);
                }
                isConstraintOrg = !isConstraintOrg;


            }
        });*/
/*
        locationFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjectAnimator anim = ObjectAnimator.ofFloat(locationFrame, "translationY", 200f);
                anim.setDuration(2000);
                anim.start();

            }
        });
*/

        searchEdTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                TransitionManager.beginDelayedTransition(constraintLayout);
                if (hasFocus) {

                    ConstraintSet constraintSetNew = new ConstraintSet();
                    constraintSetNew.clone(getContext(), R.layout.trans_cons);
                    constraintSetNew.applyTo(constraintLayout);
                    isConstraintOrg = false;

                } else {
                    ConstraintSet constraintSetOrg = new ConstraintSet();
                    constraintSetOrg.clone(getContext(), R.layout.fragment_home);
                    constraintSetOrg.applyTo(constraintLayout);
                    isConstraintOrg = true;

                }
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

            case R.id.fragment_home_frame_location:

                if (isConstraintOrg) {
                    searchEdTxt.requestFocus();
                } else {
                    searchEdTxt.clearFocus();
                }
                break;

            case R.id.fragment_home_frame_expert:
                if (isConstraintOrg) {
                    searchEdTxt.requestFocus();
                } else {
                    searchEdTxt.clearFocus();
                }

                break;
            /**
             searchEdTxt = rootView.findViewById(R.id.fragment_home_edTxt_search);
             expertFrame = rootView.findViewById(R.id.fragment_home_frame_expert);
             locationFrame = rootView.findViewById(R.id.fragment_home_frame_location);
             sliderCardView = rootView.findViewById(R.id.fragment_home_MainSliderCardView);
             constraintLayout = rootView.findViewById(R.id.fragment_home_constraintLayout);
             constraintLayoutTest = rootView.findViewById(R.id.fragment_home_constraintLayout_test);

             */

        }
    }
}
