package com.ali.rnp.khodemon.Search;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "SearchAdapter";
    private SearchList searchList;
    private OnChildClickListener onChildClickListener;

    public SearchAdapter( ArrayList<GroupModel> groupList) {
        this.searchList = new SearchList(groupList);

    }


    @Override
    public int getItemViewType(int position) {

        return searchList.getUnflattenedPosition(position).type;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

     if (viewType == SearchListPosition.GROUP){
         View viewParent = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_parent,parent,false);
         return new SearchHolder.ParentHolder(viewParent);
     }else {
         View viewChild = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_child,parent,false);
         return new SearchHolder.ChildHolder(viewChild);
     }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

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
                mHolderChild.bind(childModel);

                mHolderChild.itemView.setOnClickListener(v -> {
                    if(onChildClickListener != null){
                        onChildClickListener.onChildClick(childModel);
                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        return searchList == null ? 0 : searchList.getItemCount();
    }


    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        this.onChildClickListener = onChildClickListener;
    }

    public interface OnChildClickListener{
        void onChildClick(ChildModel childModel);
    }
}
