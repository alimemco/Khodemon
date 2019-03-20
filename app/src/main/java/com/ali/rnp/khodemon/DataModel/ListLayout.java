package com.ali.rnp.khodemon.DataModel;

import java.util.List;

public class ListLayout {
    private int id ;
    private String title;
    private String group;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
