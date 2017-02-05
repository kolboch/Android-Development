package com.example.android.quakereport;

import java.util.Date;

/**
 * Created by Karol on 2017-02-04.
 */

public class EarthquakeRecord {

    // magnitude of an earthquake
    private double magnitude;

    // city where earthquake occured
    private String city;

    // date of earthquake
    private Date date;

    /**
     *
     * @param magnitude is the magnitude of an earthquake
     * @param city is the name of the city where earthquake occured
     * @param date is date of an earthquake
     */
    public EarthquakeRecord(double magnitude, String city, Date date){
        this.magnitude = magnitude;
        this.city = city;
        this.date = date;
    }

    /**
     * get the magnitude
     */
    public double getMagnitude(){
        return magnitude;
    }

    /**
     * get the city of an earthquake
     */
    public String getCity(){
        return city;
    }

    /**
     * get the date of an earthquake
     */
    public Date getDate(){
        return date;
    }
}
