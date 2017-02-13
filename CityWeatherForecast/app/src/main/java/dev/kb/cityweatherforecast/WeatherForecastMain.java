package dev.kb.cityweatherforecast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class WeatherForecastMain extends AppCompatActivity {

    private static final String LOG_TAG = WeatherForecastMain.class.getName();
    private ArrayList<DayForecast> forecasts;
    private ListView forecastsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_main);
/*
        forecasts = getTestData();
        forecastsListView = (ListView) findViewById(R.id.list_day_forecasts);
        DayForecastAdapter adapter = new DayForecastAdapter(this, forecasts);
        forecastsListView.setAdapter(adapter);
        */
    }

    /* only for testing
    private ArrayList<DayForecast> getTestData() {

        ArrayList<DayForecast> sample = new ArrayList<>();
        sample.add(a1);
        sample.add(a2);
        sample.add(a3);
        return sample;
    }
    */
}
