package com.ali.rnp.khodemon.Adapter;

import com.ali.rnp.khodemon.DataModel.PictureUpload;
import com.ali.rnp.khodemon.Views.fragments.ScreenSlidePageFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {


   // private ArrayList<String> imgAddressList;
    private ArrayList<PictureUpload> pictureUploadList;

    public ScreenSlidePagerAdapter(FragmentManager fm,ArrayList<PictureUpload> pictureUploadList) {
        super(fm);
        this.pictureUploadList = pictureUploadList ;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return ScreenSlidePageFragment.newInstance(position,pictureUploadList);
    }

    @Override
    public int getCount() {
        return pictureUploadList.size();
    }
}