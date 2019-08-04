package com.ali.rnp.khodemon.DataModel;

import org.json.JSONObject;

public class Filter {

    private int position;
    private String title;
    private JSONObject jsonObject;
    private String tag;
    private boolean selected;

    public Filter(int position, String title, String tag, JSONObject jsonObject) {
        this.position = position;
        this.title = title;
        this.tag = tag;
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

    public String getTag() {
        return tag;
    }

    public int getPosition() {
        return position;
    }
}
