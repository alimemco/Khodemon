package com.ali.rnp.khodemon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SimilarAdapter extends RecyclerView.Adapter<SimilarAdapter.SimilarHolder>{

    private ArrayList<LocationPeople> locationPeopleList;
    private Context context;


    public SimilarAdapter(Context context, ArrayList<LocationPeople> locationPeopleList){
       this.context = context;
        this.locationPeopleList = locationPeopleList ;
    }

    @NonNull
    @Override
    public SimilarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_similar,parent,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(340, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new SimilarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarHolder holder, int position) {

        holder.bindSimilar(holder,locationPeopleList.get(position));
    }

    @Override
    public int getItemCount() {
        return locationPeopleList.size();
    }

    class SimilarHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        MyTextView nameTv;

        SimilarHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recycler_view_similar_image);
            nameTv = itemView.findViewById(R.id.recycler_view_similar_name);
        }

         void bindSimilar(SimilarHolder holder, LocationPeople locationPeople){

            holder.nameTv.setText(locationPeople.getName());

            if (!locationPeople.getOriginalPic().equals("")){
                Picasso.get()
                        .load(locationPeople.getOriginalPic())
                        .placeholder(R.drawable.holder_banner)
                        .centerCrop()
                        .resize(500,500)
                        .into(holder.imageView);
            }


            holder.itemView.setOnClickListener(v -> Toast.makeText(context, locationPeople.getName(), Toast.LENGTH_SHORT).show());

        }
    }
}
