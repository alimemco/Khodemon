package com.ali.rnp.khodemon.Views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ali.rnp.khodemon.R;
import com.zhihu.matisse.listener.OnFragmentInteractionListener;

import androidx.fragment.app.Fragment;


public class FragmentFavorite extends Fragment {


    public FragmentFavorite() {

    }


    public static FragmentFavorite newInstance() {
        FragmentFavorite fragment = new FragmentFavorite();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);

        return rootView;
    }






}
