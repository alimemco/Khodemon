package com.ali.rnp.khodemon.Search;

import com.ali.rnp.khodemon.DataModel.LocationPeople;

import java.util.ArrayList;

public class GroupModel  extends ArrayList{

    private String title;
    //private ArrayList<ChildModel> items;
    private ArrayList<LocationPeople> items;

  /*  public GroupModel(String title, ArrayList<ChildModel> items) {
        this.title = title;
        this.items = items;
    }*/

    public GroupModel(String title, ArrayList<LocationPeople> items) {
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    ArrayList<LocationPeople> getItems() {
        return items;
    }

    public int getItemCount(){
        return items == null ? 0 : items.size();
    }
}
