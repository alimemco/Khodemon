package com.ali.rnp.khodemon.Adapter;

import android.content.Context;
import android.util.Log;
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

    private static final int NORMAL_TYPE = 0 ;
    private static final int AD_TYPE = 1 ;

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
        return NORMAL_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View normalView = LayoutInflater.from(context).inflate(R.layout.recycler_view_home_items_normal,parent,false);
        return new LocationPeopleHolder(normalView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LocationPeopleHolder){
            LocationPeopleHolder locationPeopleHolder = (LocationPeopleHolder) holder;
            locationPeopleHolder.bindNormalLocationPeople(locationPeopleList.get(position));
            Log.i(TAG, "onBindViewHolder: "+position);
        }
    }



    @Override
    public int getItemCount() {
        return locationPeopleList.size();
    }

    public static class LocationPeopleHolder extends RecyclerView.ViewHolder{

        MyTextView nameTextView;
        MyTextView tagTextView;
        ImageView originalPicImageView;

        public LocationPeopleHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.recycler_view_home_items_normal_name_textView);
            tagTextView = itemView.findViewById(R.id.recycler_view_home_items_normal_tag_textView);
            originalPicImageView = itemView.findViewById(R.id.recycler_view_home_items_normal_image_imageView);
        }

        public void bindNormalLocationPeople(LocationPeople locationPeople) {

            Picasso.get().load(locationPeople.getOriginalPic()).placeholder(R.drawable.holder_banner).into(originalPicImageView);
            nameTextView.setText(locationPeople.getName());
            tagTextView.setText(locationPeople.getGroup());
            Log.i("test", "bindNormalLocationPeople: "+locationPeople.getGroup());

        }
    }
}
