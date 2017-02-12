package dev.kb.cityweatherforecast;

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
 * Created by Karol on 2017-02-12.
 */

public class QueryUtils {

    private static final String LOG_QUERY = QueryUtils.class.getName();

    private QueryUtils(){}

    private static URL createURL(String stringURL){
        URL url = null;
        try{
            url = new URL(stringURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_QUERY, "Error with creating URL", e);
        }
        return url;
    }

    public static List<DayForecast> fetchForecasts(String stringURL){
        URL url = createURL(stringURL);
        String jsonResponse = "";
        if(url != null){
            HttpURLConnection urlConnection = null;
            InputStream is = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                urlConnection.connect();
                if(urlConnection.getResponseCode() == 200){ // if successful
                    is = urlConnection.getInputStream();
                    jsonResponse = readInputStream(is);
                } else{
                    Log.e(LOG_QUERY, "Error response code: " + urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                Log.e(LOG_QUERY, "IOException when connecting to URL", e);
            }
        }
        return parseForecasts(jsonResponse);
    }

    private static String readInputStream(InputStream stream) throws IOException{
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
    /* TODO handling forecast starting after first planned display time
        (we got then not enough data for first day)
    */
    private static List<DayForecast> parseForecasts(String jsonData){
        ArrayList<DayForecast> forecasts = new ArrayList<>();
        if(!TextUtils.isEmpty(jsonData)){
            try {
                JSONObject root = new JSONObject(jsonData);
                JSONArray listOfForecasts = root.getJSONArray("list"); //should be always 40 items

                //declared outside loop to process faster
                JSONObject forecast, main, weatherObject;
                JSONArray weatherArray;
                long date;
                int weatherID;
                double temperature, pressure;

                for(int i = 0; i < listOfForecasts.length(); i++){
                    forecast = listOfForecasts.getJSONObject(i);
                    date = forecast.getLong("dt");
                    main = forecast.getJSONObject("main");
                    temperature = main.getDouble("temp");
                    pressure = main.getDouble("pressure");

                    weatherArray = forecast.getJSONArray("weather");
                    weatherObject = weatherArray.getJSONObject(0);
                    weatherID = weatherObject.getInt("id");

                    Weather weather = new Weather(temperature,pressure,weatherID, date);
                    //TODO results to array, then create DayForecast object, inner loop ?
                    //TODO verify hour and day from unix time
                }

            } catch (JSONException e) {
                Log.e(LOG_QUERY, "Parsing json error", e);
            }
        }
        return forecasts;
    }
}
