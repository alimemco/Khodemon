package com.ali.rnp.khodemon.Search;

public class ChildModel  {

    private int id;
    private String name;
    private String category;
    private String city;
    private String group;
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
    private String isAd;


    public ChildModel(Builder builder) {

        this.id = builder.id;
        this.name = builder.name;
        this.category = builder.category;
        this.city = builder.city;
        this.originalPic = builder.originalPic;
        this.thumb_pic = builder.thumb_pic;
        this.isAd = builder.isAd;
        this.group = builder.group;
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

    public String isAd() {
        return isAd;
    }

    public String getGroup() {
        return group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public static class Builder {
        private int id;
        private String name;
        private String category;
        private String city;
        private String group;
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
        private String isAd;


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


        public Builder setIsAd(String isAd) {
            this.isAd = isAd;
            return this ;
        }

        public Builder setGroup(String group) {
            this.group = group;
            return this;
        }

        public ChildModel create(){
            return new ChildModel(this);
        }
    }
}
