package dev.kb.cityweatherforecast;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class WeatherForecastMain extends AppCompatActivity implements LoaderCallbacks<List<DayForecast>> {

    private static final String LOG_TAG = WeatherForecastMain.class.getName();
    private ArrayList<DayForecast> forecasts;
    private ListView forecastsListView;
    private String queryString = "http://api.openweathermap.org/data/2.5/forecast?q=Paris,fr&appid=737490f14eb57485aa7928ce8e2c8a41";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_main);
        if(NetworkUtils.isInternetConnection(getApplicationContext())) {
            getLoaderManager().initLoader(0, null, this);
        }else{
            //TODO no Internet Connectivity Message
        }
    }

    @Override
    public Loader<List<DayForecast>> onCreateLoader(int i, Bundle bundle) {
        //TODO preferences and building query
        ForecastLoader loader = new ForecastLoader(queryString, getApplicationContext());
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<DayForecast>> loader, List<DayForecast> dayForecasts) {
        if (dayForecasts != null && !dayForecasts.isEmpty()) {
            updateUI(dayForecasts);
        } else {
            //display no data message
        }
    }

    @Override
    public void onLoaderReset(Loader<List<DayForecast>> loader) {
    }

    private void updateUI(List<DayForecast> forecast) {
        this.forecasts = (ArrayList) forecast;
        forecastsListView = (ListView) findViewById(R.id.list_day_forecasts);
        DayForecastAdapter adapter = new DayForecastAdapter(this, forecasts);
        forecastsListView.setAdapter(adapter);
    }
}
