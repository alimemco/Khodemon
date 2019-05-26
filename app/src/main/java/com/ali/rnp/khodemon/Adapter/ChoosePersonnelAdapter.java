package com.ali.rnp.khodemon.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.UtilsApp.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChoosePersonnelAdapter extends RecyclerView.Adapter<ChoosePersonnelAdapter.ChoosePersonnelHolder> {

    private ArrayList<LocationPeople> locationPeopleList;
    private OnItemClickListener onItemClickListener;

    public ChoosePersonnelAdapter(ArrayList<LocationPeople> locationPeopleList){
        this.locationPeopleList = locationPeopleList;
    }

    @NonNull
    @Override
    public ChoosePersonnelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_choose_personnel,parent,false);
        return new ChoosePersonnelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoosePersonnelHolder holder, int position) {

        holder.bindChoosePerson(holder,locationPeopleList.get(position));

    }

    @Override
    public int getItemCount() {
        return locationPeopleList.size();
    }

    class ChoosePersonnelHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        MyTextView nameTv;
        MyTextView jobTv;
         ChoosePersonnelHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recycler_view_choose_personnel_image);
            nameTv = itemView.findViewById(R.id.recycler_view_choose_personnel_name);
            jobTv = itemView.findViewById(R.id.recycler_view_choose_personnel_job);
        }

        void bindChoosePerson(ChoosePersonnelHolder holder, LocationPeople locationPeople){

            nameTv.setText(locationPeople.getName());
            jobTv.setText(locationPeople.getTag());

            if (onItemClickListener != null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(locationPeople);
                    }
                });
            }


            Utils.getImage(locationPeople, holder.imageView);

           /* if (!locationPeople.getOriginalPic().equals("")) {
                boolean isLarge;
                isLarge = locationPeople.getImageWidth() >= 1000;
                String IMG_ADDRESS ;

                IMG_ADDRESS = (isLarge) ?
                        locationPeople.getImageThumb1000()
                        : locationPeople.getOriginalPic();


                Picasso.get()
                        .load(locationPeople.getImageThumb150())
                        .placeholder(R.drawable.holder_banner)
                        .into(holder.imageView, new Callback() {


                            @Override
                            public void onSuccess() {
                                Picasso.get()
                                        .load(IMG_ADDRESS)
                                        .resize(500,500)
                                        .centerCrop()
                                        .placeholder(holder.imageView.getDrawable())
                                        .into(holder.imageView);

                                //imageView.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);

                            }

                            @Override
                            public void onError(Exception e) {

                            }

                        });

            }*/

        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(LocationPeople locationPeople);
    }
}
