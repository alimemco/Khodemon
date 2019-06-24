package com.ali.rnp.khodemon;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import androidx.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;

import androidx.multidex.MultiDex;
import io.fabric.sdk.android.Fabric;

public class MyApplication extends Application {

    private static Typeface BYekan;
    private static Typeface iranSans;
    private static Typeface iranSansBold;
    private static Typeface shp_iran_sans;
    private static Typeface shp_iran_sans_bold;
    private static Typeface shp_iran_sans_light;
    private static Typeface shp_iran_sans_mobile;
    private static Typeface shp_iran_sans_mobile_bold;
    private static Typeface shp_iran_sans_mobile_light;
    private static Typeface shp_material_drawer;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Typeface getBYekan(Context context) {
        if (BYekan == null) {
            BYekan = Typeface.createFromAsset(context.getAssets(), "fonts/byekan.ttf");
        }
        return BYekan;
    }

    public static Typeface getIranSans(Context context) {
        if (iranSans == null) {
            iranSans = Typeface.createFromAsset(context.getAssets(), "fonts/IRAN Sans.ttf");
        }
        return iranSans;
    }

    public static Typeface getIranSansBold(Context context) {
        if (iranSansBold == null) {
            iranSansBold = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansMobile.ttf");
        }
        return iranSansBold;
    }







    public static Typeface getShpIranSans(Context context){
        if (shp_iran_sans == null){
            shp_iran_sans = Typeface.createFromAsset(context.getAssets(),"fonts/shp/iran_sans.ttf");
        }
        return shp_iran_sans;
    }

    public static Typeface getShpIranSansBold(Context context){
        if (shp_iran_sans_bold == null){
            shp_iran_sans_bold = Typeface.createFromAsset(context.getAssets(),"fonts/shp/iran_sans_bold.ttf");
        }
        return shp_iran_sans_bold;
    }


    public static Typeface getShpIranSansLight(Context context){
        if (shp_iran_sans_light == null){
            shp_iran_sans_light = Typeface.createFromAsset(context.getAssets(),"fonts/shp/iran_sans_light.ttf");
        }
        return shp_iran_sans_light;
    }


    public static Typeface getShpIranSansMoblie(Context context){
        if (shp_iran_sans_mobile == null){
            shp_iran_sans_mobile = Typeface.createFromAsset(context.getAssets(),"fonts/shp/IRANSansMobile.ttf");
        }
        return shp_iran_sans_mobile;
    }


    public static Typeface getShpIranSansMobileBold(Context context){
        if (shp_iran_sans_mobile_bold == null){
            shp_iran_sans_mobile_bold = Typeface.createFromAsset(context.getAssets(),"fonts/shp/IRANSansMobile_Bold.ttf");
        }
        return shp_iran_sans_mobile_bold;
    }

    public static Typeface getShpIranSansMobileLight(Context context){
        if (shp_iran_sans_mobile_light == null){
            shp_iran_sans_mobile_light = Typeface.createFromAsset(context.getAssets(),"fonts/shp/IRANSansMobile_Light.ttf");
        }
        return shp_iran_sans_mobile_light;
    }


    public static Typeface getShpMaterialDrawer(Context context){
        if (shp_material_drawer == null){
            shp_material_drawer = Typeface.createFromAsset(context.getAssets(), "fonts/shp/material_drawer.ttf");
        }
        return shp_material_drawer;
    }


}
