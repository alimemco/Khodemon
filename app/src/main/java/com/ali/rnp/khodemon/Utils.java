package com.ali.rnp.khodemon;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

import androidx.transition.Fade;
import androidx.transition.Slide;
import androidx.transition.TransitionManager;

public class Utils {

    public Utils(){

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

    public static void startAnimationViewsFade(final View rootLayout, final View... views) {
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

}
