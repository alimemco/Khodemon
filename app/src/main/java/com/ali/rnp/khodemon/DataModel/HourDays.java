package com.ali.rnp.khodemon.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

public class HourDays implements Parcelable {

    private String dayName;
    private boolean isOpen;
    private int positionAdapter;
    private String hourFromOne;
    private String hourToOne;
    private String hourFromSec;
    private String hourToSec;

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getHourFromOne() {
        return hourFromOne;
    }

    public void setHourFromOne(String hourFromOne) {
        this.hourFromOne = hourFromOne;
    }

    public String getHourToOne() {
        return hourToOne;
    }

    public void setHourToOne(String hourToOne) {
        this.hourToOne = hourToOne;
    }

    public String getHourFromSec() {
        return hourFromSec;
    }

    public void setHourFromSec(String hourFromSec) {
        this.hourFromSec = hourFromSec;
    }

    public String getHourToSec() {
        return hourToSec;
    }

    public void setHourToSec(String hourToSec) {
        this.hourToSec = hourToSec;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getPositionAdapter() {
        return positionAdapter;
    }

    public void setPositionAdapter(int positionAdapter) {
        this.positionAdapter = positionAdapter;
    }

    public HourDays(){

    }
    protected HourDays(Parcel in) {
        dayName = in.readString();
        isOpen = in.readByte() != 0x00;
        positionAdapter = in.readInt();
        hourFromOne = in.readString();
        hourToOne = in.readString();
        hourFromSec = in.readString();
        hourToSec = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dayName);
        dest.writeByte((byte) (isOpen ? 0x01 : 0x00));
        dest.writeInt(positionAdapter);
        dest.writeString(hourFromOne);
        dest.writeString(hourToOne);
        dest.writeString(hourFromSec);
        dest.writeString(hourToSec);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HourDays> CREATOR = new Parcelable.Creator<HourDays>() {
        @Override
        public HourDays createFromParcel(Parcel in) {
            return new HourDays(in);
        }

        @Override
        public HourDays[] newArray(int size) {
            return new HourDays[size];
        }
    };
}