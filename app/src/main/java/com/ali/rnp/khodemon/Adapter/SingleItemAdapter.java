package com.ali.rnp.khodemon.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.Activities.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class SingleItemAdapter extends RecyclerView.Adapter<SingleItemAdapter.SingleItemHolder>{

    List<LocationPeople> locationPeopleList;

    Context context;


    public SingleItemAdapter(Context context){
        this.context = context;
    }

     void setListDataForAdapter(List<LocationPeople> locationPeopleList) {
        this.locationPeopleList = locationPeopleList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SingleItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.recycler_view_home_items_single_card,parent,false);

        return new SingleItemHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleItemHolder holder, int position) {

        holder.bindNormalLocation(locationPeopleList.get(position));

       // holder.itemView.setOnClickListener(v -> Toast.makeText(context, locationPeopleList.get(position).getName(), Toast.LENGTH_SHORT).show());

        ViewCompat.setTransitionName(holder.imageView, locationPeopleList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    int  postId = locationPeopleList.get(position).getId();


                Intent intent = new Intent(context, DetailActivity.class);
             //   intent.putExtra(ProvidersApp.KEY_POST_ID,postId);
                intent.putExtra(ProvidersApp.KEY_LOCATION_PEOPLE,locationPeopleList.get(position));
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return locationPeopleList.size();
    }

    static class SingleItemHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        MyTextView titleItemTextView;
        MyTextView tagItemTextView;

        SingleItemHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recycler_view_home_items_linear_layout_imageView);
            titleItemTextView = itemView.findViewById(R.id.recycler_view_home_items_linear_layout_title_textView);
            tagItemTextView = itemView.findViewById(R.id.recycler_view_home_items_linear_layout_tag_textView);
        }

        void bindNormalLocation(final LocationPeople locationPeople) {

            titleItemTextView.setText(locationPeople.getName());
            tagItemTextView.setText(locationPeople.getTag());


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!locationPeople.getOriginalPic().equals("")){
                        Picasso.get().load(locationPeople.getOriginalPic()).centerCrop().resize(500, 500).placeholder(R.drawable.holder_banner).error(R.drawable.logo_nafis_draw).into(imageView);

                    }else {
                        Picasso.get().load(R.drawable.logo_nafis_draw).centerCrop().resize(500, 500).into(imageView);
                    }

                }
            }, 1);

        }
    }






}
