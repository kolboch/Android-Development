package dev.kb.cityweatherforecast;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by Karol on 2017-02-14.
 */

public class ForecastLoader extends AsyncTaskLoader<List<DayForecast>> {

    private String queryString;

    public ForecastLoader(String queryString, Context context){
        super(context);
        this.queryString = queryString;
    }

    @Override
    public List<DayForecast> loadInBackground() {
        List<DayForecast> forecast = null;
        if(!TextUtils.isEmpty(queryString)){
            forecast = QueryUtils.fetchForecasts(queryString, super.getContext());
        }
        return forecast;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
