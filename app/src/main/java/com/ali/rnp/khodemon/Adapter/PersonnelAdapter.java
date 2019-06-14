package com.ali.rnp.khodemon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.Holder.ButtonAddHolder;
import com.ali.rnp.khodemon.Holder.TitleHolder;
import com.ali.rnp.khodemon.Interface.OnButtonAddClick;
import com.ali.rnp.khodemon.Interface.OnItemClickListener;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.UtilsApp.UtilsApp;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonnelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<LocationPeople> locationPeopleList;
    //private ArrayList<PictureUpload> pictureUploadList;

    private static final int VIEW_TYPE_TITLE_PERSONNEL = 0;
    private static final int VIEW_TYPE_PERSONNEL = 1;
    private static final int VIEW_TYPE_ADD_PERSONNEL = 2;


    //private OnItemClickListener onItemClickListener;
    private OnButtonAddClick onButtonAddClick;
    private OnItemClickListener onItemClickListener;
    private Context context;
    private boolean isEmptyPersonnel;

    private static final String TAG = "PersonnelAdapter";


    public PersonnelAdapter(Context context, ArrayList<LocationPeople> locationPeopleList) {
        this.context = context;
        this.locationPeopleList = locationPeopleList;
        this.isEmptyPersonnel = false;
    }

    public PersonnelAdapter(Context context, boolean isEmptyPersonnel) {
        this.context = context;


        if (isEmptyPersonnel )
            this.isEmptyPersonnel = true;
        else
            throw new IllegalArgumentException(context.toString()+" -> when data is empty 'isEmptyPersonnel' must set true");
    }

    @Override
    public int getItemViewType(int position) {
        if (isEmptyPersonnel) {
            if (position == 0)
                return VIEW_TYPE_TITLE_PERSONNEL;
            else
                return VIEW_TYPE_ADD_PERSONNEL;

        } else {

            if (position == 0)
                return VIEW_TYPE_TITLE_PERSONNEL;

            else if (position == locationPeopleList.size() + 1)
                return VIEW_TYPE_ADD_PERSONNEL;

            else
                return VIEW_TYPE_PERSONNEL;

        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_PERSONNEL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_personnel, parent, false);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(12, 0, 30, 12);
            view.setLayoutParams(lp);
            return new PersonnelHolder(view);
        } else if (viewType == VIEW_TYPE_TITLE_PERSONNEL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_title_detail, parent, false);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(12, 0, 0, 12);
            view.setLayoutParams(lp);
            return new TitleHolder("پرسنل",view);
        } else {
            View viewAdd = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_button_add, parent, false);
            RecyclerView.LayoutParams lpAdd = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lpAdd.setMargins(12, 0, 0, 0);
            viewAdd.setLayoutParams(lpAdd);
            return new ButtonAddHolder("اضافه کردن", viewAdd,
                    ProvidersApp.RECYCLER_VIEW_PERSONNEL, onButtonAddClick);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof PersonnelHolder) {
            PersonnelHolder mHolder = (PersonnelHolder) holder;
            mHolder.bindPersonnelView(mHolder, locationPeopleList.get(position-1));

        }
    }


    @Override
    public int getItemCount() {
        return isEmptyPersonnel ? 2 : locationPeopleList.size() + 2;
    }

    class PersonnelHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        MyTextView nameTv;
        MyTextView jobTv;

        PersonnelHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recycler_view_personnel_image);
            nameTv = itemView.findViewById(R.id.recycler_view_personnel_name);
            jobTv = itemView.findViewById(R.id.recycler_view_personnel_job);

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        }

        void bindPersonnelView(PersonnelHolder holder, LocationPeople locationPeople) {

            UtilsApp.getImage(locationPeople,holder.imageView);


            holder.nameTv.setText(locationPeople.getName());
            holder.jobTv.setText(locationPeople.getTag());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Toast.makeText(context, locationPeople.getName(), Toast.LENGTH_SHORT).show();
                    if (onItemClickListener != null){
                        locationPeople.setGroup(ProvidersApp.GROUP_NAME_PEOPLE);
                        onItemClickListener.onItemClick(locationPeople);
                    }else {
                        throw new IllegalArgumentException("PersonnelAdapter must be implement 'onItemClickListener' ");
                    }
                }
            });

        }

    }


    public void setOnButtonAddClick(OnButtonAddClick onButtonAddClick) {
        this.onButtonAddClick = onButtonAddClick;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
/*
    public interface OnItemClickListener {
        void onItemClick(LocationPeople locationPeople);
    }*/

}
