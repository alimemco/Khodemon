package com.ali.rnp.khodemon.ExpandableSingleItems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.R;
import com.thoughtbot.expandablecheckrecyclerview.CheckableChildRecyclerViewAdapter;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class AdapterSingleExp extends CheckableChildRecyclerViewAdapter<ParentViewHolderExp,ChildViewHolderExp> {

    private Context context;

    private static final String TAG = "AdapterSingleExp";

    public AdapterSingleExp(List<SingleCheckItemsExp> groups, Context context) {
        super(groups);
        this.context = context;
    }


    @Override
    public ChildViewHolderExp onCreateCheckChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_child_exp, parent, false);
        return new ChildViewHolderExp(view,context);
    }

    @Override
    public void onBindCheckChildViewHolder(ChildViewHolderExp holder, int position,
                                           CheckedExpandableGroup group, int childIndex) {
        final ChildExp childExp = (ChildExp) group.getItems().get(childIndex);
        holder.setExpertName(childExp.getName());


    }

    @Override
    public ParentViewHolderExp onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_parent_exp, parent, false);
        return new ParentViewHolderExp(view);
    }

    @Override
    public void onBindGroupViewHolder(ParentViewHolderExp holder, int flatPosition,
                                      ExpandableGroup group) {
        holder.setGroupingTitle(group);


    }

}
