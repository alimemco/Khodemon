package com.ali.rnp.khodemon.Search;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

public class ChildModel  {

    private int id;
    private String name;
    private String category;
    private String city;
    private String province;
    private int work_experience;
    private String experts;
    private String ownerSeller;
    private String address;
    private String timeReg;
    private String phone;
    private String dimen;
    private String since;
    private String originalPic;
    private int imageWidth;
    private int imageHeight;
    private String thumb_pic;


    public ChildModel(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public ChildModel(Builder builder) {

        this.id = builder.id;
        this.name = builder.name;
        this.category = builder.category;
        this.city = builder.city;
        this.originalPic = builder.originalPic;
        this.thumb_pic = builder.thumb_pic;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getOriginalPic() {
        return originalPic;
    }

    public String getThumb_pic() {
        return thumb_pic;
    }

    public String getCity() {
        return city;
    }


    public static class Builder {
        private int id;
        private String name;
        private String category;
        private String city;
        private String originalPic;
        private String thumb_pic;
        private String province;
        private String tag;
        private int work_experience;
        private String experts;
        private String ownerSeller;
        private String address;
        private String timeReg;
        private String phone;
        private String dimen;
        private String since;


        private int imageWidth;
        private int imageHeight;



        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setOriginalPic(String originalPic) {
            this.originalPic = originalPic;
            return this;
        }

        public Builder setThumb_pic(String thumb_pic) {
            this.thumb_pic = thumb_pic;
            return  this ;
        }

        public ChildModel create(){
            return new ChildModel(this);
        }
    }
}