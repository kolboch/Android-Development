package dev.kb.cityweatherforecast;

/**
 * Created by Karol on 2017-02-12.
 */

public class Weather {

    /* date for prediction */
    private long date;
    private double temperature;
    private double pressure;
    /* code for weather to determine weather type according to 'openweathermap' id's" */
    private int weatherCode;
    private int hour;

    public Weather(double temperature, double pressure, int weatherCode, long date, int hour) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.weatherCode = weatherCode;
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
     * get specified for this weather weatherCode
     */
    public int getWeatherCode() {
        return weatherCode;
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
