package com.ali.rnp.khodemon;

import android.content.Context;
import android.content.SharedPreferences;

import com.ali.rnp.khodemon.DataModel.DataGenerator;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.DataModel.User;


public class SharedPrefManager {

    private static String SHARED_PREF_CITY_ID = "city_id";
    private static String SHARED_PREF_CITY_NAME = "city_name";
    private static String SHARED_PREF_CITY_PROVINCE_NAME = "city_province_name";

    private SharedPreferences sharedPreferences;

    public SharedPrefManager(Context context) {
        String SHARED_PREF_NAME = "shared_pref_name";
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setSharedCity(LocationPeople locationPeople) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(SHARED_PREF_CITY_ID, locationPeople.getId());
        editor.putString(SHARED_PREF_CITY_NAME, locationPeople.getCity());
        editor.putString(SHARED_PREF_CITY_PROVINCE_NAME, locationPeople.getProvince());

        editor.apply();
    }

    public LocationPeople getSharedCity() {
        LocationPeople locationPeople = new LocationPeople();
        locationPeople.setId(sharedPreferences.getInt(SHARED_PREF_CITY_ID, 1));
        locationPeople.setCity(sharedPreferences.getString(SHARED_PREF_CITY_NAME, DataGenerator.TEHRAN_CITY));
        locationPeople.setProvince(sharedPreferences.getString(SHARED_PREF_CITY_PROVINCE_NAME, DataGenerator.KHUZESTAN_PROVINCE));
        return locationPeople;
    }

    public void addUser(User user){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ProvidersApp.KEY_USERNAME,user.getUserName());
        editor.apply();
    }

    public void logOutUser(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ProvidersApp.KEY_USERNAME,"");
        editor.apply();
    }

    public User getUser(){
        User user = new User();
        user.setUserName(sharedPreferences.getString(ProvidersApp.KEY_USERNAME,""));
        return user;
    }


}
