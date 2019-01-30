package com.ali.rnp.khodemon;

import android.content.Context;
import android.content.SharedPreferences;

import com.ali.rnp.khodemon.DataModel.DataGenerator;
import com.ali.rnp.khodemon.DataModel.LocationCity;


public class SharedPrefManager {

    private static String SHARED_PREF_CITY_ID = "city_id";
    private static String SHARED_PREF_CITY_NAME = "city_name";
    private static String SHARED_PREF_CITY_PROVINCE_NAME = "city_province_name";

    private SharedPreferences sharedPreferences;

    public SharedPrefManager(Context context) {
        String SHARED_PREF_NAME = "shared_pref_name";
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setSharedCity(LocationCity locationCity) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(SHARED_PREF_CITY_ID, locationCity.getCityId());
        editor.putString(SHARED_PREF_CITY_NAME, locationCity.getCityName());
        editor.putString(SHARED_PREF_CITY_PROVINCE_NAME, locationCity.getCityProvinceName());

        editor.apply();
    }

    public LocationCity getSharedCity() {
        LocationCity locationCity = new LocationCity();
        locationCity.setCityId(sharedPreferences.getInt(SHARED_PREF_CITY_ID, 1));
        locationCity.setCityName(sharedPreferences.getString(SHARED_PREF_CITY_NAME, DataGenerator.BESAST_TOWN_CITY));
        locationCity.setCityProvinceName(sharedPreferences.getString(SHARED_PREF_CITY_PROVINCE_NAME, DataGenerator.KHUZESTAN_PROVINCE));
        return locationCity;
    }
}
