package com.ali.rnp.khodemon.Search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.rnp.khodemon.R;

import java.util.ArrayList;


public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private SearchList searchList;
    private OnChildClickListener onChildClickListener;
    private String typed;
    private static final int isDefault = 0;
    private static final int isEmpty = 1;
    private static final int isSearching = 2;
    private static final int isSuccess = 29;
    // private boolean isEmpty;
    // private boolean isSearching;
    // private int viewType;
    private int state = isDefault;


    public SearchAdapter() {
        state = isDefault;
    }

    public void setData(ArrayList<GroupModel> groupList) {
        this.searchList = new SearchList(groupList);

        state = isSuccess;

        notifyDataSetChanged();

    }

    public void isEmpty() {
        state = isEmpty;
        notifyDataSetChanged();
    }

    public void isSearching() {
        state = isSearching;
        notifyDataSetChanged();
    }

    public void setTyped(String typed) {
        this.typed = typed;
        notifyDataSetChanged();

    }


    @Override
    public int getItemViewType(int position) {

        switch (state) {
            case isEmpty:
                return SearchListPosition.EMPTY;

            case isSearching:
                return SearchListPosition.SEARCHING;

            case isSuccess:
                return searchList.getUnflattenedPosition(position).type;

            default:
                return isDefault;
        }



    }

    @Override
    public int getItemCount() {

        if (state == isEmpty || state == isSearching) {
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
            return new SearchHolder.EmptyHolder(viewEmpty);

        } else if (viewType == SearchListPosition.SEARCHING) {
            View viewEmpty = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_searching, parent, false);
            return new SearchHolder.EmptyHolder(viewEmpty);

        } else {
            View viewChild = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_child, parent, false);
            return new SearchHolder.ChildHolder(viewChild, parent.getContext());
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (state == isSuccess) {
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


                    mHolderChild.bind(childModel, typed, listPos.childPos == 0);

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
