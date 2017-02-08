package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Karol on 2017-02-08.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<EarthquakeRecord>> {

    private String[]urls;

    public EarthquakeLoader(Context context, String[]urls){
        super(context);
        this.urls = urls;
    }

    @Override
    public List<EarthquakeRecord> loadInBackground() {
        Log.i("Loader", "loadInBackground called");
        List<EarthquakeRecord> earthquakes = null;
        if (urls.length != 0 && urls[0] != null) {
            earthquakes = QueryUtils.fetchEarthquakes(urls[0]);
        }
        return earthquakes;
    }

    @Override
    protected void onStartLoading(){
        Log.i("Loader", "onStartLoading() called");
        super.onStartLoading();
        forceLoad();
    }
}
