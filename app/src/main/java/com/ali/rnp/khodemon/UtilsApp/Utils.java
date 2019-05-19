package com.ali.rnp.khodemon.UtilsApp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

import java.text.DecimalFormat;
import java.util.Random;

import androidx.transition.Fade;
import androidx.transition.Slide;
import androidx.transition.TransitionManager;

public class Utils {

    public Utils(){

    }

    public static boolean isConnectedToNetwork(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null && networkInfo.isConnected();

    }
    public static void startAnimationViewsSlide(final View rootLayout, final View... views) {
        for (View view : views){
            view.setVisibility(View.INVISIBLE);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TransitionManager.beginDelayedTransition((ViewGroup) rootLayout, new Slide());

                for (View view : views) {
                    view.setVisibility(View.VISIBLE);
                }

            }
        },1);
    }

    public static void startAnimationViewsFadeVisible(final View rootLayout, final View... views) {
        for (View view : views){
            view.setVisibility(View.INVISIBLE);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TransitionManager.beginDelayedTransition((ViewGroup) rootLayout, new Fade());

                for (View view : views) {
                    view.setVisibility(View.VISIBLE);
                }

            }
        },1);
    }


    public static void startAnimationViewsFadeGone(final View rootLayout, final View... views) {

        //Handler handler = new Handler();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TransitionManager.beginDelayedTransition((ViewGroup) rootLayout, new Fade());

                for (View view : views) {
                    view.setVisibility(View.INVISIBLE);
                }

            }
        },1);
    }

    public static void animMoveViewVisible(View view){
        TranslateAnimation animation = new TranslateAnimation(
                0.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);



        animation.setDuration(1000);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(animation);

    }


    public static int randomInteger(int min, int max){
        Random random = new Random();
        return random.nextInt(max-min) + min;
    }

    public static float randomFloat(float min, float max){


        Random r = new Random();
        float random =  min + r.nextFloat() * (max - min) ;
        DecimalFormat decimalFormat = new DecimalFormat("#,#");
        return Float.valueOf(decimalFormat.format(random));

    }

}
