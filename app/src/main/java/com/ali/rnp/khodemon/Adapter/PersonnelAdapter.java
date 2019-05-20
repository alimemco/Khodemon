package com.ali.rnp.khodemon.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.Library.CircularImageView;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PersonnelAdapter extends RecyclerView.Adapter<PersonnelAdapter.PersonnelHolder> {

    private ArrayList<LocationPeople> locationPeopleList;


    public PersonnelAdapter(ArrayList<LocationPeople> locationPeopleList){
        this.locationPeopleList = locationPeopleList;
    }


    @NonNull
    @Override
    public PersonnelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_personnel,parent,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
       lp.setMargins(12,0,0,12);
        view.setLayoutParams(lp);
        return new PersonnelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonnelHolder holder, int position) {

        bindView(holder,position);

    }



    @Override
    public int getItemCount() {
        return locationPeopleList.size();
    }

    class PersonnelHolder extends RecyclerView.ViewHolder {
        CircularImageView imageView;
        MyTextView nameTv;
        MyTextView jobTv;

        PersonnelHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recycler_view_personnel_image);
            nameTv = itemView.findViewById(R.id.recycler_view_personnel_name);
            jobTv = itemView.findViewById(R.id.recycler_view_personnel_job);

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    private void bindView(PersonnelHolder holder, int position) {

        if (!locationPeopleList.get(position).getOriginalPic().equals("")){
            Picasso.get().
                    load(locationPeopleList.get(position).getOriginalPic())
                    //.placeholder(R.drawable.holder_banner)
                    .resize(400,400)
                    .into(holder.imageView);
        }


        holder.nameTv.setText(locationPeopleList.get(position).getName());
        holder.jobTv.setText(locationPeopleList.get(position).getTag());





    }
}
