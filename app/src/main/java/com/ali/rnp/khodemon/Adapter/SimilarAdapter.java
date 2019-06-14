package com.ali.rnp.khodemon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.Holder.TitleHolder;
import com.ali.rnp.khodemon.Interface.OnItemClickListener;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SimilarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<LocationPeople> locationPeopleList;
    private Context context;
    private OnItemClickListener onItemClickListener;


    public SimilarAdapter(Context context, ArrayList<LocationPeople> locationPeopleList){
       this.context = context;
        this.locationPeopleList = locationPeopleList ;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_similar,parent,false);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(340, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new SimilarHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof SimilarHolder) {
            SimilarHolder mHolder = (SimilarHolder) holder;
            mHolder.bindSimilar(mHolder, locationPeopleList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return locationPeopleList.size();
    }

    class SimilarHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        MyTextView nameTv;
        MyTextView tagTv;

        SimilarHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recycler_view_similar_image);
            nameTv = itemView.findViewById(R.id.recycler_view_similar_name);
            tagTv = itemView.findViewById(R.id.recycler_view_similar_tag);
        }

         void bindSimilar(SimilarHolder holder, LocationPeople locationPeople){

            holder.nameTv.setText(locationPeople.getName());
            holder.tagTv.setText(locationPeople.getTag());

            if (!locationPeople.getOriginalPic().equals("")){
                Picasso.get()
                        .load(locationPeople.getOriginalPic())
                        .placeholder(R.drawable.holder_banner)
                        .centerCrop()
                        .resize(500,500)
                        .into(holder.imageView);
            }



            holder.itemView.setOnClickListener(v -> {
                if (onItemClickListener != null)
                onItemClickListener.onItemClick(locationPeople);
            });

        }
    }
}
