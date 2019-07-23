package com.ali.rnp.khodemon.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.DataModel.Filter;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FilterOptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Filter> filterList;

    public FilterOptionAdapter(ArrayList<Filter> filterList) {
        this.filterList = filterList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_filter_option, parent, false);

        return new FilterOptionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        FilterOptionHolder mHolder = (FilterOptionHolder) holder;
        mHolder.bind(filterList.get(position));


    }

    @Override
    public int getItemCount() {
        return filterList == null ? 0 : filterList.size();
    }

    class FilterOptionHolder extends RecyclerView.ViewHolder {
        private MyTextView titleTv;
        private MyTextView countTv;

        FilterOptionHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.rcv_filter_title);
            countTv = itemView.findViewById(R.id.rcv_filter_count);
        }

        void bind(Filter filter) {

            titleTv.setText(filter.getTitle());


        }
    }
}
