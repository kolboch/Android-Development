package dev.kb.cityweatherforecast;

/**
 * Created by Karol on 2017-02-12.
 */

public class DayForecast {
    /* date string */
    private String date;
    /* name of day */
    private String dayName;
    /* array of weathers */
    private Weather[] weathers;

    public DayForecast(String date, String dayName, Weather[] weathers) {
        this.date = date;
        this.dayName = dayName;
        this.weathers = weathers;
    }

    /**
     * get date of this day
     */
    public String getDate() {
        return date;
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
    public Weather[] getWeathers() {
        return weathers;
    }
}
