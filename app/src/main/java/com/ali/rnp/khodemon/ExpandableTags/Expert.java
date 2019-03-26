package com.ali.rnp.khodemon.ExpandableTags;

import android.os.Parcel;
import android.os.Parcelable;

public class Expert implements Parcelable {
    private String name;
    private boolean isFavorite;

    public Expert(String name, boolean isFavorite) {
        this.name = name;
        this.isFavorite = isFavorite;
    }

    protected Expert(Parcel in) {
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
        if (!(o instanceof Expert)) return false;

        Expert expert = (Expert) o;

        if (isFavorite() != expert.isFavorite()) return false;
        return getName() != null ? getName().equals(expert.getName()) : expert.getName() == null;

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

    public static final Creator<Expert> CREATOR = new Creator<Expert>() {
        @Override
        public Expert createFromParcel(Parcel in) {
            return new Expert(in);
        }

        @Override
        public Expert[] newArray(int size) {
            return new Expert[size];
        }
    };
}
