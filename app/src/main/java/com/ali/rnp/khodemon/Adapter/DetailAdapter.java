package com.ali.rnp.khodemon.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ali.rnp.khodemon.DataModel.Info;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Info> infoList;

    private int VIEW_TYPE_TITLE = 0;
    private int VIEW_TYPE_INFO = 1;

    public DetailAdapter(ArrayList<Info> infoList) {

        this.infoList = infoList;

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }

    @Override
    public int getItemViewType(int position) {
        // return super.getItemViewType(position);


        if (position == 0)
            return VIEW_TYPE_TITLE;
        else
            return VIEW_TYPE_INFO;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_TITLE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_title_detail,parent,false);
            return new DetailAdapterTitleHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_location_detail, parent, false);
            return new DetailAdapterHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

         if (holder instanceof DetailAdapterHolder){
           DetailAdapterHolder mHolder = (DetailAdapterHolder) holder;
            mHolder.bindDetail(mHolder, infoList.get(position + 1));
        }


    }

    @Override
    public int getItemCount() {
        return infoList.size() + 1;
    }


    class DetailAdapterHolder extends RecyclerView.ViewHolder {
        MyTextView subjectTV;
        MyTextView descriptionTV;
        ImageView iconIV;

        DetailAdapterHolder(@NonNull View itemView) {
            super(itemView);
            iconIV = itemView.findViewById(R.id.recycler_view_location_detail_iconIV);
            subjectTV = itemView.findViewById(R.id.recycler_view_location_detail_subjectTV);
            descriptionTV = itemView.findViewById(R.id.recycler_view_location_detail_variableTV);
        }

        void bindDetail(DetailAdapterHolder holder, Info info) {

            holder.subjectTV.setText(info.getSubject());
            holder.descriptionTV.setText(info.getDescription());

            holder.iconIV.setImageResource(info.getIcon());

        }
    }

    class DetailAdapterTitleHolder extends RecyclerView.ViewHolder {
        MyTextView titleTV;

         DetailAdapterTitleHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.recycler_view_title_detail_TV);

            titleTV.setText("مشخصات");
        }


    }
}
