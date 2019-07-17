package com.ali.rnp.khodemon.Search;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

public class ChildModel  {

    String name;
    String category;




    public ChildModel(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }
}
