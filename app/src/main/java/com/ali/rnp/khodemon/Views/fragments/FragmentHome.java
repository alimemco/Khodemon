package com.ali.rnp.khodemon.Views.fragments;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

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


public class FragmentHome extends Fragment  {


    private FrameLayout expertFrame;
    private FrameLayout locationFrame;
    private EditText searchEdTxt;
    private Slider slider;
    private CardView sliderCardView;
    private ConstraintLayout constraintLayout;
    private ConstraintLayout constraintLayoutTest;
    private boolean changedConsLayout;

    private static final String TAG = "FragmentHome";

    private OnFragmentInteractionListener mListener;

    public FragmentHome() {
        // Required empty public constructor
    }

    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    private void SetupBannerSlider(View rootView) {
        Slider.init(new PicassoImageLoadingService(getContext()));
        slider = rootView.findViewById(R.id.fragment_home_MainSlider);

        slider.setAdapter(new MainSliderAdapter());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        SetupBannerSlider(rootView);
        initViews(rootView);

        return rootView;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initViews(View rootView) {
        searchEdTxt = rootView.findViewById(R.id.fragment_home_edTxt_search);
        expertFrame = rootView.findViewById(R.id.fragment_home_frame_expert);
        locationFrame = rootView.findViewById(R.id.fragment_home_frame_location);
        sliderCardView = rootView.findViewById(R.id.fragment_home_MainSliderCardView);
        constraintLayout = rootView.findViewById(R.id.fragment_home_constraintLayout);
        constraintLayoutTest = rootView.findViewById(R.id.fragment_home_constraintLayout_test);

        changedConsLayout = false;

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


        expertFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TransitionManager.beginDelayedTransition(constraintLayout);
                if (changedConsLayout){
                    ConstraintSet constraintSetOrg = new ConstraintSet();
                    constraintSetOrg.clone(getContext(),R.layout.fragment_home);
                    constraintSetOrg.applyTo(constraintLayout);
                    searchEdTxt.clearFocus();

                }else {
                    ConstraintSet constraintSetNew = new ConstraintSet();
                    constraintSetNew.clone(getContext(),R.layout.trans_cons);
                    constraintSetNew.applyTo(constraintLayout);
                }
                changedConsLayout = !changedConsLayout;
/*
                new ConstraintSet();
                ConstraintSet consT;
                if (changedConsLayout){
                    consT = constraintSetOrg;
                    Log.i(TAG, "Doroste: "+changedConsLayout);
                    changedConsLayout = false;

                }else {
                    consT = constraintSetNew;
                    Log.i(TAG, "qalate: "+changedConsLayout);
                    changedConsLayout = true;

                }
                TransitionManager.beginDelayedTransition(constraintLayout);

                consT.applyTo(constraintLayout);

*/





/*
                ObjectAnimator anim = ObjectAnimator.ofFloat(expertFrame, "translationY", 100f);
                anim.setDuration(2000);
                anim.start();
                */
/*
                final int valueA = 120;
                Animation anim = new Animation() {
                    @Override
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        super.applyTransformation(interpolatedTime, t);
                        /*
                        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) expertFrame.getLayoutParams();
                        params.topToBottom = par
                        */
                        /*
                        ConstraintSet constraintSet = new ConstraintSet();
                        constraintSet.clone(constraintLayout);
                        constraintSet.connect(R.id.fragment_home_frame_location,ConstraintSet.RIGHT,R.id.fragment_home_frame_expert,ConstraintSet.LEFT);
                        constraintSet.connect(R.id.fragment_home_frame_location,ConstraintSet.TOP,R.id.fragment_home_frame_expert,ConstraintSet.BOTTOM);
                        constraintSet.applyTo(constraintLayout);
*/
                        /*

                        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) expertFrame.getLayoutParams();
                        params.leftMargin = (int) (valueA * interpolatedTime);
                        params.topToTop = 1;
                        expertFrame.setLayoutParams(params);

                    }
                };
                anim.setDuration(2000);
                expertFrame.startAnimation(anim);
                */

            }
        });

        locationFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // expertFrame.animate().setDuration(2000).translationYBy(0.2f).start();
                ObjectAnimator anim = ObjectAnimator.ofFloat(locationFrame, "translationY", 200f);
                anim.setDuration(2000);
                anim.start();

            }
        });


        searchEdTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                TransitionManager.beginDelayedTransition(constraintLayout);
                if (hasFocus) {

                    ConstraintSet constraintSetNew = new ConstraintSet();
                    constraintSetNew.clone(getContext(),R.layout.trans_cons);
                    constraintSetNew.applyTo(constraintLayout);
                    changedConsLayout = true;

                } else {
                    ConstraintSet constraintSetOrg = new ConstraintSet();
                    constraintSetOrg.clone(getContext(),R.layout.fragment_home);
                     constraintSetOrg.applyTo(constraintLayout);
                    changedConsLayout = false;

                }
            }
        });


    }

    private void animationMoveToPosition(View view, Float ToYValue) {
        TranslateAnimation anim = new TranslateAnimation
                (Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0, Animation.RELATIVE_TO_PARENT, ToYValue);

        anim.setDuration(600);
        //anim.setInterpolator(new AccelerateInterpolator());
        anim.setFillAfter(true);
        view.startAnimation(anim);


    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
