/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<EarthquakeRecord>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private ArrayList<EarthquakeRecord> earthquakes;
    private TextView noItemsTextView;
    private ListView earthquakesListView;
    private static final String QUERY_URL_BASE = "http://earthquake.usgs.gov/fdsnws/event/1/query";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        noItemsTextView = (TextView) findViewById(R.id.no_items_textView);
        earthquakesListView = (ListView) findViewById(R.id.list);
        earthquakesListView.setEmptyView(noItemsTextView);

        if (NetworkUtils.isInternetConnection(this)) {
            getLoaderManager().initLoader(0, null, this);
        } else {
            hideProgressBar();
            displayNoInternetConnection();
        }
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

    private void setListItemsURLs(final ListView lv) {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                EarthquakeRecord item = (EarthquakeRecord) lv.getAdapter().getItem(position);
                String urlString = item.getEarthquakeURL();
                Intent intent = null;
                try {
                    URL url = new URL(urlString);
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()));
                } catch (MalformedURLException e) {
                    Log.e(LOG_TAG, "Error when creating url from url string.");
                    e.printStackTrace();
                }
                if (intent != null) {
                    startActivity(intent);
                }
            }
        });
    }

    private void updateUI(List<EarthquakeRecord> earthquakesRecord) {
        this.earthquakes = (ArrayList) earthquakesRecord;
        // Create a new {@link ArrayAdapter} of earthquakes
        EarthquakeRecordAdapter earthquakesAdapter = new EarthquakeRecordAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakesListView.setAdapter(earthquakesAdapter);
        setListItemsURLs(earthquakesListView);
    }

    @Override
    public Loader<List<EarthquakeRecord>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.setting_min_magnitude_default));
        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));
        Uri baseUri = Uri.parse(QUERY_URL_BASE);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        EarthquakeLoader loader = new EarthquakeLoader(this, new String[]{uriBuilder.toString()});
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<EarthquakeRecord>> loader, List<EarthquakeRecord> earthquakeRecords) {
        hideProgressBar();
        if (earthquakeRecords != null && !earthquakeRecords.isEmpty()) {
            updateUI(earthquakeRecords);
        } else {
            displayNoItemsToShow();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<EarthquakeRecord>> loader) {
    }

    private void displayNoItemsToShow() {
        noItemsTextView.setText(R.string.no_earthquakes);
    }

    private void displayNoInternetConnection() {
        noItemsTextView.setText(R.string.no_internet);
    }

    private void hideProgressBar() {
        findViewById(R.id.loading_spinner).setVisibility(View.GONE);
    }
}
