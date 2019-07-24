package com.ali.rnp.khodemon.DataModel;

import org.json.JSONObject;

public class Filter {

    private String title;
    private JSONObject jsonObject;
    private boolean selected;

    public Filter(String title, JSONObject jsonObject) {
        this.title = title;
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


}
