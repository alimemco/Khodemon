package com.ali.rnp.khodemon.DataModel;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    public static List<City> getListCity(){

        List<City> cities = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {

            City city = new City();

            city.setId(i);

            switch (i){
                case 1:
                    city.setCity("آبادان");
                    break;

                case 2:
                    city.setCity("ساوه");
                    break;

                case 3:
                    city.setCity("اهواز");
                    break;


                case 4:
                    city.setCity("کرج");
                    break;



                case 5:
                    city.setCity("شهرک بعثت");
                    break;


                case 6:
                    city.setCity("خرمشهر");
                    break;


                case 7:
                    city.setCity("ماهشهر");
                    break;

                case 8:
                    city.setCity("مشهد");
                    break;

                case 9:
                    city.setCity("شیراز");
                    break;


                case 10:
                    city.setCity("اصفهان");
                    break;


                case 11:
                    city.setCity("همدان");
                    break;

                case 12:
                    city.setCity("بوشهر");
                    break;

                case 13:
                    city.setCity("اراک");
                    break;


                case 14:
                    city.setCity("شادگان");
                    break;

                case 15:
                    city.setCity("سمنان");
                    break;

                case 16:
                    city.setCity("شهرکرد");
                    break;

                case 17:
                    city.setCity("شوشتر");
                    break;

                case 18:
                    city.setCity("کرمان");
                    break;


                case 19:
                    city.setCity("یزد");
                    break;


                case 20:
                    city.setCity("تهران");
                    break;

            }

            cities.add(city);
        }
        return cities;
    }
}
