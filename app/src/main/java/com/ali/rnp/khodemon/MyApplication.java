package com.ali.rnp.khodemon;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class MyApplication extends Application {

    private static Typeface BYekan;
    private static Typeface iranSans;
    private static Typeface iranSansBold;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
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
}
