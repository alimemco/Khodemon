package com.ali.rnp.khodemon.DataModel;

public class PictureUpload {

    private int pic_id;
    private String pic_name;
    private String pic_address;
    private int is_original;


    public int getPic_id() {
        return pic_id;
    }

    public void setPic_id(int pic_id) {
        this.pic_id = pic_id;
    }

    public String getPic_address() {
        return pic_address;
    }

    public void setPic_address(String pic_address) {
        this.pic_address = pic_address;
    }

    public int getIs_original() {
        return is_original;
    }

    public void setIs_original(int is_original) {
        this.is_original = is_original;
    }

    public String getPic_name() {
        return pic_name;
    }

    public void setPic_name(String pic_name) {
        this.pic_name = pic_name;
    }
}
