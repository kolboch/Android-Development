package com.example.android.quakereport;

/**
 * Created by Karol on 2017-02-04.
 */

public class EarthquakeRecord {

    // magnitude of an earthquake
    private double magnitude;

    // place where earthquake occured
    private String place;

    // date of earthquake in unix time
    private long date;

    // url to details of this earhquake
    private String earthquakeURL;

    /**
     * @param magnitude is the magnitude of an earthquake
     * @param place     is the name of the place where earthquake occured
     * @param date      is date of an earthquake in unix time
     */
    public EarthquakeRecord(double magnitude, String place, long date, String earthquakeURL) {
        this.magnitude = magnitude;
        this.place = place;
        this.date = date;
        this.earthquakeURL = earthquakeURL;
    }

    /**
     * get the magnitude
     */
    public double getMagnitude() {
        return magnitude;
    }

    /**
     * get the place of an earthquake
     */
    public String getPlace() {
        return place;
    }

    /**
     * get the date of an earthquake in unix time
     */
    public long getDate() {
        return date;
    }

    /**
     * get url string of details of this earthquake
     */
    public String getEarthquakeURL() {
        return earthquakeURL;
    }
}
