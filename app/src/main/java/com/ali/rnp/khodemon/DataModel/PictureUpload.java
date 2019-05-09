package com.ali.rnp.khodemon.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

public class PictureUpload implements Parcelable {

    private int pic_id;
    private String pic_name;
    private String pic_address;
    private boolean is_original;

    private int width;
    private int height;
    private String thumb_150;
    private String thumb_1000;


    public int getPic_id() {
        return pic_id;
    }

    public void setPic_id(int pic_id) {
        this.pic_id = pic_id;
    }

    public String getPic_address() {
        return pic_address;
    }

    public void setPic_address(String pic_address) {
        this.pic_address = pic_address;
    }

    public boolean getIs_original() {
        return is_original;
    }

    public void setIs_original(boolean is_original) {
        this.is_original = is_original;
    }

    public String getPic_name() {
        return pic_name;
    }

    public void setPic_name(String pic_name) {
        this.pic_name = pic_name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getThumb_150() {
        return thumb_150;
    }

    public void setThumb_150(String thumb_150) {
        this.thumb_150 = thumb_150;
    }

    public String getThumb_1000() {
        return thumb_1000;
    }

    public void setThumb_1000(String thumb_1000) {
        this.thumb_1000 = thumb_1000;
    }

    protected PictureUpload(Parcel in) {
        pic_id = in.readInt();
        pic_name = in.readString();
        pic_address = in.readString();
        is_original = in.readByte() != 0x00;
        width = in.readInt();
        height = in.readInt();
        thumb_150 = in.readString();
        thumb_1000 = in.readString();
    }

    public PictureUpload(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pic_id);
        dest.writeString(pic_name);
        dest.writeString(pic_address);
        dest.writeByte((byte) (is_original ? 0x01 : 0x00));
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(thumb_150);
        dest.writeString(thumb_1000);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PictureUpload> CREATOR = new Parcelable.Creator<PictureUpload>() {
        @Override
        public PictureUpload createFromParcel(Parcel in) {
            return new PictureUpload(in);
        }

        @Override
        public PictureUpload[] newArray(int size) {
            return new PictureUpload[size];
        }
    };
}