package com.ali.rnp.khodemon.ExpandableItems;

import com.ali.rnp.khodemon.ExpandableTags.Grouping;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class ParentExp extends ExpandableGroup<ChildExp> {

    private int iconResId;

    public ParentExp(String title, List<ChildExp> items, int iconResId) {
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
