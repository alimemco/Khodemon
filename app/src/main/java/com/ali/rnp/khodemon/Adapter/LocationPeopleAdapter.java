package com.ali.rnp.khodemon.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LocationPeopleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<LocationPeople> locationPeopleList;

    private static final int LOCATION_TYPE = 0 ;
    private static final int PEOPLE_TYPE = 1;
    private static final int AD_TYPE = 2 ;

    private static final String LOCATION_GROUP = "LOCATION" ;
    private static final String PEOPLE_GROUP = "PEOPLE" ;

    private static final String TAG = "LocationPeopleAdapter";

    public LocationPeopleAdapter(Context context){
        this.context = context;
    }

    public void setListDataForAdapter(List<LocationPeople> locationPeopleList){
        this.locationPeopleList = locationPeopleList ;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View normalView = LayoutInflater.from(context).inflate(R.layout.recycler_view_home_items_location,parent,false);
                return new LocationHolder(normalView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LocationHolder){
            LocationHolder locationHolder = (LocationHolder) holder;
            locationHolder.bindNormalLocation(locationPeopleList.get(position));

        }
    }



    @Override
    public int getItemCount() {
        return locationPeopleList.size();
    }

    public static class LocationHolder extends RecyclerView.ViewHolder{

        MyTextView nameTextView;
        MyTextView tagTextView;
        ImageView originalPicImageView;
        ImageView redImageView;

        public LocationHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.recycler_view_group_items_people_name_textView);
            tagTextView = itemView.findViewById(R.id.recycler_view_group_items_people_city_textView);
            originalPicImageView = itemView.findViewById(R.id.recycler_view_group_items_people_image_imageView);
            redImageView = itemView.findViewById(R.id.recycler_view_home_items_normal_red_imageView);
        }

        public void bindNormalLocation(final LocationPeople locationPeople) {

            nameTextView.setText(locationPeople.getName());
            tagTextView.setText(locationPeople.getGroup());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Picasso.get().load(locationPeople.getOriginalPic()).centerCrop().resize(500,500).placeholder(R.drawable.holder_banner).into(originalPicImageView);

                }
            },1);

            if (locationPeople.getGroup().equals(LOCATION_GROUP)){
                redImageView.setVisibility(View.INVISIBLE);
            }else if (locationPeople.getGroup().equals(PEOPLE_GROUP)){
                redImageView.setVisibility(View.VISIBLE);
            }

        }
    }


}
