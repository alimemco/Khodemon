package com.ali.rnp.khodemon.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ali.rnp.khodemon.DataModel.PictureUpload;
import com.ali.rnp.khodemon.Views.fragments.ScreenSlidePageFragment;

import java.util.ArrayList;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {


   // private ArrayList<String> imgAddressList;
    private ArrayList<PictureUpload> pictureUploadList;

    public ScreenSlidePagerAdapter(FragmentManager fm,ArrayList<PictureUpload> pictureUploadList) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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