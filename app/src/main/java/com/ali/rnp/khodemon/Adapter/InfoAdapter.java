package com.ali.rnp.khodemon.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ali.rnp.khodemon.DataModel.Info;
import com.ali.rnp.khodemon.Holder.ButtonAddHolder;
import com.ali.rnp.khodemon.Holder.TitleHolder;
import com.ali.rnp.khodemon.Interface.OnButtonAddClick;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

public class InfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Info> infoList;
    private OnButtonAddClick onButtonAddClick;

    private int VIEW_TYPE_TITLE = 0;
    private int VIEW_TYPE_INFO = 1;
    private int VIEW_TYPE_BTN_ADD = 2;

    public InfoAdapter(ArrayList<Info> infoList) {

        this.infoList = infoList;

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }

    public void setOnButtonAddClick(OnButtonAddClick onButtonAddClick) {
        this.onButtonAddClick = onButtonAddClick;
    }

    @Override
    public int getItemViewType(int position) {
        // return super.getItemViewType(position);


        if (position == 0)
            return VIEW_TYPE_TITLE;
        else if(position == infoList.size()+1)
            return VIEW_TYPE_BTN_ADD;
        else
            return VIEW_TYPE_INFO;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_TITLE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_title_detail,parent,false);
            return new TitleHolder("اطلاعات",view);

        }else if(viewType == VIEW_TYPE_BTN_ADD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_button_add,parent,false);
            return new ButtonAddHolder("تغییر اطلاعات", view
                    ,ProvidersApp.RECYCLER_VIEW_INFO,onButtonAddClick);
            
        }  else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_location_detail, parent, false);
            return new DetailAdapterHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

         if (holder instanceof DetailAdapterHolder){
           DetailAdapterHolder mHolder = (DetailAdapterHolder) holder;
            mHolder.bindDetail(mHolder, infoList.get(position -1));
        }


    }

    @Override
    public int getItemCount() {
        return infoList.size() + 2;
    }


    class DetailAdapterHolder extends RecyclerView.ViewHolder {
        MyTextView subjectTV;
        MyTextView descriptionTV;
        ImageView iconIV;

        DetailAdapterHolder(@NonNull View itemView) {
            super(itemView);
            iconIV = itemView.findViewById(R.id.recycler_view_location_detail_iconIV);
            subjectTV = itemView.findViewById(R.id.recycler_view_location_detail_subjectTV);
            descriptionTV = itemView.findViewById(R.id.recycler_view_location_detail_variableTV);
        }

        void bindDetail(DetailAdapterHolder holder, Info info) {

            holder.subjectTV.setText(info.getSubject());
            holder.descriptionTV.setText(info.getDescription());

            holder.iconIV.setImageResource(info.getIcon());

        }
    }

   /* class DetailAdapterTitleHolder extends RecyclerView.ViewHolder {
        MyTextView titleTV;

         DetailAdapterTitleHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.recycler_view_title_detail_TV);

            titleTV.setText("مشخصات");
        }


    }*/
}
