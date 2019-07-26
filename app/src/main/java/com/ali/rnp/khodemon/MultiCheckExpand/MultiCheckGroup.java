package com.ali.rnp.khodemon.MultiCheckExpand;

import com.thoughtbot.expandablecheckrecyclerview.models.MultiCheckExpandableGroup;

import java.util.List;

public class MultiCheckGroup extends MultiCheckExpandableGroup {

    private int iconResId;

    public MultiCheckGroup(String title, List items, int iconResId) {
        super(title, items);
        this.iconResId = iconResId;
    }

    public int getIconResId() {
        return iconResId;
    }


}