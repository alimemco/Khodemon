package com.ali.rnp.khodemon.ExpandableTags;

import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;

import com.ali.rnp.khodemon.R;
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;

public class SingleCheckExpertViewHolder extends CheckableChildViewHolder {

    private CheckedTextView childCheckedTextView;


    public SingleCheckExpertViewHolder(View itemView) {
        super(itemView);
        childCheckedTextView = itemView.findViewById(R.id.list_item_singlecheck_artist_name);



    }

    @Override
    public Checkable getCheckable() {
        return childCheckedTextView;
    }


    public void setExpertName(String expertName) {

        childCheckedTextView.setText(expertName);
    }
}
