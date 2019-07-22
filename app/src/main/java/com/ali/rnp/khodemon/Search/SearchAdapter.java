package com.ali.rnp.khodemon.Search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "SearchAdapter";
    private SearchList searchList;
    private OnChildClickListener onChildClickListener;
    private String typed;
    private boolean isEmpty;


    public SearchAdapter() {

    }
/*
    public void setData(ArrayList<GroupModel> groupList , boolean isEmpty) {
        this.searchList = new SearchList(groupList);
        this.isEmpty = isEmpty;
        notifyDataSetChanged();

    }*/


    public void setData(ArrayList<GroupModel> groupList) {
        this.searchList = new SearchList(groupList);
        this.isEmpty = false;
        notifyDataSetChanged();

    }

    public void isEmpty() {
        this.isEmpty = true;
        notifyDataSetChanged();
    }

    public void setTyped(String typed) {
        this.typed = typed;
        notifyDataSetChanged();

    }


    @Override
    public int getItemViewType(int position) {

        return isEmpty ? SearchListPosition.EMPTY : searchList.getUnflattenedPosition(position).type;

    }

    @Override
    public int getItemCount() {

        if (isEmpty) {
            return 1;
        } else {
            return searchList == null ? 0 : searchList.getItemCount();
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == SearchListPosition.GROUP) {
            View viewParent = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_parent, parent, false);
            return new SearchHolder.ParentHolder(viewParent);
        } else if (viewType == SearchListPosition.EMPTY) {
            View viewEmpty = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_search_empty, parent, false);
            return new SearchHolder.EmptyHolder(viewEmpty, parent.getContext());
        } else {
            View viewChild = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_child, parent, false);
            return new SearchHolder.ChildHolder(viewChild, parent.getContext());
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (!isEmpty) {
            SearchListPosition listPos = searchList.getUnflattenedPosition(position);

            GroupModel group = searchList.getGroup(listPos);
            switch (listPos.type) {
                case SearchListPosition.GROUP:
                    SearchHolder.ParentHolder mHolder = (SearchHolder.ParentHolder) holder;
                    mHolder.bind(group);


                    break;
                case SearchListPosition.CHILD:
                    ChildModel childModel = group.getItems().get(listPos.childPos);
                    SearchHolder.ChildHolder mHolderChild = (SearchHolder.ChildHolder) holder;


                    mHolderChild.bind(childModel, typed, isEmpty);


                    mHolderChild.itemView.setOnClickListener(v -> {
                        if (onChildClickListener != null) {
                            onChildClickListener.onChildClick(childModel);
                        }
                    });
                    break;
            }
        }

    }


    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        this.onChildClickListener = onChildClickListener;
    }

    public interface OnChildClickListener {
        void onChildClick(ChildModel childModel);
    }


}
