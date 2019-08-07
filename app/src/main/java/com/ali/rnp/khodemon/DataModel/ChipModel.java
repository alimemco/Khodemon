package com.ali.rnp.khodemon.DataModel;

import java.io.Serializable;

public class ChipModel implements Serializable {

    private String key;
    private String title;

    public ChipModel(String key, String title) {
        this.key = key;
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }
}
