package com.ali.rnp.khodemon.RcvHeader;

import com.ali.rnp.khodemon.Views.Activities.MainActivity;

public class ChildModel implements ListItem{

    private String child;

    public void setChild(String child) {
        this.child = child;
    }

    @Override
    public boolean isHeader() {
        return false;
    }

    @Override
    public String getName() {
        return child;
    }
}