package dev.kb.cityweatherforecast;

import java.util.ArrayList;

/**
 * Created by Karol on 2017-02-12.
 */

public class DayForecast {

    /* name of day */
    private String dayName;
    /* array of weathers */
    private ArrayList<Weather> weathers;
    private static String cityName;
    private static String countryCode;

    public DayForecast(String dayName, ArrayList<Weather> weathers) {
        this.dayName = dayName;
        this.weathers = weathers;
    }

    /**
     * get dayName of this day
     */
    public String getDayName() {
        return dayName;
    }

    /**
     * get all weather forecast for this day as array
     */
    public ArrayList<Weather> getWeathers() {
        return weathers;
    }

    /**
     * set city name for all fetched forecasts
     */
    public static void setCityName(String name) {
        DayForecast.cityName = name;
    }

    /**
     * get city name for fetched forecasts data
     */
    public static String getCityName() {
        return DayForecast.cityName;
    }

    /**
     * set country code for all forecasts
     */
    public static void setCountryCode(String countryCode) {
        DayForecast.countryCode = countryCode;
    }

    /**
     * get country code of all fetched forecasts data
     */
    public static String getCountryCode() {
        return DayForecast.countryCode;
    }
}
