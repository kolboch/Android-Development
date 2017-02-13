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
}
