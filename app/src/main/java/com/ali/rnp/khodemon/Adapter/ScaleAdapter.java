package com.ali.rnp.khodemon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ali.rnp.khodemon.DataModel.Info;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.UtilsApp.UtilsApp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ScaleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Info> infoListOne;
    private ArrayList<Info> infoListScale;
    private LocationPeople locPeoPostOne;
    private LocationPeople locPeoPostScale;
    private String GROUP_NAME;
    private int VIEW_TYPE_SCALE_TOP = 0;
    private int VIEW_TYPE_SCALE_NORMAL = 1;

    private Context context;
    private OnAddScaleClick onAddScaleClick;



    public ScaleAdapter(ArrayList<Info> infoListOne, LocationPeople locPeoPost) {
        this.infoListOne = infoListOne;
        this.locPeoPostOne = locPeoPost;
        this.GROUP_NAME = locPeoPost.getGroup();
    }

    public void setSaleSecond(ArrayList<Info> infoListTwo, LocationPeople locPeoPostTwo) {
        this.infoListScale = infoListTwo;
        this.locPeoPostScale = locPeoPostTwo;
        notifyDataSetChanged();
    }

    public void setOnAddScaleClick(OnAddScaleClick onAddScaleClick) {
        this.onAddScaleClick = onAddScaleClick;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_SCALE_TOP;
        }
        return VIEW_TYPE_SCALE_NORMAL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SCALE_TOP) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_scale_top_adapter, parent, false);

            return new ScaleTopHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_scale_adapter, parent, false);

            return new ScaleHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof ScaleHolder) {
            ScaleHolder mHolder = (ScaleHolder) holder;
            mHolder.bind(mHolder, infoListOne.get(position - 1));
            if (infoListScale != null) {
                mHolder.bindScale(mHolder, infoListScale.get(position - 1));
        }
        } else if (holder instanceof ScaleTopHolder) {
            ScaleTopHolder mHolder = (ScaleTopHolder) holder;
            mHolder.bindTop(locPeoPostOne);
            mHolder.bindScaleTop(locPeoPostScale);
        }

    }


    @Override
    public int getItemCount() {
        return infoListOne.size() + 1;
    }

    class ScaleHolder extends RecyclerView.ViewHolder {
        MyTextView titleTV;
        MyTextView desOneTV;
        MyTextView desScaleTV;

        ScaleHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.rcv_scale_adapter_title);
            desOneTV = itemView.findViewById(R.id.rcv_scale_adapter_decOne);
            desScaleTV = itemView.findViewById(R.id.rcv_scale_adapter_decTwo);

            desScaleTV.setText("-");

        }

        void bind(ScaleHolder holder, Info info) {
            //Info infoPrs = UtilsApp.parseInfoScale(info);
            Info infoPrs = null;
            if (GROUP_NAME.equals(ProvidersApp.GROUP_NAME_LOCATION)){
                infoPrs = UtilsApp.parseInfoLocation(info,true);
                holder.titleTV.setText(infoPrs.getSubject());
                holder.desOneTV.setText(infoPrs.getDescription());
            }else {
                infoPrs = UtilsApp.parseInfoPeople(info,true);
                holder.titleTV.setText(infoPrs.getSubject());
                holder.desOneTV.setText(infoPrs.getDescription());
            }


            // holder.desScaleTV.setText(infoPrs.getDescription());
        }

        void bindScale(ScaleHolder holder, Info info) {

            if (info.getDescription().equals("") || info.getDescription().equals("0")){
                info.setDescription("-");
            }else {
                holder.desScaleTV.setText(info.getDescription());
            }


        }
    }

    class ScaleTopHolder extends RecyclerView.ViewHolder {
        ImageView desOneIV;
        ImageView desScaleIV;
        MyTextView desOneTextV;
        MyTextView desScaleTextV;

        ScaleTopHolder(@NonNull View itemView) {
            super(itemView);
            desOneIV = itemView.findViewById(R.id.rcv_scale_top_adapter_decOne_IV);
            desScaleIV = itemView.findViewById(R.id.rcv_scale_top_adapter_decTwoIV);
            desOneTextV = itemView.findViewById(R.id.rcv_scale_top_adapter_decOneTV);
            desScaleTextV = itemView.findViewById(R.id.rcv_scale_top_adapter_decTwoTV);

        }

        void bindTop(LocationPeople locationPeople) {
            desOneTextV.setText(locPeoPostOne.getName());


            Picasso.get()
                    .load(locationPeople.getOriginalPic())
                    .centerCrop()
                    .resize(1000, 1000)
                    .into(desOneIV);


            desScaleIV.setOnClickListener(v -> {
                if (onAddScaleClick != null) {
                    onAddScaleClick.OnAddScale();
                } else {
                    throw new IllegalArgumentException(" [ScaleAdapter] onAddScaleClick is null , setListener in Activity");
                }

            });
        }

        void bindScaleTop(LocationPeople locationPeople) {
            if (locationPeople != null) {
                desScaleTextV.setText(locPeoPostScale.getName());
                Picasso.get()
                        .load(locationPeople.getOriginalPic())
                        .centerCrop()
                        .resize(1000, 1000)
                        .into(desScaleIV);
            } else {
                desScaleTextV.setText("اضافه کردن برای مقایسه");
                Picasso.get()
                        .load(R.drawable.holder_banner)
                        .centerCrop()
                        .resize(1000, 1000)
                        .into(desScaleIV);
            }

        }
    }

    public interface OnAddScaleClick {
        void OnAddScale();
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }


}
