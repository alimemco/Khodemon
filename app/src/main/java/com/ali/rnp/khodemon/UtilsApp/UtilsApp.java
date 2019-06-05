package com.ali.rnp.khodemon.UtilsApp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.ali.rnp.khodemon.DataModel.Info;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Random;

import androidx.transition.Fade;
import androidx.transition.Slide;
import androidx.transition.TransitionManager;

public class UtilsApp {

    public UtilsApp() {

    }

    public static boolean isConnectedToNetwork(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();

    }

    public static void startAnimationViewsSlide(final View rootLayout, final View... views) {
        for (View view : views) {
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
        }, 1);
    }

    public static void startAnimationViewsFadeVisible(final View rootLayout, final View... views) {
        for (View view : views) {
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
        }, 1);
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
        }, 1);
    }

    public static void animMoveViewVisible(View view) {
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


    public static int randomInteger(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public static float randomFloat(float min, float max) {


        Random r = new Random();
        float random = min + r.nextFloat() * (max - min);
        DecimalFormat decimalFormat = new DecimalFormat("#,#");
        return Float.valueOf(decimalFormat.format(random));

    }

    /*
        public static void getImage(String url, ImageView imgV,String imgTmbSmall, String imgTmbLarge, int imgWidth){
            if (!url.equals("")) {
                boolean isLarge;
                isLarge = imgWidth >= 1000;
                String IMG_ADDRESS ;

                IMG_ADDRESS = (isLarge) ? imgTmbLarge : url;


                Picasso.get()
                        .load(imgTmbSmall)
                        .placeholder(R.drawable.holder_banner)
                        .into(imgV, new Callback() {


                            @Override
                            public void onSuccess() {
                                Picasso.get()
                                        .load(IMG_ADDRESS)
                                        .placeholder(imgV.getDrawable())
                                        .into(imgV);

                            }

                            @Override
                            public void onError(Exception e) {

                            }

                        });

            }
        }
        */
    public static void getImage(LocationPeople locationPeople, ImageView imgV) {
        if (!locationPeople.getOriginalPic().equals("")) {
            boolean isLarge;
            isLarge = locationPeople.getImageWidth() >= 1000;
            String IMG_ADDRESS;

            IMG_ADDRESS = (isLarge) ?
                    locationPeople.getImageThumb1000()
                    : locationPeople.getOriginalPic();


            Picasso.get()
                    .load(locationPeople.getImageThumb150())
                    .placeholder(R.drawable.holder_banner)
                    .into(imgV, new Callback() {


                        @Override
                        public void onSuccess() {
                            Picasso.get()
                                    .load(IMG_ADDRESS)
                                    .resize(500, 500)
                                    .centerCrop()
                                    .placeholder(imgV.getDrawable())
                                    .into(imgV);

                            //imageView.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);

                        }

                        @Override
                        public void onError(Exception e) {

                        }

                    });

        }
    }

    public static void getImageWithoutResize(LocationPeople locationPeople, ImageView imgV) {
        if (!locationPeople.getOriginalPic().equals("")) {
            boolean isLarge;
            isLarge = locationPeople.getImageWidth() >= 1000;
            String IMG_ADDRESS;

            IMG_ADDRESS = (isLarge) ?
                    locationPeople.getImageThumb1000()
                    : locationPeople.getOriginalPic();


            Picasso.get()
                    .load(locationPeople.getImageThumb150())
                    .placeholder(R.drawable.holder_banner)
                    .into(imgV, new Callback() {


                        @Override
                        public void onSuccess() {
                            Picasso.get()
                                    .load(IMG_ADDRESS)
                                    .placeholder(imgV.getDrawable())
                                    .into(imgV);

                            //imageView.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);

                        }

                        @Override
                        public void onError(Exception e) {

                        }

                    });

        }
    }

    public static Info parseInfoLocation(Info info, boolean isScale) {

        validateDescription(info);

        switch (info.getSubject()) {
            case ProvidersApp.KEY_SINCE:
                info.setSubject("سال تاسیس");
                info.setIcon(R.drawable.ic_under_construction);
                break;

            case ProvidersApp.KEY_DIMENSIONS:
                info.setSubject("مساحت");
                info.setIcon(R.drawable.ic_dimensions);
                break;


            case ProvidersApp.KEY_PHONE_NUMBER:
                if (info.getDescription().equals("")) {
                    info.setDescription("وارد کنید..");
                }
                info.setSubject("تلفن تماس");
                info.setIcon(R.drawable.ic_phone);
                break;
        }
        return info;
    }

    public static Info parseInfoPeople(Info info,boolean isScale) {


        switch (info.getSubject()) {
            case ProvidersApp.KEY_SINCE:
                info.setSubject("سال تولد");
                info.setIcon(R.drawable.ic_under_construction);
                break;

            case ProvidersApp.KEY_WORK_EXPERIENCE:
                info.setSubject("تجربه کاری");
                info.setIcon(R.drawable.ic_dimensions);
                break;


            case ProvidersApp.KEY_DEGREE_OF_EDUCATION:
                info.setSubject("مدرک تحصیلی");
                info.setIcon(R.drawable.ic_phone);
                break;

            case ProvidersApp.KEY_IS_EVIDENCE:
                info.setSubject("دارای مدرک");
                if (isScale){
                    if (info.getDescription().equals("true"))
                        info.setIcon(android.R.drawable.stat_sys_download_done);
                    else
                        info.setIcon(android.R.drawable.stat_notify_error);
                }else {
                    info.setIcon(R.drawable.ic_heart);
                }

                break;
        }

       // validateDescription(info);
        return info;
    }

    public static Info parseInfoScale(Info info) {

        validateDescription(info);

        switch (info.getSubject()) {
            case ProvidersApp.KEY_SINCE:
                info.setSubject("سال تاسیس");
                info.setIcon(R.drawable.ic_under_construction);
                break;

            case ProvidersApp.KEY_DIMENSIONS:
                info.setSubject("مساحت");
                info.setIcon(R.drawable.ic_dimensions);
                break;


            case ProvidersApp.KEY_PHONE_NUMBER:
                if (info.getDescription().equals("")) {
                    info.setDescription("وارد کنید..");
                }
                info.setSubject("تلفن تماس");
                info.setIcon(R.drawable.ic_phone);
                break;
        }


        return info;
    }

    private static void validateDescription(Info info) {
        String description = info.getDescription();
        if (description == null || description.equals("") || description.equals("0")) {
            info.setDescription("-");
        }
    }

    public static String statusCodeToError(int statusCode , String error){
        String msg = "" ;
        switch (statusCode){
            case ProvidersApp.STATUS_CODE_VOLLEY_ERROR:
                msg = "VOLLEY_ERROR | " + error ;
                break;
            case ProvidersApp.STATUS_CODE_JSON_EXCEPTION_ERROR:
                msg = "JSON_EXCEPTION_ERROR | " + error ;
                break;
            case ProvidersApp.STATUS_CODE_SERVER_ERROR:
                msg = "SERVER_ERROR | " + error ;
                break;
            case ProvidersApp.STATUS_CODE_SERVER_MISSING_ERROR:
                msg = "SERVER_MISSING_ERROR | " + error ;
                break;


            case ProvidersApp.STATUS_CODE_SUCCESSFULLY:
                msg = "SUCCESSFULLY" ;
                break;
        }
        return msg;
    }



    public static class validate{

        public static Info validateInfo(Info info){

            validateBySubject(info);

            return info;

        }
        private static void validateBySubject(Info info) {

            info.setIcon(0);


            String subject = info.getSubject();

            switch (subject){
                case ProvidersApp.KEY_WORK_EXPERIENCE:
                    if (!info.getDescription().equals("-")){
                        String msg = UtilsApp.validate.validateWorkExperience(Integer.parseInt(info.getDescription()));
                        info.setDescription(msg);
                    }


                    break;

                case ProvidersApp.KEY_IS_EVIDENCE:



                     UtilsApp.validate.validateByBoolean(info);

                    break;

                case ProvidersApp.KEY_DEGREE_OF_EDUCATION:

                   // UtilsApp.validate.validateByDescription(info);

                    break;

                case ProvidersApp.KEY_PHONE_NUMBER:

                  //  UtilsApp.validate.validateByDescription(info);

                    break;


            }




        }
        private static void validateByDescription(Info info) {

            String description = info.getDescription();

            switch (description) {

                case "":
                case "0":
                    info.setDescription("-");
                    break;

            }


        }

        private static void validateByBoolean(Info info) {

            info.setBoolean(true);

            String description = info.getDescription();

            if (description.equals("true")){
                info.setIcon(R.drawable.ic_validate_true);
               // info.setDescription("true");
            }else {
                info.setIcon(R.drawable.ic_validate_false);
               // info.setDescription("false");
            }

        }

        private static String validateWorkExperience(int workExperience){

            String msg;
            if ( 0 <= workExperience && workExperience <= 6){
                msg = "کمتر از 6 ماه";
            } else if ( 7 <= workExperience && workExperience <= 12){
                msg = "کمتر از 1 سال";
            } else if ( 13 <= workExperience && workExperience <= 24){
                msg = "بیش از 1 سال";
            } else if ( 25 <= workExperience && workExperience <= 36){
                msg = "بیش از 2 سال";
            } else {
                msg = "بیش از 3 سال";
            }

            return msg;

        }

    }

}
