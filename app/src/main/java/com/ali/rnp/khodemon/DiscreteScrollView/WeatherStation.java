package com.ali.rnp.khodemon.DiscreteScrollView;

import com.ali.rnp.khodemon.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yarolegovich on 08.03.2017.
 */

public class WeatherStation {


    public static WeatherStation get() {
        return new WeatherStation();
    }

    private WeatherStation() {
    }

    public List<Forecast> getForecasts() {
        return Arrays.asList(
                new Forecast("Pisa", R.drawable.logo_nafis_draw, "16", Weather.PARTLY_CLOUDY),
                new Forecast("Paris", R.drawable.holder_banner, "14", Weather.CLEAR),
                new Forecast("New York", R.drawable.logo_nafis_draw, "9", Weather.MOSTLY_CLOUDY),
                new Forecast("Rome", R.drawable.holder_banner, "18", Weather.PARTLY_CLOUDY),
                new Forecast("London", R.drawable.logo_nafis_draw, "6", Weather.PERIODIC_CLOUDS),
                new Forecast("Washington", R.drawable.holder_banner, "20", Weather.CLEAR));
    }
}