package com.ali.rnp.khodemon.ExpandableTags;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Grouping extends ExpandableGroup<Expert> {

    private int iconResId;

    public Grouping(String title, List<Expert> items, int iconResId) {
        super(title, items);
        this.iconResId = iconResId;
    }

    public int getIconResId() {
        return iconResId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Grouping)) return false;

        Grouping tag = (Grouping) o;

        return getIconResId() == tag.getIconResId();

    }

    @Override
    public int hashCode() {
        return getIconResId();
    }
}