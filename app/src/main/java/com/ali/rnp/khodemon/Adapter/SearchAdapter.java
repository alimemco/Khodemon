package com.ali.rnp.khodemon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.Helper.StringHighlight;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.TestActivity;
import com.ali.rnp.khodemon.UtilsApp.UtilsApp;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int VIEW_TYPE_HEADER = 0 ;
    private static int VIEW_TYPE_CHILD = 1 ;
    ArrayList<LocationPeople> locationPeopleList;
    private String txt;
    Context context;

    public SearchAdapter() {
    }

    public void setData(ArrayList<LocationPeople> locationPeopleList, String txt) {
        this.locationPeopleList = locationPeopleList;
        this.txt = txt ;
        notifyDataSetChanged();
    }

    public void setIsEmpty() {
        this.locationPeopleList = null;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        return (position==0) ? VIEW_TYPE_HEADER : VIEW_TYPE_CHILD;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_parent, parent, false);
            return new HeaderHolder(view);

        }else  if (viewType == VIEW_TYPE_CHILD){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_search, parent, false);
            return new SearchHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_search, parent, false);
            return new SearchHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof SearchHolder) {
            SearchHolder mHolder = (SearchHolder) holder;
            mHolder.bindSearch(mHolder, position-1);
        }else if (holder instanceof HeaderHolder){
            HeaderHolder mHolder = (HeaderHolder) holder;
            mHolder.bindHeader();
        }

    }

    @Override
    public int getItemCount() {
        return locationPeopleList == null ? 0 : locationPeopleList.size()+1;
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
           // mHolder.nameTv.setText(locationPeople.getName());
            mHolder.nameTv.setText(StringHighlight.highlight(locationPeople.getName(), txt,
                    ContextCompat.getColor(context,R.color.searchDialogResultHighlightColor)));

                    mHolder.categoryTv.setText(locationPeople.getTag());

        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        MyTextView headerTitleTv;

         HeaderHolder(@NonNull View itemView) {
            super(itemView);
            headerTitleTv = itemView.findViewById(R.id. rcv_parent_title_tv);

        }

        void bindHeader(){
            int size = locationPeopleList == null ? 0 : locationPeopleList.size();
            String txt = size + " نتیجه ";
            headerTitleTv.setText(txt);
        }

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }
}
