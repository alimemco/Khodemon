package com.ali.rnp.khodemon.UtilsApp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.ali.rnp.khodemon.DataModel.Info;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.R;
import com.google.android.gms.common.util.NumberUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.StringTokenizer;

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


    private static void validateDescription(Info info) {
        String description = info.getDescription();
        if (description == null || description.equals("") || description.equals("0")) {
            info.setDescription("-");
        }
    }

    public static String statusCodeToError(int statusCode, String error) {
        String msg = "";
        switch (statusCode) {
            case ProvidersApp.STATUS_CODE_VOLLEY_ERROR:
                msg = "VOLLEY_ERROR | " + error;
                break;
            case ProvidersApp.STATUS_CODE_JSON_EXCEPTION_ERROR:
                msg = "JSON_EXCEPTION_ERROR | " + error;
                break;
            case ProvidersApp.STATUS_CODE_SERVER_ERROR:
                msg = "SERVER_ERROR | " + error;
                break;
            case ProvidersApp.STATUS_CODE_SERVER_MISSING_ERROR:
                msg = "SERVER_MISSING_ERROR | " + error;
                break;

            case ProvidersApp.STATUS_CODE_SUCCESSFULLY:
                msg = "SUCCESSFULLY";
                break;
        }
        return msg;
    }

    public static int getMax(int[] numbers){
        int max = numbers[0];
        for (int number : numbers) {
            max = Math.max(max, number);
        }

        return max;
    }

    public static class validate {

        public static Info validateInfo(Info info, String group, boolean isScale) {

            validateBySubject(info, group,isScale);

            validateEmptyInfo(info);

            if (isScale){
                validateByBoolean(info);
            }

            validateLargeDesInfo(info);

            return info;

        }

        private static void validateEmptyInfo(Info info) {
            String des = info.getDescription();

            if (des.equals("") || des.equals("0")){
                info.setDescription("---");
            }
        }

        private static void validateLargeDesInfo(Info info) {

            String data = info.getDescription();
            String[] items = data.split(",");
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < items.length; i++) {
                sb.append(items[i]);
                if(i+1 != items.length)
                sb.append("\n");
            }

            info.setDescription(sb.toString());

        }

        private static void validateBySubject(Info info, String group,boolean isScale) {

            if (!isScale){
                if (info.getDescription().equals("") || info.getDescription().equals("0")){
                    info.setVisible(false);
                }
            }

            String subject = info.getSubject();

            switch (subject) {

                case ProvidersApp.KEY_DIMENSIONS:

                    info.setSubject("مساحت مکان");
                    if (!isScale)
                    info.setIcon(R.drawable.ic_dimensions);
                    else
                        info.setIcon(0);

                    break;

                case ProvidersApp.KEY_WORK_EXPERIENCE:

                    String exp = UtilsApp.validate.validateWorkExperience(info.getDescription());
                    info.setDescription(exp);
                    info.setSubject("تجربه کاری");
                    if (!isScale)
                    info.setIcon(R.drawable.ic_work_experience);
                    else
                        info.setIcon(0);


                    break;

                case ProvidersApp.KEY_SINCE:

                    String since = UtilsApp.validate.validateSince(info.getDescription(), group);
                    info.setDescription(since);

                    if (group.equals(ProvidersApp.GROUP_NAME_LOCATION)) {
                        info.setSubject("سال تاسیس");
                        if (!isScale)
                        info.setIcon(R.drawable.ic_under_construction);
                        else
                            info.setIcon(0);
                    }


                    else if (group.equals(ProvidersApp.GROUP_NAME_PEOPLE)) {

                        info.setSubject("سن");
                        if (!isScale)
                        info.setIcon(R.drawable.ic_birthday_cake);
                        else
                            info.setIcon(0);
                    }


                    break;

                case ProvidersApp.KEY_IS_EVIDENCE:
                    info.setSubject("دارای مدرک");
                    if (!isScale)
                    info.setIcon(R.drawable.ic_diploma);
                    else
                        info.setIcon(0);
                    break;
                case ProvidersApp.KEY_IS_MEDAL:
                    info.setSubject("تایید شده");
                    if (!isScale)
                    info.setIcon(R.drawable.ic_verified);
                    else
                        info.setIcon(0);
                    break;

                case ProvidersApp.KEY_DEGREE_OF_EDUCATION:
                    info.setSubject("مدرک تحصیلی");
                    if (!isScale)
                    info.setIcon(R.drawable.ic_diploma);
                    else
                        info.setIcon(0);
                    break;

                case ProvidersApp.KEY_STUDY:
                    info.setSubject("رشته تحصیلی");
                    if (!isScale)
                        info.setIcon(R.drawable.ic_student);
                    else
                        info.setIcon(0);
                    break;

                case ProvidersApp.KEY_EXPERTS:
                    info.setSubject("تخصص ها");
                    if (!isScale)
                        info.setIcon(R.drawable.ic_skills);
                    else
                    info.setIcon(0);
                    break;

                case ProvidersApp.KEY_PHONE_NUMBER:
                    info.setSubject("تلفن تماس");
                    if (!isScale)
                    info.setIcon(R.drawable.ic_call_answer);
                    else
                        info.setIcon(0);

                    break;

            }

        }

        private static void validateByBoolean(Info info) {

            String description = info.getDescription();

            if (info.isBoolean()) {
                info.setDescription("");
                if (description.equals("true")) {
                    info.setIcon(R.drawable.ic_true_circle);
                } else  {
                    info.setIcon(R.drawable.ic_false_circle);

                }

            } else {
                if (description.equals("0") || description.equals("")) {
                    info.setDescription("---");
                }
            }

        }

        private static String validateWorkExperience(String workExperience) {
            if (isNumber(workExperience)) {
                int number = Integer.parseInt(workExperience);
                float year = (float)(number / 12) ;

                if (year <= 0.5 ){
                    workExperience = "کمتر از 6 ماه";
                }else if ( year >= 0.5 && year <= 1){
                    workExperience = "کمتر از 1 سال";
                }else if (year > 1){

                    workExperience =(int)year+" سال";
                }
/*
                if (0 <= number && number <= 6) {
                    workExperience = "کمتر از 6 ماه";
                } else if (7 <= number && number <= 12) {
                    workExperience = "کمتر از 1 سال";
                } else if (13 <= number && number <= 24) {
                    workExperience = "بیش از 1 سال";
                } else if (25 <= number && number <= 36) {
                    workExperience = "بیش از 2 سال";
                } else {
                    workExperience = "بیش از 3 سال";
                }
                */
            }


            return workExperience;

        }

        private static String validateSince(String since, String group) {
            if (group.equals(ProvidersApp.GROUP_NAME_LOCATION)) {
                if(isNumber(since)){
                            if(Integer.parseInt(since) == 0){
                                return "---";
                            }
                }
                return " سال " + since;
            } else if (group.equals(ProvidersApp.GROUP_NAME_PEOPLE)) {
                int ageInt = 0;
                if (isNumber(since)) {
                    int userYear = Integer.parseInt(since);
                    if (userYear == 0){
                        return "---";
                    }
                    int cYear = Calendar.getInstance().get(Calendar.YEAR);
                    ageInt = (cYear - userYear) - 621;
                }

                String age = String.valueOf(ageInt) + " سال ";
                return age;
            } else {
                return since;
            }


        }

        public static boolean isNumber(String string) {
            try {
                Double.parseDouble(string);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }


    }

}
