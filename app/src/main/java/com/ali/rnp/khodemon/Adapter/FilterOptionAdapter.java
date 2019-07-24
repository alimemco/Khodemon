package com.ali.rnp.khodemon.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.rnp.khodemon.DataModel.Filter;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;

import java.util.ArrayList;

public class FilterOptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Filter> filterList;
    private OnItemClicked onItemClicked;

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
        mHolder.bind(position);


    }

    @Override
    public int getItemCount() {
        return filterList == null ? 0 : filterList.size();
    }

    private void changeState(int position) {

        for (int i = 0; i < filterList.size(); i++) {
            this.filterList.get(i).setSelected(i == position);
        }

        notifyDataSetChanged();

    }

    public void setOnItemClicked(OnItemClicked onItemClicked) {
        this.onItemClicked = onItemClicked;
    }


    public interface OnItemClicked {
        void onItemClicked(Filter filter);
    }

    class FilterOptionHolder extends RecyclerView.ViewHolder {
        private MyTextView titleTv;
        private MyTextView countTv;
        ///private boolean isSelected = false;

        FilterOptionHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.rcv_filter_title);
            countTv = itemView.findViewById(R.id.rcv_filter_count);
        }

        void bind(int position) {

            Filter filter = filterList.get(position);

            titleTv.setText(filter.getTitle());
            itemView.setSelected(filter.getSelected());
            titleTv.setOnClickListener(v -> {
                changeState(position);
                if (onItemClicked != null) {
                    onItemClicked.onItemClicked(filter);
                }
            });

        }
    }
}