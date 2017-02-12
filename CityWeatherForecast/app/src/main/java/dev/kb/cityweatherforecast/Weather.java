package dev.kb.cityweatherforecast;

/**
 * Created by Karol on 2017-02-12.
 */

public class Weather {

    /* hour of taking measure */
    private String measureHour;
    private double temperature;
    private double pressure;
    /* id for weather to determine weather type according to 'openweathermap' id's" */
    private int weatherID;

    public Weather(double temperature, double pressure, int weatherID, String measureHour) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.weatherID = weatherID;
        this.measureHour = measureHour;
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
     * get hour
     */
    public String getMeasureHour() {
        return measureHour;
    }
}
