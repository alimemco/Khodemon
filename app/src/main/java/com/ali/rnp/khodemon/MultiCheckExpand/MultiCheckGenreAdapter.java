package com.ali.rnp.khodemon.MultiCheckExpand;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.ExpandableSingleItems.ChildExp;
import com.ali.rnp.khodemon.ExpandableSingleItems.ParentViewHolderExp;
import com.ali.rnp.khodemon.R;
import com.thoughtbot.expandablecheckrecyclerview.CheckableChildRecyclerViewAdapter;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;


public class MultiCheckGenreAdapter extends
        CheckableChildRecyclerViewAdapter<ParentViewHolderExp, ChildViewHolderMultiExp> {

    public MultiCheckGenreAdapter(List<MultiCheckGroup> groups) {
        super(groups);
    }

    @Override
    public ChildViewHolderMultiExp onCreateCheckChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_child_multi_exp, parent, false);
        return new ChildViewHolderMultiExp(view, parent.getContext());
    }

    @Override
    public void onBindCheckChildViewHolder(ChildViewHolderMultiExp holder, int position,
                                           CheckedExpandableGroup group, int childIndex) {
        final ChildExp child = (ChildExp) group.getItems().get(childIndex);
        holder.setName(child.getName());
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