package dev.kb.cityweatherforecast;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class WeatherForecastMain extends AppCompatActivity {

    private static final String LOG_TAG = WeatherForecastMain.class.getName();
    private ArrayList<DayForecast> forecasts;
    private ListView forecastsListView;
    private String queryString = "http://api.openweathermap.org/data/2.5/forecast?q=Paris,fr&appid=737490f14eb57485aa7928ce8e2c8a41";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_main);
        new DataFetcher().execute(queryString);
    }

    private class DataFetcher extends AsyncTask<String, ArrayList<DayForecast>, Void>{

        @Override
        protected Void doInBackground(String... strings) {
            forecasts = (ArrayList)QueryUtils.fetchForecasts(queryString, getApplicationContext());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            updateUI();
        }
    }

    private void updateUI(){
        forecastsListView = (ListView) findViewById(R.id.list_day_forecasts);
        DayForecastAdapter adapter = new DayForecastAdapter(this, forecasts);
        forecastsListView.setAdapter(adapter);
    }
}
