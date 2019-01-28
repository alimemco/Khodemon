package com.ali.rnp.khodemon.DataModel;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    public static final String KHUZESTAN_PROVINCE="خوزستان";
    private static final String MARKAZI_PROVINCE="مرکزی";
    private static final String TEHRAN_PROVINCE="تهران";
    private static final String MASHHAD_PROVINCE="مشهد";
    private static final String SHIRAZ_PROVINCE="شیراز";
    private static final String ISFAHAN_PROVINCE="اصفهان";
    private static final String HAMEDAN_PROVINCE="همدان";
    private static final String BUSHEHR_PROVINCE="بوشهر";
    private static final String KERMAN_PROVINCE="کرمان";
    private static final String SHAHREKORD_PROVINCE="شهرکرد";
    private static final String YAZD_PROVINCE="یزد";

    public static final String BESAST_TOWN_CITY="شهرک بعثت";

    public static List<City> getListCity(){

        List<City> cities = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {

            City city = new City();

            city.setId(i);

            switch (i){
                case 1:
                    city.setCity("آبادان");
                    city.setProvince(KHUZESTAN_PROVINCE);
                    break;

                case 2:
                    city.setCity("ساوه");
                    city.setProvince(MARKAZI_PROVINCE);
                    break;

                case 3:
                    city.setCity("اهواز");
                    city.setProvince(KHUZESTAN_PROVINCE);
                    break;


                case 4:
                    city.setCity("کرج");
                    city.setProvince(TEHRAN_PROVINCE);
                    break;



                case 5:
                    city.setCity(BESAST_TOWN_CITY);
                    city.setProvince(KHUZESTAN_PROVINCE);
                    break;


                case 6:
                    city.setCity("خرمشهر");
                    city.setProvince(KHUZESTAN_PROVINCE);
                    break;


                case 7:
                    city.setCity("ماهشهر");
                    city.setProvince(KHUZESTAN_PROVINCE);
                    break;

                case 8:
                    city.setCity("مشهد");
                    city.setProvince(MASHHAD_PROVINCE);
                    break;

                case 9:
                    city.setCity("شیراز");
                    city.setProvince(SHIRAZ_PROVINCE);
                    break;


                case 10:
                    city.setCity("اصفهان");
                    city.setProvince(ISFAHAN_PROVINCE);
                    break;


                case 11:
                    city.setCity("همدان");
                    city.setProvince(HAMEDAN_PROVINCE);
                    break;

                case 12:
                    city.setCity("بوشهر");
                    city.setProvince(BUSHEHR_PROVINCE);
                    break;

                case 13:
                    city.setCity("اراک");
                    city.setProvince(MARKAZI_PROVINCE);
                    break;


                case 14:
                    city.setCity("شادگان");
                    city.setProvince(KHUZESTAN_PROVINCE);
                    break;

                case 15:
                    city.setCity("سمنان");
                    city.setProvince(KHUZESTAN_PROVINCE);
                    break;

                case 16:
                    city.setCity("شهرکرد");
                    city.setProvince(SHAHREKORD_PROVINCE);
                    break;

                case 17:
                    city.setCity("شوشتر");
                    city.setProvince(KHUZESTAN_PROVINCE);
                    break;

                case 18:
                    city.setCity("کرمان");
                    city.setProvince(KERMAN_PROVINCE);
                    break;


                case 19:
                    city.setCity("یزد");
                    city.setProvince(YAZD_PROVINCE);
                    break;


                case 20:
                    city.setCity("تهران");
                    city.setProvince(TEHRAN_PROVINCE);
                    break;

            }

            cities.add(city);
        }
        return cities;
    }
}
