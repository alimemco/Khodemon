package com.ali.rnp.khodemon.DataModel;

public class Filter {

    private String title;
    private String address;

    public Filter(String title, String address) {
        this.title = title;
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }
}
