package com.ali.rnp.khodemon.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.DataModel.PictureUpload;
import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

public class PersonnelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<LocationPeople> locationPeopleList;
    //private ArrayList<PictureUpload> pictureUploadList;

    private static final int VIEW_TYPE_PERSONNEL = 0;
    private static final int VIEW_TYPE_ADD_PERSONNEL = 1;

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
        this.isEmptyPersonnel = true;
    }

    @Override
    public int getItemViewType(int position) {
        if (isEmptyPersonnel) {
            return VIEW_TYPE_ADD_PERSONNEL;
        } else {

            if (position == locationPeopleList.size()) {
                return VIEW_TYPE_ADD_PERSONNEL;
            } else {
                return VIEW_TYPE_PERSONNEL;
            }
        }


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_PERSONNEL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_personnel, parent, false);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(12, 0, 0, 12);
            view.setLayoutParams(lp);
            return new PersonnelHolder(view);
        } else {
            View viewAdd = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_add_personnel, parent, false);
            RecyclerView.LayoutParams lpAdd = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lpAdd.setMargins(12, 0, 0, 12);
            viewAdd.setLayoutParams(lpAdd);
            return new PersonnelAddHolder(viewAdd);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof PersonnelHolder) {
            PersonnelHolder mHolder = (PersonnelHolder) holder;
            mHolder.bindPersonnelView(mHolder, locationPeopleList.get(position));

        } else if (holder instanceof PersonnelAddHolder) {

            PersonnelAddHolder mHolder = (PersonnelAddHolder) holder;
            mHolder.bindAddPersonnel(mHolder);


        }


    }


    @Override
    public int getItemCount() {
        return isEmptyPersonnel ? 1 : locationPeopleList.size() + 1;
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


            loadImage(holder, locationPeople);

        /*if (!locationPeople.getOriginalPic().equals("")){
            Picasso.get().
                    load(locationPeople.getOriginalPic())
                    .placeholder(R.drawable.holder_banner)
                    .into(holder.imageView);
        }*/

            holder.nameTv.setText(locationPeople.getName());
            holder.jobTv.setText(locationPeople.getTag());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, locationPeople.getName(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }


    class PersonnelAddHolder extends RecyclerView.ViewHolder {
        MyButton addBtn;

        public PersonnelAddHolder(@NonNull View itemView) {
            super(itemView);
            addBtn = itemView.findViewById(R.id.recycler_view_personnel_add_btn);


        }

        public void bindAddPersonnel(PersonnelAddHolder mHolder) {
            mHolder.addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(v);
                }
            });
        }


    }


    private void loadImage(PersonnelHolder holder, LocationPeople locationPeople) {
        if (!locationPeople.getOriginalPic().equals("")) {

            boolean isLarge;
            isLarge = locationPeople.getImageWidth() >= 1000;
            String IMG_ADDRESS;

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
                                    .placeholder(holder.imageView.getDrawable())
                                    .into(holder.imageView);

                            //imageView.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);

                        }

                        @Override
                        public void onError(Exception e) {

                        }

                    });

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

}
