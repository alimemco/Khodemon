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
import com.ali.rnp.khodemon.UtilsApp.UtilsApp;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

public class InfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Info> infoList;
    private OnButtonAddClick onButtonAddClick;
    private String GROUP_NAME;

    private int VIEW_TYPE_TITLE = 0;
    private int VIEW_TYPE_INFO = 1;
    private int VIEW_TYPE_BTN_ADD = 2;

    public InfoAdapter(ArrayList<Info> infoList, String group) {

        //this.infoList = infoList;
        this.GROUP_NAME = group;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);


        for (int i = 0; i < infoList.size(); i++) {
            Info info = infoList.get(i) ;

            if (info.getDescription().equals("") || info.getDescription().equals("0")){
                infoList.remove(i);
            }
        }
        this.infoList = infoList;

    }


    public void setOnButtonAddClick(OnButtonAddClick onButtonAddClick) {
        this.onButtonAddClick = onButtonAddClick;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0)
            return VIEW_TYPE_TITLE;
        else if (position == infoList.size() + 1)
            return VIEW_TYPE_BTN_ADD;
        else
            return VIEW_TYPE_INFO;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_TITLE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_title_detail, parent, false);
            return new TitleHolder("اطلاعات", view);

        } else if (viewType == VIEW_TYPE_BTN_ADD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_button_add, parent, false);
            return new ButtonAddHolder("تغییر اطلاعات", view
                    , ProvidersApp.RECYCLER_VIEW_INFO, onButtonAddClick);

        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_location_detail, parent, false);
            return new DetailAdapterHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof DetailAdapterHolder) {
            DetailAdapterHolder mHolder = (DetailAdapterHolder) holder;
            Info info = infoList.get(position - 1) ;
            if (GROUP_NAME.equals(ProvidersApp.GROUP_NAME_LOCATION)) {
                UtilsApp.validate.validateInfo(info, ProvidersApp.GROUP_NAME_LOCATION, false);

            } else {
                UtilsApp.validate.validateInfo(info, ProvidersApp.GROUP_NAME_PEOPLE, false);
            }

                mHolder.bindDetail(mHolder, info);

        }


    }

    @Override
    public int getItemCount() {
        return infoList.isEmpty() ? 2 : infoList.size() + 2;
    }


    class DetailAdapterHolder extends RecyclerView.ViewHolder {
        MyTextView subjectTV;
        MyTextView descriptionTV;
        ImageView iconIV;
        ImageView iconDescriptionIV;
        ImageView arrowIV;

        DetailAdapterHolder(@NonNull View itemView) {
            super(itemView);
            iconIV = itemView.findViewById(R.id.recycler_view_location_detail_iconIV);
            iconDescriptionIV = itemView.findViewById(R.id.recycler_view_location_detail_iconDescriptionIV);
            subjectTV = itemView.findViewById(R.id.recycler_view_location_detail_subjectTV);
            descriptionTV = itemView.findViewById(R.id.recycler_view_location_detail_variableTV);
            arrowIV = itemView.findViewById(R.id.recycler_view_location_detail_imageView_arrow);
        }

        void bindDetail(DetailAdapterHolder holder, Info info) {
/*
            if (GROUP_NAME.equals(ProvidersApp.GROUP_NAME_LOCATION)) {
                UtilsApp.validate.validateInfo(info, ProvidersApp.GROUP_NAME_LOCATION, false);

            } else {
                UtilsApp.validate.validateInfo(info, ProvidersApp.GROUP_NAME_PEOPLE, false);
            }*/
            holder.subjectTV.setText(info.getSubject());
            holder.iconIV.setImageResource(info.getIcon());

            if (info.isBoolean()) {
                if (info.getDescription().equals("true")) {
                    holder.iconDescriptionIV.setImageResource(R.drawable.ic_validate_true);
                    holder.descriptionTV.setText("");
                } else if (info.getDescription().equals("false")) {
                    holder.iconDescriptionIV.setImageResource(R.drawable.ic_validate_false);
                    holder.descriptionTV.setText("");
                }else {

                    holder.iconDescriptionIV.setImageResource(0);
                    holder.descriptionTV.setText("-");

                }
            }else {

                    holder.iconDescriptionIV.setImageResource(0);
                    if (info.getDescription().equals("") || info.getDescription().equals("0")){
                        holder.descriptionTV.setText("---");
                    }else {
                        holder.descriptionTV.setText(info.getDescription());

                }


            }
        }
    }
}
