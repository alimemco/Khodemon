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

public class PeopleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<LocationPeople> locationPeopleList;


    private static final int AD_TYPE = 2 ;

    private static final String LOCATION_GROUP = "LOCATION" ;
    private static final String PEOPLE_GROUP = "PEOPLE" ;

    private static final String TAG = "PeopleAdapter";

    public PeopleAdapter(Context context){
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

                View normalView = LayoutInflater.from(context).inflate(R.layout.recycler_view_group_items_people,parent,false);
                return new PeopleHolder(normalView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PeopleHolder){
            PeopleHolder locationHolder = (PeopleHolder) holder;
            locationHolder.bindPeople(locationPeopleList.get(position));

        }
    }



    @Override
    public int getItemCount() {
        return locationPeopleList.size();
    }

    public static class PeopleHolder extends RecyclerView.ViewHolder{

        MyTextView nameTextView;
        MyTextView cityTextView;
        MyTextView tagTextView;
        MyTextView workExperienceTextView;
        MyTextView expertsTextView;

        ImageView originalPicImageView;



        public PeopleHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.recycler_view_group_items_people_name_textView);
            tagTextView = itemView.findViewById(R.id.recycler_view_group_items_people_tag_textView);
            cityTextView = itemView.findViewById(R.id.recycler_view_group_items_people_city_textView);
            workExperienceTextView = itemView.findViewById(R.id.recycler_view_group_items_people_work_experience_textView);
            expertsTextView = itemView.findViewById(R.id.recycler_view_group_items_people_experts_textView);
            originalPicImageView = itemView.findViewById(R.id.recycler_view_group_items_people_image_imageView);
        }

        public void bindPeople(final LocationPeople locationPeople) {

            nameTextView.setText(locationPeople.getName());
            tagTextView.setText(locationPeople.getTag());
            cityTextView.setText(locationPeople.getCity());
            workExperienceTextView.setText(convertMonthToYear(locationPeople.getWork_experience()));
            expertsTextView.setText(locationPeople.getExperts());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Picasso.get().load(locationPeople.getOriginalPic()).centerCrop().resize(500,500).placeholder(R.drawable.holder_banner).into(originalPicImageView);

                }
            },1);



        }

        private String convertMonthToYear(int work_experience) {

            String msg = "";
            if (work_experience >= 0 && work_experience <= 6){
                msg = "نیم سال تجربه";
            }else if(work_experience >= 7 && work_experience <= 12) {
                msg = "1 سال تجربه";
            }else if(work_experience >= 13&& work_experience <= 24) {
                msg = "2 سال تجربه";
            }else if(work_experience >= 25) {
                msg = "بیش از 2 سال تجربه";
            }
            return msg;
        }
    }


}
