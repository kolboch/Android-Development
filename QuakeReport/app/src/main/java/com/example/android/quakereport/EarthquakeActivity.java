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
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<EarthquakeRecord>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private ArrayList<EarthquakeRecord> earthquakes;
    private static final String QUERY_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=4&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        getLoaderManager().initLoader(0, null, this);
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
        this.earthquakes = (ArrayList)earthquakesRecord;
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        EarthquakeRecordAdapter earthquakesAdapter = new EarthquakeRecordAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(earthquakesAdapter);
        setListItemsURLs(earthquakeListView);
    }

    @Override
    public Loader<List<EarthquakeRecord>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG, "onCreateLoader called");
        EarthquakeLoader loader = new EarthquakeLoader(getApplicationContext(), new String[]{QUERY_URL});
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<EarthquakeRecord>> loader, List<EarthquakeRecord> earthquakeRecords) {
        Log.i(LOG_TAG, "onLoadFinished called");
        if(earthquakeRecords != null){
            updateUI(earthquakeRecords);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<EarthquakeRecord>> loader) {
        Log.i(LOG_TAG, "onLoaderResetCalled called");
    }
}
