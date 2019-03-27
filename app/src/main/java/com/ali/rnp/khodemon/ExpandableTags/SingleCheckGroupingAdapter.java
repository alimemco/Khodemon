package com.ali.rnp.khodemon.ExpandableTags;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.rnp.khodemon.R;
import com.thoughtbot.expandablecheckrecyclerview.CheckableChildRecyclerViewAdapter;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class SingleCheckGroupingAdapter extends CheckableChildRecyclerViewAdapter<GroupingViewHolder, SingleCheckExpertViewHolder> {


    private Context context;

    public SingleCheckGroupingAdapter(List<SingleCheckGroup> groups, Context context) {
        super(groups);
        this.context = context;
    }


    @Override
    public SingleCheckExpertViewHolder onCreateCheckChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_single_check_expert, parent, false);
        return new SingleCheckExpertViewHolder(view,context);
    }

    @Override
    public void onBindCheckChildViewHolder(SingleCheckExpertViewHolder holder, int position,
                                           CheckedExpandableGroup group, int childIndex) {
        final Expert expert = (Expert) group.getItems().get(childIndex);
        holder.setExpertName(expert.getName());


    }

    @Override
    public GroupingViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_grouping, parent, false);
        return new GroupingViewHolder(view);
    }

    @Override
    public void onBindGroupViewHolder(GroupingViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {
        holder.setGroupingTitle(group);
    }





}


