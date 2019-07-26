package com.ali.rnp.khodemon.DataModel;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    public static final String KHUZESTAN_PROVINCE = "خوزستان";
    public static final String BESAST_TOWN_CITY = "شهرک بعثت";
    public static final String TEHRAN_CITY = "تهران";


    public static List<HourDays> hourDaysList() {

        List<HourDays> hourDaysList = new ArrayList<>();

        for (int i = 0; i < 7 ; i++) {
            HourDays hourDays = new HourDays();
            switch (i){
                case 0:
                    hourDays.setDayName("شنبه");
                    hourDays.setOpen(false);
                    break;
                case 1:
                    hourDays.setDayName("یک شنبه");
                    hourDays.setOpen(false);
                    break;

                case 2:
                    hourDays.setDayName("دو شنبه");
                    hourDays.setOpen(false);
                    break;

                case 3:
                    hourDays.setDayName("سه شنبه");
                    hourDays.setOpen(false);
                    break;

                case 4:
                    hourDays.setDayName("چهارشنبه");
                    hourDays.setOpen(false);
                    break;

                case 5:
                    hourDays.setDayName("پنجشنبه");
                    hourDays.setOpen(false);
                    break;

                case 6:
                    hourDays.setDayName("جمعه");
                    hourDays.setOpen(false);
                    break;
            }
            hourDaysList.add(hourDays);

        }

        return hourDaysList;

    }

    public static List<String> hoursFrom() {

        List<String> hours = new ArrayList<>();
        hours.add("از ساعت");
        hours.add("00:00");
        hours.add("00:30");
        hours.add("01:30");
        hours.add("02:00");
        hours.add("02:30");
        hours.add("03:00");
        hours.add("03:30");
        hours.add("04:00");
        hours.add("04:30");
        hours.add("05:00");
        hours.add("05:30");
        hours.add("06:00");
        hours.add("06:30");
        hours.add("07:00");
        hours.add("07:30");
        hours.add("08:00");
        hours.add("08:30");

        return hours;

    }

    public static List<String> hoursTo() {

        List<String> hours = new ArrayList<>();
        hours.add("تا ساعت");
        hours.add("00:00");
        hours.add("00:30");
        hours.add("01:30");
        hours.add("02:00");
        hours.add("02:30");
        hours.add("03:00");
        hours.add("03:30");
        hours.add("04:00");
        hours.add("04:30");
        hours.add("05:00");
        hours.add("05:30");
        hours.add("06:00");
        hours.add("06:30");
        hours.add("07:00");
        hours.add("07:30");
        hours.add("08:00");
        hours.add("08:30");

        return hours;

    }

    public static List<String> ownerSeller() {

        List<String> owner = new ArrayList<>();
        owner.add("نوع مالکیت");
        owner.add("مالک اصلی");
        owner.add("فروشنده");
        owner.add("سازنده");
        owner.add("بنیانگذار");
        owner.add("تاسیس کننده");


        return owner;

    }


}
