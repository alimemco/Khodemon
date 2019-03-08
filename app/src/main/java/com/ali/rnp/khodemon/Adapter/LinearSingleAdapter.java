package com.ali.rnp.khodemon.Adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.Api.ApiService;
import com.ali.rnp.khodemon.DataModel.HomeList;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LinearSingleAdapter extends RecyclerView.Adapter<LinearSingleAdapter.LinearSingleAdapterHolder> {

    public List<LocationPeople> locationPeopleList;
    private List<HomeList> homeList;
    Context context;
    private static SingleItemAdapter singleItemAdapter;


    public LinearSingleAdapter(Context context){
        this.context = context;
    }

    public void setListDataForAdapter(List<HomeList> homeList) {
        this.homeList = homeList;
        notifyDataSetChanged();
    }

    public void setListDataForSingleAdapter(List<LocationPeople> locationPeopleList) {
        this.locationPeopleList = locationPeopleList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public LinearSingleAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.recycler_view_home_items_linear_layout,parent,false);

        singleItemAdapter = new SingleItemAdapter(context);

/*
        singleItemAdapter = new SingleItemAdapter(context);
        if (locationPeopleList != null ){
            singleItemAdapter.setListDataForAdapter(locationPeopleList);
        }else {
            Log.i("adapter", "onCreateViewHolder: Error");
        }

        */


        return new LinearSingleAdapterHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull LinearSingleAdapterHolder holder, int position) {


            holder.bindNormalLocation(homeList.get(position));



    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    public static class LinearSingleAdapterHolder extends RecyclerView.ViewHolder {

        MyTextView nameTextView ;
        MyTextView moreTextView ;
        RecyclerView recyclerViewSingle ;


        public LinearSingleAdapterHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.recyclerView_home_items_linear_layout_name_textView);
            moreTextView = itemView.findViewById(R.id.recyclerView_home_items_linear_layout_more_textView);
            recyclerViewSingle = itemView.findViewById(R.id.recyclerView_home_items_linear_layout_RecyclerView);
        recyclerViewSingle.setLayoutManager(new LinearLayoutManager(itemView.getContext(),RecyclerView.HORIZONTAL,false));

        }

        public void bindNormalLocation(final HomeList homeList) {

            nameTextView.setText(homeList.getTitle());

                List<LocationPeople> locationPeopleList = homeList.getLocationPeopleList();
                singleItemAdapter.setListDataForAdapter(locationPeopleList);
                recyclerViewSingle.setAdapter(singleItemAdapter);







        }
    }
}
