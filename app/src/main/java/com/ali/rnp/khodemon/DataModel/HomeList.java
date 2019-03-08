package com.ali.rnp.khodemon.DataModel;

import java.util.List;

public class HomeList {
    private int id ;
    private String title;
    private List<LocationPeople> locationPeopleList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<LocationPeople> getLocationPeopleList() {
        return locationPeopleList;
    }

    public void setLocationPeopleList(List<LocationPeople> locationPeopleList) {
        this.locationPeopleList = locationPeopleList;
    }
}
