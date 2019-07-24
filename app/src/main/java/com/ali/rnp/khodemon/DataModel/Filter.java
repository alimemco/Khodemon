package com.ali.rnp.khodemon.DataModel;

public class Filter {

    private String title;
    private String address;
    private boolean selected;

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

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}
