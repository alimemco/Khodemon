package com.ali.rnp.khodemon.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<LocationPeople> locationPeopleList;
    private String kamaEnglish;

    private static final int AD_TYPE = 1;

    private static final String LOCATION_GROUP = "LOCATION";
    private static final String PEOPLE_GROUP = "PEOPLE";

    private static final String TAG = "LocationAdapter";

    public LocationAdapter(Context context) {
        this.context = context;
    }

    public void setListDataForAdapter(List<LocationPeople> locationPeopleList) {
        this.locationPeopleList = locationPeopleList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View normalView = LayoutInflater.from(context).inflate(R.layout.recycler_view_group_items_location, parent, false);
        return new LocationHolder(normalView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LocationHolder) {
            LocationHolder locationHolder = (LocationHolder) holder;
            locationHolder.bindNormalLocation(locationPeopleList.get(position));

            locationHolder.itemView.setOnClickListener(v -> {
                Toast.makeText(context, ""+locationPeopleList.get(position).getName(), Toast.LENGTH_SHORT).show();

            });

        }
    }


    @Override
    public int getItemCount() {
        return locationPeopleList.size();
    }

    public static class LocationHolder extends RecyclerView.ViewHolder {

        MyTextView nameTextView;
        MyTextView tagTextView;
        MyTextView ownerSellerTextView;
        MyTextView addressTextView;
        ImageView originalPicImageView;


        public LocationHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.recycler_view_group_items_people_name_textView);
            tagTextView = itemView.findViewById(R.id.recycler_view_group_items_location_tag_textView);
            ownerSellerTextView = itemView.findViewById(R.id.recycler_view_group_items_location_ownerSeller_textView);
            addressTextView = itemView.findViewById(R.id.recycler_view_group_items_location_address_textView);
            originalPicImageView = itemView.findViewById(R.id.recycler_view_group_items_people_image_imageView);

        }

        public void bindNormalLocation(final LocationPeople locationPeople) {

            nameTextView.setText(locationPeople.getName());
            tagTextView.setText(locationPeople.getTag());
            ownerSellerTextView.setText(locationPeople.getOwnerSeller());

            addressTextView.setText(locationPeople.getAddress());

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Picasso.get().load(locationPeople.getOriginalPic()).centerCrop().resize(500, 500).placeholder(R.drawable.holder_banner).into(originalPicImageView);

                }
            }, 1);


        }
    }


}
