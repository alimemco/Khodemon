package com.ali.rnp.khodemon.ExpandableSingleItems;

import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;

import com.ali.rnp.khodemon.MyApplication;
import com.ali.rnp.khodemon.R;
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;

public class ChildViewHolderExp extends CheckableChildViewHolder {

    private CheckedTextView childCheckedTextView;

    public ChildViewHolderExp(View itemView, Context context) {
        super(itemView);
        childCheckedTextView = itemView.findViewById(R.id.list_item_singleCheck_child_name);
        childCheckedTextView.setTypeface(MyApplication.getShpIranSansMoblie(context));
    }



    @Override
    public Checkable getCheckable() {
        return childCheckedTextView;
    }


    public void setName(String name) {

        childCheckedTextView.setText(name);
    }
}
