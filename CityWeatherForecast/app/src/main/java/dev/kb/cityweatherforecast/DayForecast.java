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

    public String getDate() {
        return date;
    }

    public String getDayName() {
        return dayName;
    }

    public Weather[] getWeathers() {
        return weathers;
    }

    public Weather getWeather(int index){
        if(index < weathers.length){
            return weathers[index];
        }else{
            return null;
        }
    }
}
