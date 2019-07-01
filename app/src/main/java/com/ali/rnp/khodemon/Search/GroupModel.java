package com.ali.rnp.khodemon.Search;

import java.util.ArrayList;
import java.util.List;

public class GroupModel  extends ArrayList{

    private String title;
    private ArrayList<ChildModel> items;

    public GroupModel(String title, ArrayList<ChildModel> items) {
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    ArrayList<ChildModel> getItems() {
        return items;
    }

    public int getItemCount(){
        return items == null ? 0 : items.size();
    }
}
