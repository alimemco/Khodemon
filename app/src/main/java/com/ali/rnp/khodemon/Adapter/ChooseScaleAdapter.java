package com.ali.rnp.khodemon.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.UtilsApp.UtilsApp;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChooseScaleAdapter extends RecyclerView.Adapter<ChooseScaleAdapter.ChooseScaleHolder> {

    private ArrayList<LocationPeople> locationPeopleList;
    private OnItemClickListener onItemClickListener;


    public ChooseScaleAdapter(ArrayList<LocationPeople> locationPeopleList){
        this.locationPeopleList = locationPeopleList;
    }

    @NonNull
    @Override
    public ChooseScaleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_choose_scale_adapter,parent,false);
        return new ChooseScaleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseScaleHolder holder, int position) {

        holder.bind(holder,locationPeopleList.get(position));

    }

    @Override
    public int getItemCount() {
        return locationPeopleList.size();
    }

    class ChooseScaleHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        MyTextView nameTv;
        MyTextView jobTv;
        ChooseScaleHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.rcv_choose_scale_image);
            nameTv = itemView.findViewById(R.id.rcv_choose_scale_name);
            jobTv = itemView.findViewById(R.id.rcv_choose_scale_job);
        }

        void bind(ChooseScaleHolder holder, LocationPeople locationPeople){

                nameTv.setText(locationPeople.getName());
               jobTv.setText(locationPeople.getTag());
               //jobTv.setText(locationPeople.getTag());

            if (onItemClickListener != null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(locationPeople);
                    }
                });
            }


            UtilsApp.getImage(locationPeople, holder.imageView);



        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(LocationPeople locationPeople);
    }
}
