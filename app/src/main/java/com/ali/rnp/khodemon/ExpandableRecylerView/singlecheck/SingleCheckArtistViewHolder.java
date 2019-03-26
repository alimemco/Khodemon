package com.ali.rnp.khodemon.ExpandableRecylerView.singlecheck;

import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;

import com.ali.rnp.khodemon.R;
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;


public class SingleCheckArtistViewHolder extends CheckableChildViewHolder {

  private CheckedTextView childCheckedTextView;


  public SingleCheckArtistViewHolder(View itemView) {
    super(itemView);
    childCheckedTextView = itemView.findViewById(R.id.list_item_singlecheck_artist_name);



  }

  @Override
  public Checkable getCheckable() {
    return childCheckedTextView;
  }


  public void setArtistName(String artistName) {

    childCheckedTextView.setText(artistName);
  }



}
