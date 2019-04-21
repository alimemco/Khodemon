package com.ali.rnp.khodemon.ExpandableSingleItems;

import android.os.Parcel;

import com.thoughtbot.expandablecheckrecyclerview.models.SingleCheckExpandableGroup;

import java.util.List;

public class SingleCheckItemsExp  extends SingleCheckExpandableGroup {

    private int iconResId;
   // private String iconUrl;
  //  private boolean isUrl=false;


    public SingleCheckItemsExp(String title, List items, int iconResId) {
        super(title, items);
        this.iconResId = iconResId;
    }
/*
    public SingleCheckItemsExp(String title, List items, String iconUrl,boolean isUrl) {
        super(title, items);
        this.isUrl = isUrl;
        this.iconUrl = iconUrl;
    }
*/
    protected SingleCheckItemsExp(Parcel in) {
        super(in);
        iconResId = in.readInt();
    }

    public int getIconResId() {
        return iconResId;

    }
/*
    public String getIconUrl() {
        return iconUrl;
    }
*/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(iconResId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SingleCheckItemsExp> CREATOR = new Creator<SingleCheckItemsExp>() {
        @Override
        public SingleCheckItemsExp createFromParcel(Parcel in) {
            return new SingleCheckItemsExp(in);
        }

        @Override
        public SingleCheckItemsExp[] newArray(int size) {
            return new SingleCheckItemsExp[size];
        }
    };
/*
    public boolean isUrl() {
        return isUrl;
    }*/
}
