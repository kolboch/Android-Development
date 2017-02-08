package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karol on 2017-02-05.
 */

public final class QueryUtils {

    private static final String LOG_INF = "QueryUtils class";

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_INF, "Error with creating URL ", e);
        }
        return url;
    }

    public static List<EarthquakeRecord> fetchEarthquakes(String urlString) {
        URL url = createUrl(urlString);
        String jsonResponse = "";
        if (url != null) {
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(15000);
                urlConnection.setReadTimeout(10000);
                urlConnection.connect();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readStream(inputStream);
                } else {
                    Log.e(LOG_INF, "Error response code: " + urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                Log.e(LOG_INF, "IOException", e);
                e.printStackTrace();
            }
        }
        return extractEarthquakes(jsonResponse);
    }

    private static String readStream(InputStream stream) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (stream != null) {
            InputStreamReader streamReader = new InputStreamReader(stream);
            BufferedReader buffer = new BufferedReader(streamReader);
            String line = buffer.readLine();
            while (line != null) {
                sb.append(line);
                line = buffer.readLine();
            }
        }
        return sb.toString();
    }

    /**
     * Return a list of {@link EarthquakeRecord} objects that has been built up from
     * parsing a JSON response.
     */
    private static List<EarthquakeRecord> extractEarthquakes(String jsonData) {
        List<EarthquakeRecord> earthquakes = new ArrayList<>();
        if (!TextUtils.isEmpty(jsonData)) {
            try {
                JSONObject root = new JSONObject(jsonData);
                JSONArray features = root.getJSONArray("features");

                // out of loop to make processing faster
                JSONObject earthquake, properties;
                double magnitude;
                long date;
                String details;
                for (int i = 0; i < features.length(); i++) {
                    earthquake = features.getJSONObject(i);
                    properties = earthquake.getJSONObject("properties");
                    magnitude = properties.getDouble("mag");
                    String place = properties.getString("place");
                    date = properties.getLong("time");
                    details = properties.getString("url");
                    EarthquakeRecord record = new EarthquakeRecord(magnitude, place, date, details);
                    earthquakes.add(record);
                }
            } catch (JSONException e) {
                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            }
        }
        return earthquakes;
    }

}
