package dev.kb.cityweatherforecast;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WeatherForecastMain extends AppCompatActivity implements LoaderCallbacks<List<DayForecast>> {

    //TODO add spinner for loading
    //TODO add preferences for city with gentle country code handling
    //TODO consider downloading query_APPID from some server or sth ?

    private static final String LOG_TAG = WeatherForecastMain.class.getName();
    private ArrayList<DayForecast> forecasts;
    private ExpandableListView forecastsExpandableListView;
    private TextView errorTextView;
    private static final String query_BASE = "http://api.openweathermap.org/data/2.5/forecast?q=Paris,fr";
    private static final String query_APPID = "737490f14eb57485aa7928ce8e2c8a41";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_main);

        forecastsExpandableListView = (ExpandableListView) findViewById(R.id.list_day_forecasts);
        errorTextView = (TextView) findViewById(R.id.error_message_text_view);
        forecastsExpandableListView.setEmptyView(errorTextView);

        if (NetworkUtils.isInternetConnection(getApplicationContext())) {
            getLoaderManager().initLoader(0, null, this);
        } else {
            setErrorTextViewMessage(R.string.no_internet);
        }
    }

    @Override
    public Loader<List<DayForecast>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String temperatureUnit = preferences.getString(getString(R.string.settings_temp_unit_key),
                getString(R.string.settings_temp_unit_default));

        Uri baseUri = Uri.parse(query_BASE);
        Uri.Builder builder = baseUri.buildUpon();
        builder.appendQueryParameter("units", temperatureUnit);

        builder.appendQueryParameter("appid", query_APPID); // api access code

        ForecastLoader loader = new ForecastLoader(builder.toString(), getApplicationContext());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateUI(List<DayForecast> forecast) {
        this.forecasts = (ArrayList) forecast;
        DayForecastAdapter adapter = new DayForecastAdapter(this, forecasts);
        forecastsExpandableListView.setAdapter(adapter);
    }

    private void setErrorTextViewMessage(int stringResource) {
        errorTextView.setText(stringResource);
    }
}
