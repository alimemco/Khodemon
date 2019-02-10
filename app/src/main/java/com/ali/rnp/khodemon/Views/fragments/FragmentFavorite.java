package com.ali.rnp.khodemon.Views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import ss.com.bannerslider.Slider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.BannerSlider.MainSliderAdapter;
import com.ali.rnp.khodemon.BannerSlider.PicassoImageLoadingService;
import com.ali.rnp.khodemon.R;
import com.google.firebase.analytics.FirebaseAnalytics;


public class FragmentFavorite extends Fragment {


    private OnFragmentInteractionListener mListener;

    public FragmentFavorite() {

    }


    public static FragmentFavorite newInstance() {
        FragmentFavorite fragment = new FragmentFavorite();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAnalytics mfirebaseAnalytics;

        mfirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID,"12");
        bundle.putString("AliRnpEvent","We Can");

        mfirebaseAnalytics.logEvent("eventForAliRnp",bundle);




    }

    private void SetupBanner(View rootView) {

            Slider.init(new PicassoImageLoadingService(getContext()));
            Slider slider = rootView.findViewById(R.id.fragment_fav_MainSlider);

            slider.setAdapter(new MainSliderAdapter());



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);

        SetupBanner(rootView);

        return rootView;
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
