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

        forecasts = getTestData();
        forecastsListView = (ListView) findViewById(R.id.list_day_forecasts);
        DayForecastAdapter adapter = new DayForecastAdapter(this, forecasts);
        forecastsListView.setAdapter(adapter);
    }

    /* only for testing */
    private ArrayList<DayForecast> getTestData() {
        DayForecast a1 = new DayForecast("09-09-2016", "MONDAY", new Weather[]{new Weather(45, 1000, 800, "12:00")});
        DayForecast a2 = new DayForecast("09-09-2017", "WEDNESDAY", new Weather[]{new Weather(36, 1010, 600, "15:00")});
        DayForecast a3 = new DayForecast("09-09-2015", "THURSDAY", new Weather[]{new Weather(-9.5, 1005, 500, "9:00")});
        ArrayList<DayForecast> sample = new ArrayList<>();
        sample.add(a1);
        sample.add(a2);
        sample.add(a3);
        return sample;
    }
}
