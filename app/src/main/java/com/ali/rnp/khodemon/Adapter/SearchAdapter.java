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

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<LocationPeople> locationPeopleList;

    public SearchAdapter() {
    }

    public void setData(ArrayList<LocationPeople> locationPeopleList) {
        this.locationPeopleList = locationPeopleList;
        notifyDataSetChanged();
    }

    public void setIsEmpty() {
        this.locationPeopleList = null;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_search, parent, false);

        return new SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof SearchHolder) {
            SearchHolder mHolder = (SearchHolder) holder;
            mHolder.bindSearch(mHolder, position);
        }

    }

    @Override
    public int getItemCount() {
        return locationPeopleList == null ? 0 : locationPeopleList.size();
    }

    class SearchHolder extends RecyclerView.ViewHolder {

        CircleImageView imageView;
        MyTextView nameTv, categoryTv;

        SearchHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.rcv_search_imageView);
            nameTv = itemView.findViewById(R.id.rcv_search_nameTv);
            categoryTv = itemView.findViewById(R.id.rcv_search_categoryTv);
        }

        void bindSearch(SearchHolder mHolder, int position) {

            LocationPeople locationPeople = locationPeopleList.get(position);

            UtilsApp.getImage(locationPeople, mHolder.imageView);
            mHolder.nameTv.setText(locationPeople.getName());
            mHolder.categoryTv.setText(locationPeople.getTag());

        }
    }
}
