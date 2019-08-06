package com.ali.rnp.khodemon.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Filter implements Parcelable {

    private int position;
    private String title;
    private JSONObject jsonObject;
    private String tag;
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Filter> CREATOR = new Parcelable.Creator<Filter>() {
        @Override
        public Filter createFromParcel(Parcel in) {
            return new Filter(in);
        }

        @Override
        public Filter[] newArray(int size) {
            return new Filter[size];
        }
    };
    private boolean selected;
    private ArrayList<String> filtered;

    public Filter(int position, String title, String tag, JSONObject jsonObject) {
        this.position = position;
        this.title = title;
        this.tag = tag;
        this.jsonObject = jsonObject;

    }

    public String getTitle() {
        return title;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getTag() {
        return tag;
    }

    public int getPosition() {
        return position;
    }

    private List<Parcelable> stateList;

    protected Filter(Parcel in) {
        position = in.readInt();
        title = in.readString();
        try {
            jsonObject = in.readByte() == 0x00 ? null : new JSONObject(in.readString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tag = in.readString();
        if (in.readByte() == 0x01) {
            filtered = new ArrayList<String>();
            in.readList(filtered, String.class.getClassLoader());
        } else {
            filtered = null;
        }
        selected = in.readByte() != 0x00;
    }

    public ArrayList<String> getFiltered() {
        return filtered;
    }

    public void setFiltered(ArrayList<String> filtered) {
        this.filtered = filtered;
    }

    public List getStateList() {
        return stateList;
    }

    public void setStateList(List stateList) {
        this.stateList = stateList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(position);
        dest.writeString(title);
        if (jsonObject == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeString(jsonObject.toString());
        }
        dest.writeString(tag);
        if (filtered == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(filtered);
        }
        dest.writeByte((byte) (selected ? 0x01 : 0x00));
    }
}