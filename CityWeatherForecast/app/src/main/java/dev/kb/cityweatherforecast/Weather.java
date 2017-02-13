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
    private int hour;

    public Weather(double temperature, double pressure, int weatherID, long date, int hour) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.weatherID = weatherID;
        this.date = date;
        this.hour = hour;
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

    /**
     * get hour for weather prediction
     */
    public int getHour() {
        return hour;
    }
}
