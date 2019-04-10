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

    public static List<String> dayList(){

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
}
