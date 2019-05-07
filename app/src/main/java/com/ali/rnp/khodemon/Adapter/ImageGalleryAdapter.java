package com.ali.rnp.khodemon.Adapter;

import com.ali.rnp.khodemon.Views.fragments.FragmentImageGalley;
import com.ali.rnp.khodemon.Views.fragments.ScreenSlidePageFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ImageGalleryAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> imgAddressList;

    public ImageGalleryAdapter(FragmentManager fm, ArrayList<String> imgAddressList) {
        super(fm);
        this.imgAddressList = imgAddressList ;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return FragmentImageGalley.newInstance(imgAddressList.get(position));
    }

    @Override
    public int getCount() {
        return imgAddressList.size();
    }
}
