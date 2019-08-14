package com.ali.rnp.khodemon.Views.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ali.rnp.khodemon.Adapter.ImageGalleryAdapter;
import com.ali.rnp.khodemon.Adapter.ImageGalleryRcvAdapter;
import com.ali.rnp.khodemon.DataModel.PictureUpload;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;


public class FragmentImageGalleryDialog extends FragmentDialog implements
        ViewPager.OnPageChangeListener,
        DiscreteScrollView.OnItemChangedListener {


    private static final String TAG = "FragmentImageGalleryDia";

    private ArrayList<PictureUpload> pictureUploadList;
    private int position;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
   // private RecyclerView mRecyclerView;
    private DiscreteScrollView scrollView;
    // private ImageGalleryRcvAdapter mRecyclerViewAdapter;
    private MyTextView textViewToolbar;



    public FragmentImageGalleryDialog() {

    }

    public static FragmentImageGalleryDialog newInstance(int position, ArrayList<PictureUpload> pictureUploadList) {

        Bundle args = new Bundle();
        args.putInt(ProvidersApp.KEY_BUNDLE_POSITION, position);
        args.putParcelableArrayList(ProvidersApp.KEY_BUNDLE_IMG_LIST, pictureUploadList);
        FragmentImageGalleryDialog fragment = new FragmentImageGalleryDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);

        if (getArguments() != null) {


            pictureUploadList = getArguments().getParcelableArrayList(ProvidersApp.KEY_BUNDLE_IMG_LIST);
            position = getArguments().getInt(ProvidersApp.KEY_BUNDLE_POSITION);


        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            if (dialog.getWindow() != null)
                dialog.getWindow().setLayout(width, height);
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_gallery_dialog, container, false);

        initViews(rootView);
        initCarouselScrollViewImages(rootView);
        initToolbar(rootView);

        setToolbarTextValue(position);

        return rootView;
    }

    private void initToolbar(View rootView) {
        Toolbar toolbar = rootView.findViewById(R.id.fragment_image_gallery_dialog_toolbar);
        textViewToolbar = rootView.findViewById(R.id.fragment_image_gallery_dialog_toolbar_textView);

        if (getActivity() != null){
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

            ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

            if (actionBar != null){
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowTitleEnabled(false);

                toolbar.setNavigationOnClickListener(v -> dismiss());
            }
        }

    }

    private void initCarouselScrollViewImages(View rootView) {

        scrollView = rootView.findViewById(R.id.picker);
        scrollView.setSlideOnFling(true);
       // ImageGalleryRcvAdapter imageGalleryRcvAdapter = ;
       // imageGalleryRcvAdapter.setImages(pictureUploadList);
        scrollView.setAdapter(new ImageGalleryRcvAdapter(pictureUploadList));
        scrollView.addOnItemChangedListener(this);
        scrollView.scrollToPosition(position);
        scrollView.setItemTransitionTimeMillis(120);
        scrollView.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

    }

    private void initViews(View rootView) {


        mPager = rootView.findViewById(R.id.fragment_image_gallery_dialog_viewPager);

        if (getActivity() != null && pictureUploadList != null) {
            mPagerAdapter = new ImageGalleryAdapter(getChildFragmentManager(), pictureUploadList);

            mPager.setAdapter(mPagerAdapter);
            mPager.setCurrentItem(position);
            mPager.addOnPageChangeListener(this);
        }




    }



    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int i) {
        mPager.setCurrentItem(i);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //mRecyclerView.smoothScrollToPosition(position);
        setToolbarTextValue(position);
        scrollView.smoothScrollToPosition(position);





    }

    private void setToolbarTextValue(int position) {
        StringBuilder sb = new StringBuilder();
        sb.append(position+1);
        sb.append(" از ");
        sb.append(pictureUploadList.size());
        textViewToolbar.setText(sb);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
