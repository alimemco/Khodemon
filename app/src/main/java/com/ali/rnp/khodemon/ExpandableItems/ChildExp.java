package com.ali.rnp.khodemon.ExpandableItems;

import android.os.Parcel;
import android.os.Parcelable;

public class ChildExp implements Parcelable {
    private String name;
    private boolean isFavorite;


    public ChildExp(String name, boolean isFavorite) {
        this.name = name;
        this.isFavorite = isFavorite;
    }

    protected ChildExp(Parcel in) {
        name = in.readString();
    }

    public String getName() {
        return name;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChildExp)) return false;

        ChildExp childExp = (ChildExp) o;

        if (isFavorite() != childExp.isFavorite()) return false;
        return getName() != null ? getName().equals(childExp.getName()) : childExp.getName() == null;

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (isFavorite() ? 1 : 0);
        return result;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChildExp> CREATOR = new Creator<ChildExp>() {
        @Override
        public ChildExp createFromParcel(Parcel in) {
            return new ChildExp(in);
        }

        @Override
        public ChildExp[] newArray(int size) {
            return new ChildExp[size];
        }
    };
}
