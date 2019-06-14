package com.ali.rnp.khodemon.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ali.rnp.khodemon.DataModel.Info;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.UtilsApp.UtilsApp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.internal.Util;

public class ScaleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Info> infoListOne;
    private ArrayList<Info> infoListScale;
    private ArrayList<Info> infoTitle;
    private LocationPeople locPeoPostOne;
    private LocationPeople locPeoPostScale;
    private String GROUP_NAME;
    private int VIEW_TYPE_SCALE_TOP = 0;
    private int VIEW_TYPE_SCALE_NORMAL = 1;

    private OnAddScaleClick onAddScaleClick;
    private Context context;

    private static final String TAG = "ScaleAdapter";


    public ScaleAdapter(ArrayList<Info> infoListOne, LocationPeople locPeoPost) {
        this.infoTitle = infoListOne;

        this.locPeoPostOne = locPeoPost;
        this.GROUP_NAME = locPeoPost.getGroup();

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        for (int i = 0; i < infoListOne.size(); i++) {
           infoListOne.get(i).setIcon(0);
        }
        this.infoListOne = infoListOne;

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

            mHolder.bind(mHolder, infoListOne,position-1);
            mHolder.bindTitle(mHolder,infoTitle.get(position-1));
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
        return infoTitle.size() + 1;
    }

    class ScaleHolder extends RecyclerView.ViewHolder  {
        MyTextView titleTV;
        MyTextView desOneTV;
        MyTextView desScaleTV;
         ImageView desOneImageView;
        ImageView desScaleImageView;

        ScaleHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.rcv_scale_adapter_title);
            desOneTV = itemView.findViewById(R.id.rcv_scale_adapter_decOne);
            desScaleTV = itemView.findViewById(R.id.rcv_scale_adapter_decTwo);
             desOneImageView = itemView.findViewById(R.id.rcv_scale_adapter_decOneImageView);
             desScaleImageView = itemView.findViewById(R.id.rcv_scale_adapter_decTwoImageView);


        }

        void bindTitle(ScaleHolder holder, Info info) {

            holder.titleTV.setText(info.getSubject());

            if (GROUP_NAME.equals(ProvidersApp.GROUP_NAME_LOCATION)) {

                UtilsApp.validate.validateInfo(info,ProvidersApp.GROUP_NAME_LOCATION,true);

            } else {

                UtilsApp.validate.validateInfo(info,ProvidersApp.GROUP_NAME_PEOPLE,true);


            }

            holder.titleTV.setText(info.getSubject());

        }

        void bind(ScaleHolder holder, ArrayList<Info> infoList , int position) {
           // validateInfo(info,holder.desOneImageView,holder.desOneTV);
            if (infoList!= null){
                Info info = infoListOne.get(position);
                Info infoPrs = UtilsApp.validate.validateInfo(info,locPeoPostOne.getGroup(),true);
                ScaleAdapter.this.infoListOne.set(position,infoPrs);

                if (info.isBoolean()){
                    holder.desOneTV.setText("");
                     holder.desOneImageView.setImageResource(info.getIcon());
                }else {
                    holder.desOneTV.setText(info.getDescription());
                          holder.desOneImageView.setImageResource(0);



                }
            }

        }


        void bindScale(ScaleHolder holder, Info info) {

            UtilsApp.validate.validateInfo(info,locPeoPostOne.getGroup(),true);

            if (info.isBoolean()){

                holder.desScaleTV.setText("");
                holder.desScaleImageView.setImageResource(info.getIcon());
            }else {
                holder.desScaleTV.setText(info.getDescription());
                holder.desScaleImageView.setImageResource(0);

            }

        }


    }



    class ScaleTopHolder extends RecyclerView.ViewHolder {
        ImageView OneImageView;
        ImageView ScaleImageView;
        MyTextView nameOneTextView;
        MyTextView nameScaleTextView;
        MyTextView tagOneTextView;
        MyTextView tagScaleTextView;

        ScaleTopHolder(@NonNull View itemView) {
            super(itemView);
            OneImageView = itemView.findViewById(R.id.rcv_scale_top_adapter_decOne_IV);
            ScaleImageView = itemView.findViewById(R.id.rcv_scale_top_adapter_decTwoIV);
            nameOneTextView = itemView.findViewById(R.id.rcv_scale_top_adapter_decOneTV);
            nameScaleTextView = itemView.findViewById(R.id.rcv_scale_top_adapter_decTwoTV);
            tagOneTextView = itemView.findViewById(R.id.rcv_scale_top_adapter_decOneTagTv);
            tagScaleTextView = itemView.findViewById(R.id.rcv_scale_top_adapter_decScaleTagTv);

        }

        void bindTop(LocationPeople locationPeople) {
            nameOneTextView.setText(locationPeople.getName());
            tagOneTextView.setText(locationPeople.getTag());

            if (locationPeople.getOriginalPic() != null) {
                Picasso.get()
                        .load(locationPeople.getOriginalPic())
                        .centerCrop()
                        .resize(1000, 1000)
                        .into(OneImageView);
            }


            ScaleImageView.setOnClickListener(v -> {
                if (onAddScaleClick != null) {
                    onAddScaleClick.OnAddScale();
                } else {
                    throw new IllegalArgumentException(" [ScaleAdapter] onAddScaleClick is null , setListener in Activity");
                }

            });
        }

        void bindScaleTop(LocationPeople locationPeople) {
            if (locationPeople != null) {
                nameScaleTextView.setText(locationPeople.getName());
                tagScaleTextView.setText(locationPeople.getTag());


                if (!locationPeople.getOriginalPic().equals("")){
                    Picasso.get()
                            .load(locationPeople.getOriginalPic())
                            .centerCrop()
                            .resize(1000, 1000)
                            .into(ScaleImageView);
                }

            } else {
                nameScaleTextView.setText("اضافه کردن برای مقایسه");
                Picasso.get()
                        .load(R.drawable.add_scale)
                        .centerCrop()
                        .resize(1000, 1000)
                        .into(ScaleImageView);
            }

        }
    }

    public interface OnAddScaleClick {
        void OnAddScale();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.context = recyclerView.getContext() ;
    }

    public int convertDpToPx(Context context, int dp) {
        return (int)( dp * context.getResources().getDisplayMetrics().density);
    }

    public  int dpToPx(int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }
}
