package dev.kb.cityweatherforecast;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WeatherForecastMain extends AppCompatActivity implements LoaderCallbacks<List<DayForecast>> {

    private static final String LOG_TAG = WeatherForecastMain.class.getName();
    private ArrayList<DayForecast> forecasts;
    private ListView forecastsListView;
    private TextView errorTextView;
    private String queryString = "http://api.openweathermap.org/data/2.5/forecast?q=Paris,fr&appid=737490f14eb57485aa7928ce8e2c8a41";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_main);

        forecastsListView = (ListView) findViewById(R.id.list_day_forecasts);
        errorTextView = (TextView) findViewById(R.id.error_message_text_view);
        forecastsListView.setEmptyView(errorTextView);

        if(NetworkUtils.isInternetConnection(getApplicationContext())) {
            getLoaderManager().initLoader(0, null, this);
        }else{
            setErrorTextViewMessage(R.string.no_internet);
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
            setErrorTextViewMessage(R.string.no_items);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<DayForecast>> loader) {
    }

    private void updateUI(List<DayForecast> forecast) {
        this.forecasts = (ArrayList) forecast;
        DayForecastAdapter adapter = new DayForecastAdapter(this, forecasts);
        forecastsListView.setAdapter(adapter);
    }

    private void setErrorTextViewMessage(int stringResource){
        errorTextView.setText(stringResource);
    }
}
