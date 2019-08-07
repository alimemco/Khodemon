package com.ali.rnp.khodemon.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Filter implements Parcelable {

    private int position;
    private String title;
    private JSONObject jsonObject;
    private String tag;
    private boolean selected;
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
    private ArrayList<ChipModel> filtered;

    public Filter(int position, String title, String tag, JSONObject jsonObject) {
        this.position = position;
        this.title = title;
        this.tag = tag;
        this.jsonObject = jsonObject;

    }

    protected Filter(Parcel in) {
        position = in.readInt();
        title = in.readString();
        try {
            jsonObject = in.readByte() == 0x00 ? null : new JSONObject(in.readString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tag = in.readString();
        selected = in.readByte() != 0x00;
        if (in.readByte() == 0x01) {
            filtered = new ArrayList<ChipModel>();
            in.readList(filtered, ChipModel.class.getClassLoader());
        } else {
            filtered = null;
        }
    }

    public String getTitle() {
        return title;
    }

    public int getPosition() {
        return position;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getTag() {
        return tag;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isSelected() {
        return selected;
    }

    public ArrayList<ChipModel> getFiltered() {
        return filtered;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setFiltered(ArrayList<ChipModel> filtered) {
        this.filtered = filtered;
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
        dest.writeByte((byte) (selected ? 0x01 : 0x00));
        if (filtered == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(filtered);
        }
    }
}