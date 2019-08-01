package com.ali.rnp.khodemon.DataModel;

public class CheckModel {

    private String title;

    private boolean checked;

    public CheckModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
