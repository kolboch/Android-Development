package dev.kb.cityweatherforecast;

/**
 * Created by Karol on 2017-02-12.
 */

public class Weather {

    // private int hour ??
    private double temperature;
    private double pressure;
    private int weatherID;

    public Weather(double temperature, double pressure, int weatherID){
        this.temperature = temperature;
        this.pressure = pressure;
        this.weatherID = weatherID;
    }

    public double getTemperature(){
        return temperature;
    }

    public double getPressure(){
        return pressure;
    }

    public int getWeatherID(){
        return weatherID;
    }
}
