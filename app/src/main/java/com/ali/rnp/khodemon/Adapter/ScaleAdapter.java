package com.ali.rnp.khodemon.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.DataModel.Info;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ScaleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Info> infoList;
    private int VIEW_TYPE_SCALE_TOP = 0;
    private int VIEW_TYPE_SCALE_NORMAL = 1;

    public ScaleAdapter(ArrayList<Info> infoList) {
        this.infoList = infoList;
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_SCALE_NORMAL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       if (viewType == VIEW_TYPE_SCALE_NORMAL){
           View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_scale_adapter, parent, false);

           return new ScaleHolder(view);
       }else {
           View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_scale_adapter, parent, false);

           return new ScaleHolder(view);
       }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof ScaleHolder) {
            ScaleHolder mHolder = (ScaleHolder) holder;
            mHolder.bindScale(mHolder, infoList.get(position));
        }

    }


    @Override
    public int getItemCount() {
        return infoList.size();
    }

    class ScaleHolder extends RecyclerView.ViewHolder {
        MyTextView titleTV;
        MyTextView desOneTV;
        MyTextView desTwoTV;

        ScaleHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.rcv_scale_adapter_title);
            desOneTV = itemView.findViewById(R.id.rcv_scale_adapter_decOne);
            desTwoTV = itemView.findViewById(R.id.rcv_scale_adapter_decTwo);

        }

        void bindScale(ScaleHolder holder, Info info) {
            holder.titleTV.setText(info.getSubject());
            holder.desOneTV.setText(info.getDescription());
            holder.desTwoTV.setText("any data");
        }
    }
}
