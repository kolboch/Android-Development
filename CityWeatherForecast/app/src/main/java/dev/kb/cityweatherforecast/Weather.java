package dev.kb.cityweatherforecast;

/**
 * Created by Karol on 2017-02-12.
 */

public class Weather {

    /* date for prediction */
    private long date;
    private double temperature;
    private double pressure;
    /* id for weather to determine weather type according to 'openweathermap' id's" */
    private int weatherID;

    public Weather(double temperature, double pressure, int weatherID, long date) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.weatherID = weatherID;
        this.date = date;
    }

    /**
     * get predicted temperature
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * get predicted atmospheric pressure
     */
    public double getPressure() {
        return pressure;
    }

    /**
     * get specified for this weather weatherID
     */
    public int getWeatherID() {
        return weatherID;
    }

    /**
     * get date
     */
    public long getDate() {
        return date;
    }
}
