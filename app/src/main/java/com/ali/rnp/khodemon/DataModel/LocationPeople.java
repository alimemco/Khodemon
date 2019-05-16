package com.ali.rnp.khodemon.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

public class LocationPeople implements Parcelable {

    private int id;
    private String name;
    private String group;
    private String city;
    private String province;
    private String tag;
    private int work_experience;
    private String experts;
    private String ownerSeller;
    private String address;
    private String timeReg;

    private String originalPic;

    public LocationPeople() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getOriginalPic() {
        return originalPic;
    }

    public void setOriginalPic(String originalPic) {
        this.originalPic = originalPic;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getWork_experience() {
        return work_experience;
    }

    public void setWork_experience(int work_experience) {
        this.work_experience = work_experience;
    }

    public String getExperts() {
        return experts;
    }

    public void setExperts(String experts) {
        this.experts = experts;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwnerSeller() {
        return ownerSeller;
    }

    public void setOwnerSeller(String ownerSeller) {
        this.ownerSeller = ownerSeller;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTimeReg() {
        return timeReg;
    }

    public void setTimeReg(String timeReg) {
        this.timeReg = timeReg;
    }

    protected LocationPeople(Parcel in) {
        id = in.readInt();
        name = in.readString();
        group = in.readString();
        city = in.readString();
        province = in.readString();
        tag = in.readString();
        work_experience = in.readInt();
        experts = in.readString();
        ownerSeller = in.readString();
        address = in.readString();
        originalPic = in.readString();
        timeReg = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(group);
        dest.writeString(city);
        dest.writeString(province);
        dest.writeString(tag);
        dest.writeInt(work_experience);
        dest.writeString(experts);
        dest.writeString(ownerSeller);
        dest.writeString(address);
        dest.writeString(originalPic);
        dest.writeString(timeReg);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<LocationPeople> CREATOR = new Parcelable.Creator<LocationPeople>() {
        @Override
        public LocationPeople createFromParcel(Parcel in) {
            return new LocationPeople(in);
        }

        @Override
        public LocationPeople[] newArray(int size) {
            return new LocationPeople[size];
        }
    };


}