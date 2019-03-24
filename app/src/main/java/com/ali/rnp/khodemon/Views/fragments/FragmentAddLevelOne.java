package com.ali.rnp.khodemon.Views.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ali.rnp.khodemon.Adapter.UriAdapter;


import com.ali.rnp.khodemon.GifSizeFilter;
import com.ali.rnp.khodemon.Glide4Engine;
import com.ali.rnp.khodemon.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnSelectedListener;

import java.util.ArrayList;
import java.util.List;


public class FragmentAddLevelOne extends Fragment implements UriAdapter.OnItemChooseRecyclerClicked {

    private Context context;
    private RecyclerView recyclerViewImages;
    private Button button;
    public static UriAdapter mAdapter;
    private static final int REQUEST_CODE_CHOOSE = 23;
    private static final String TAG = "FragmentAddLevelOne";

    private OnNextButtonClicked onNextButtonClicked;


    public FragmentAddLevelOne() {

    }

    @SuppressLint("ValidFragment")
    public FragmentAddLevelOne(OnNextButtonClicked onNextButtonClicked) {
        this.onNextButtonClicked = onNextButtonClicked;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_add_level_one,container,false);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {
        recyclerViewImages = rootView.findViewById(R.id.fragment_add_one_recycler_view);
        button = rootView.findViewById(R.id.fragment_add_one_button);
        recyclerViewImages.setLayoutManager(new GridLayoutManager(context,3));
        recyclerViewImages.setAdapter(mAdapter = new UriAdapter(context));
        

        

        
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onNextButtonClicked.onNextClicked(2);
            }
        });


        Uri path = Uri.parse("android.resource://com.ali.rnp.khodemon/" + R.drawable.holder_banner);
        List<Uri> uriPath = new ArrayList<>();
        uriPath.add(path);
        mAdapter.setData(uriPath,true,this);
    }

    private void injectPhoto() {



        Matisse.from(getActivity())
                .choose(MimeType.ofImage(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.ali.rnp.khodemon.fileprovider"))
                .maxSelectable(10)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(
                        getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
//                                            .imageEngine(new GlideEngine())  // for glide-V3
                .imageEngine(new Glide4Engine())    // for glide-V4
                .setOnSelectedListener(new OnSelectedListener() {
                    @Override
                    public void onSelected(
                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e(TAG, "onSelected: pathList=" + pathList);

                    }
                })
               /* .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(new OnCheckedListener() {
                    @Override
                    public void onCheck(boolean isChecked) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e(TAG, "onCheck: isChecked=" + isChecked);
                    }
                })*/
                .forResult(REQUEST_CODE_CHOOSE);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void onRecyclerClicked() {
        //Choose photo from gallery
            injectPhoto();

    }

    public interface OnNextButtonClicked{
        void onNextClicked(int nextLevel);
    }
}
