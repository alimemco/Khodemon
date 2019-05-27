package com.ali.rnp.khodemon.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ali.rnp.khodemon.DataModel.Info;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailAdapterHolder> {

    private ArrayList<Info> infoList;

    public DetailAdapter (ArrayList<Info> infoList){

        this.infoList = infoList;

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }

    @NonNull
    @Override
    public DetailAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_location_detail,parent,false);

        return new DetailAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapterHolder holder, int position) {

        holder.bindDetail(holder,infoList.get(position));

    }

    @Override
    public int getItemCount() {
        return infoList.size();
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

        void bindDetail(DetailAdapterHolder holder, Info info){

            holder.subjectTV.setText(info.getSubject());
            holder.descriptionTV.setText(info.getDescription());

            holder.iconIV.setImageResource(info.getIcon());

        }
    }
}
