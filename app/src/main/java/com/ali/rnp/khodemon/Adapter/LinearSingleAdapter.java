package com.ali.rnp.khodemon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ali.rnp.khodemon.DataModel.ListLayout;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LinearSingleAdapter extends RecyclerView.Adapter<LinearSingleAdapter.LinearSingleAdapterHolder> {

    private List<ListLayout> listLayout;
    Context context;
    private static SingleItemAdapter singleItemAdapter;


    public LinearSingleAdapter(Context context){
        this.context = context;
    }

    public void setListDataForAdapter(List<ListLayout> listLayout) {
        this.listLayout = listLayout;
        notifyDataSetChanged();
    }




    @NonNull
    @Override
    public LinearSingleAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.recycler_view_home_items_linear_layout,parent,false);

        singleItemAdapter = new SingleItemAdapter(context);



        return new LinearSingleAdapterHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull LinearSingleAdapterHolder holder, int position) {


            holder.bindNormalLocation(listLayout.get(position));



            holder.itemView.setOnClickListener(v -> Toast.makeText(context, listLayout.get(position).getTitle(), Toast.LENGTH_SHORT).show());
            holder.recyclerViewSingle.setOnClickListener(v -> Toast.makeText(context, "", Toast.LENGTH_SHORT).show());


    }

    @Override
    public int getItemCount() {
        return listLayout.size();
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

        public void bindNormalLocation(final ListLayout listLayout) {

            nameTextView.setText(listLayout.getTitle());

                List<LocationPeople> locationPeopleList = listLayout.getLocationPeopleList();
                singleItemAdapter.setListDataForAdapter(locationPeopleList);
                recyclerViewSingle.setAdapter(singleItemAdapter);







        }
    }
}
