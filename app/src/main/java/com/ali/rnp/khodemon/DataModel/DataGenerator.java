package com.ali.rnp.khodemon.DataModel;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {
    /*

        private static final String MARKAZI_PROVINCE = "مرکزی";
        private static final String TEHRAN_PROVINCE = "تهران";
        private static final String MASHHAD_PROVINCE = "مشهد";
        private static final String SHIRAZ_PROVINCE = "شیراز";
        private static final String ISFAHAN_PROVINCE = "اصفهان";
        private static final String HAMEDAN_PROVINCE = "همدان";
        private static final String BUSHEHR_PROVINCE = "بوشهر";
        private static final String KERMAN_PROVINCE = "کرمان";
        private static final String SHAHREKORD_PROVINCE = "شهرکرد";
        private static final String YAZD_PROVINCE = "یزد";
        */
    public static final String KHUZESTAN_PROVINCE = "خوزستان";
    public static final String BESAST_TOWN_CITY = "شهرک بعثت";
/*
    public static List<City> getListCity() {

        List<City> cities = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {

            City city = new City();

            city.setId(i);

            switch (i) {
                case 1:
                    city.setCityName("آبادان");
                    city.setProvince(KHUZESTAN_PROVINCE);
                    break;

                case 2:
                    city.setCityName("ساوه");
                    city.setProvince(MARKAZI_PROVINCE);
                    break;

                case 3:
                    city.setCityName("اهواز");
                    city.setProvince(KHUZESTAN_PROVINCE);
                    break;


                case 4:
                    city.setCityName("کرج");
                    city.setProvince(TEHRAN_PROVINCE);
                    break;


                case 5:
                    city.setCityName(BESAST_TOWN_CITY);
                    city.setProvince(KHUZESTAN_PROVINCE);
                    break;


                case 6:
                    city.setCityName("خرمشهر");
                    city.setProvince(KHUZESTAN_PROVINCE);
                    break;


                case 7:
                    city.setCityName("ماهشهر");
                    city.setProvince(KHUZESTAN_PROVINCE);
                    break;

                case 8:
                    city.setCityName("مشهد");
                    city.setProvince(MASHHAD_PROVINCE);
                    break;

                case 9:
                    city.setCityName("شیراز");
                    city.setProvince(SHIRAZ_PROVINCE);
                    break;


                case 10:
                    city.setCityName("اصفهان");
                    city.setProvince(ISFAHAN_PROVINCE);
                    break;


                case 11:
                    city.setCityName("همدان");
                    city.setProvince(HAMEDAN_PROVINCE);
                    break;

                case 12:
                    city.setCityName("بوشهر");
                    city.setProvince(BUSHEHR_PROVINCE);
                    break;

                case 13:
                    city.setCityName("اراک");
                    city.setProvince(MARKAZI_PROVINCE);
                    break;


                case 14:
                    city.setCityName("شادگان");
                    city.setProvince(KHUZESTAN_PROVINCE);
                    break;

                case 15:
                    city.setCityName("سمنان");
                    city.setProvince(KHUZESTAN_PROVINCE);
                    break;

                case 16:
                    city.setCityName("شهرکرد");
                    city.setProvince(SHAHREKORD_PROVINCE);
                    break;

                case 17:
                    city.setCityName("شوشتر");
                    city.setProvince(KHUZESTAN_PROVINCE);
                    break;

                case 18:
                    city.setCityName("کرمان");
                    city.setProvince(KERMAN_PROVINCE);
                    break;


                case 19:
                    city.setCityName("یزد");
                    city.setProvince(YAZD_PROVINCE);
                    break;


                case 20:
                    city.setCityName("تهران");
                    city.setProvince(TEHRAN_PROVINCE);
                    break;

            }

            cities.add(city);
        }
        return cities;
    }
*/

    public static List<String> dayList() {

        List<String> dayList = new ArrayList<>();
        dayList.add("شنبه");
        dayList.add("یک شنبه");
        dayList.add("دو شنبه");
        dayList.add("سه شنبه");
        dayList.add("چهار شنبه");
        dayList.add("پنج شنبه");
        dayList.add("جمعه");

        return dayList;

    }

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
