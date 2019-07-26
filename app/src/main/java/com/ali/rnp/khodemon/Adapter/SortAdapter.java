package com.ali.rnp.khodemon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.rnp.khodemon.MyApplication;
import com.ali.rnp.khodemon.R;

import java.util.ArrayList;


public class SortAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> models;
    private Context context;
    private OnItemSortClick onItemSortClick;


    public SortAdapter( ArrayList<String> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.rcv_sort, parent, false);
        return new SortHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        SortHolder mHolder = (SortHolder) holder;
        mHolder.bind(position);

    }

    @Override
    public int getItemCount() {
        return models == null ? 0  : models.size();
    }

    public void setOnItemSortClick(OnItemSortClick onItemSortClick) {
        this.onItemSortClick = onItemSortClick;
    }

    public interface OnItemSortClick {
        void OnSortClick(String name);
    }

    class SortHolder extends RecyclerView.ViewHolder {

        AppCompatRadioButton titleRdBtn;

        SortHolder(View itemView) {
            super(itemView);

            titleRdBtn = itemView.findViewById(R.id.rcv_sort_titleRdBtn);
            titleRdBtn.setTypeface(MyApplication.getShpIranSansMoblie(context));

            titleRdBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemSortClick != null) {
                        onItemSortClick.OnSortClick(titleRdBtn.getText().toString());
                    }
                }
            });

        }

        void bind(int position){

            titleRdBtn.setText(models.get(position));

        }
    }


}
