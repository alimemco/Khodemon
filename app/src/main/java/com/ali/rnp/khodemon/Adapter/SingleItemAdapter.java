package com.ali.rnp.khodemon.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SingleItemAdapter extends RecyclerView.Adapter<SingleItemAdapter.SingleItemHolder>{

    List<LocationPeople> locationPeopleList;
    Context context;


    public SingleItemAdapter(Context context){
        this.context = context;
    }

    public void setListDataForAdapter(List<LocationPeople> locationPeopleList) {
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

    }

    @Override
    public int getItemCount() {
        return locationPeopleList.size();
    }

    public static class SingleItemHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public SingleItemHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recycler_view_home_items_linear_layout_imageView);
        }

        public void bindNormalLocation(final LocationPeople locationPeople) {


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Picasso.get().load(locationPeople.getOriginalPic()).centerCrop().resize(500, 500).placeholder(R.drawable.holder_banner).into(imageView);

                }
            }, 1);

        }
    }
}
