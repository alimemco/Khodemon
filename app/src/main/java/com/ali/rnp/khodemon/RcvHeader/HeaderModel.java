package com.ali.rnp.khodemon.RcvHeader;

import com.ali.rnp.khodemon.Views.Activities.MainActivity;

public class HeaderModel implements ListItem{
 
    private String header;
 
    public void setHeader(String header) {
        this.header = header;
    }
 
    @Override
    public boolean isHeader() {
        return true;
    }
 
    @Override
    public String getName() {
        return header;
    }
}