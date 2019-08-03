package com.ali.rnp.khodemon.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.rnp.khodemon.DataModel.CheckModel;
import com.ali.rnp.khodemon.MyApplication;
import com.ali.rnp.khodemon.R;

import java.util.ArrayList;


public class FilterNonExpandAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "FilterNonExpandAdapter";
    private ArrayList<CheckModel> models;


    public FilterNonExpandAdapter(ArrayList<CheckModel> models) {
        this.models = models;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_filter_normal, parent, false);
        return new FilterNormalHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        FilterNormalHolder mHolder = (FilterNormalHolder) holder;

        bindFilter(mHolder, position);

    }

    private void bindFilter(FilterNormalHolder mHolder, int position) {

        CheckModel checkModel = models.get(position);

        mHolder.titleChTv.setText(checkModel.getTitle());
        mHolder.titleChTv.setChecked(checkModel.getChecked());

        mHolder.itemView.setOnClickListener(v -> {

            checkModel.setChecked(!checkModel.getChecked());

            models.set(position, checkModel);

            notifyDataSetChanged();

        });
    }


    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    public void clearChoices() {

        for (int i = 0; i < models.size(); i++) {
            models.get(i).setChecked(false);
        }

        notifyDataSetChanged();
    }

    public static class FilterNormalHolder extends RecyclerView.ViewHolder {

        CheckedTextView titleChTv;

        FilterNormalHolder(View itemView) {
            super(itemView);

            titleChTv = itemView.findViewById(R.id.rcv_filter_normal_titleChTv);
            titleChTv.setTypeface(MyApplication.getShpIranSansMoblie(itemView.getContext()));

        }

    }


}
