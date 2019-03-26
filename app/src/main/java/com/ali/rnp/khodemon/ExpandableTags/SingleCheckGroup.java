package com.ali.rnp.khodemon.ExpandableTags;

import android.os.Parcel;

import com.thoughtbot.expandablecheckrecyclerview.models.SingleCheckExpandableGroup;

import java.util.List;

public class SingleCheckGroup extends SingleCheckExpandableGroup {

    private int iconResId;

    public SingleCheckGroup(String title, List items, int iconResId) {
        super(title, items);
        this.iconResId = iconResId;
    }

    protected SingleCheckGroup(Parcel in) {
        super(in);
        iconResId = in.readInt();
    }

    public int getIconResId() {
        return iconResId;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(iconResId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SingleCheckGroup> CREATOR = new Creator<SingleCheckGroup>() {
        @Override
        public SingleCheckGroup createFromParcel(Parcel in) {
            return new SingleCheckGroup(in);
        }

        @Override
        public SingleCheckGroup[] newArray(int size) {
            return new SingleCheckGroup[size];
        }
    };
}
