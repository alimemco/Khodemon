package com.ali.rnp.khodemon.DataModel;

public class HourDays {

    private String dayName;
    private boolean isOpen;
    private int positionAdapter;
    private String hourFromOne;
    private String hourToOne;
    private String hourFromSec;
    private String hourToSec;

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getHourFromOne() {
        return hourFromOne;
    }

    public void setHourFromOne(String hourFromOne) {
        this.hourFromOne = hourFromOne;
    }

    public String getHourToOne() {
        return hourToOne;
    }

    public void setHourToOne(String hourToOne) {
        this.hourToOne = hourToOne;
    }

    public String getHourFromSec() {
        return hourFromSec;
    }

    public void setHourFromSec(String hourFromSec) {
        this.hourFromSec = hourFromSec;
    }

    public String getHourToSec() {
        return hourToSec;
    }

    public void setHourToSec(String hourToSec) {
        this.hourToSec = hourToSec;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getPositionAdapter() {
        return positionAdapter;
    }

    public void setPositionAdapter(int positionAdapter) {
        this.positionAdapter = positionAdapter;
    }
}
