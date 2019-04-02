package com.ali.rnp.khodemon.ExpandableItems;

import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;

import com.ali.rnp.khodemon.MyApplication;
import com.ali.rnp.khodemon.R;
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;

public class ChildViewHolderExp extends CheckableChildViewHolder {

    private CheckedTextView childCheckedTextView;
    private Context context;


    public ChildViewHolderExp(View itemView, Context context) {
        super(itemView);
        this.context = context;
        childCheckedTextView = itemView.findViewById(R.id.list_item_singleCheck_child_name);
        childCheckedTextView.setTypeface(MyApplication.getShpIranSansMoblie(context));


    }

    @Override
    public Checkable getCheckable() {
        return childCheckedTextView;
    }


    public void setExpertName(String expertName) {

        childCheckedTextView.setText(expertName);
    }
}
