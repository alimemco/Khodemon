package com.ali.rnp.khodemon.BottomSheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SortBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    private View view;
    private OnClickBottomSheet onClickBottomSheet;

    public static SortBottomSheet newInstance() {

        Bundle args = new Bundle();

        SortBottomSheet fragment = new SortBottomSheet();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.btm_sht_sort, container, false);
        initViews();
        return view;
    }

    private void initViews() {

        MyTextView nearestTv = view.findViewById(R.id.btm_sht_sort_nearest);
        MyTextView scoreTv = view.findViewById(R.id.btm_sht_sort_score);
        MyTextView seenTv = view.findViewById(R.id.btm_sht_sort_seen);

        nearestTv.setOnClickListener(this);
        scoreTv.setOnClickListener(this);
        seenTv.setOnClickListener(this);


    }

    public void setOnClickBottomSheet(OnClickBottomSheet onClickBottomSheet) {
        this.onClickBottomSheet = onClickBottomSheet;
    }

    @Override
    public void onClick(View v) {

        if (onClickBottomSheet != null) {
            MyTextView tv = (MyTextView) v;
            onClickBottomSheet.OnClickSheet(tv.getText().toString());
        }
        /*
        switch (v.getId()){
            case R.id.btm_sht_sort_closest:

                break;

            case R.id.btm_sht_sort_score:

                break;


            case R.id.btm_sht_sort_seen:

                break;
        }
        */
    }

    public interface OnClickBottomSheet {
        void OnClickSheet(String title);
    }
}
