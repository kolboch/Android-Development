package dev.kb.cityweatherforecast;

import android.content.Context;
import android.content.res.Resources;
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
import java.util.Calendar;
import java.util.List;

/**
 * Created by Karol on 2017-02-12.
 */

public class QueryUtils {

    private static final String LOG_QUERY = QueryUtils.class.getName();
    private static final Calendar CALENDAR = Calendar.getInstance();
    private static final int RESULT_HOUR_1 = 9;
    private static final int RESULT_HOUR_2 = 15;
    private static final int RESULT_HOUR_3 = 21;

    private QueryUtils() {
    }

    private static URL createURL(String stringURL) {
        URL url = null;
        try {
            url = new URL(stringURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_QUERY, "Error with creating URL", e);
        }
        return url;
    }

    public static List<DayForecast> fetchForecasts(String stringURL, Context context) {
        URL url = createURL(stringURL);
        String jsonResponse = "";
        if (url != null) {
            HttpURLConnection urlConnection = null;
            InputStream is = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                urlConnection.connect();
                if (urlConnection.getResponseCode() == 200) { // if successful
                    is = urlConnection.getInputStream();
                    jsonResponse = readInputStream(is);
                } else {
                    Log.e(LOG_QUERY, "Error response code: " + urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                Log.e(LOG_QUERY, "IOException when connecting to URL", e);
            }
        }
        return parseForecasts(jsonResponse, context);
    }

    private static String readInputStream(InputStream stream) throws IOException {
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

    private static List<DayForecast> parseForecasts(String jsonData, Context context) {
        ArrayList<DayForecast> forecasts = null;
        if (!TextUtils.isEmpty(jsonData)) {
            try {
                JSONObject root = new JSONObject(jsonData);
                JSONArray listOfForecasts = root.getJSONArray("list");

                //declared outside loop to process faster
                JSONObject forecast, main, weatherJsonObject;
                JSONArray weatherJsonArray;
                long date;
                int weatherID, hour;
                double temperature, pressure;
                ArrayList<Weather> weatherList = new ArrayList<>();
                for (int i = 0; i < listOfForecasts.length(); i++) {
                    forecast = listOfForecasts.getJSONObject(i);
                    date = forecast.getLong("dt");
                    hour = getHourFromUnixDate(date);
                    if (!isHourNeeded(hour)) {
                        continue;
                    }
                    main = forecast.getJSONObject("main");
                    temperature = main.getDouble("temp");
                    pressure = main.getDouble("pressure");

                    weatherJsonArray = forecast.getJSONArray("weather");
                    weatherJsonObject = weatherJsonArray.getJSONObject(0);
                    weatherID = weatherJsonObject.getInt("id");

                    Weather weather = new Weather(temperature, pressure, weatherID, date, hour);
                    weatherList.add(weather);
                }
                forecasts = parseWeatherForecasts(weatherList, context); // properly split data to DayForecast objects
            } catch (JSONException e) {
                Log.e(LOG_QUERY, "Parsing json error", e);
            }
        }
        return forecasts;
    }

    /**
     * extracts hour from unix timestamp
     */
    private static int getHourFromUnixDate(long date) {
        date %= 86400; //86400 seconds in one day, 3600 seconds in one hour
        return (int) (date / 3600);
    }

    private static ArrayList<DayForecast> parseWeatherForecasts(ArrayList<Weather> weatherList, Context context) {
        ArrayList<DayForecast> forecasts = new ArrayList<>();
        ArrayList<Weather> bufferDayForecast = new ArrayList<>();
        int dayForecasts = 0;
        Weather current;
        for (int i = 0; dayForecasts < 5 && i < weatherList.size(); i++) {
            current = weatherList.get(i);
            bufferDayForecast.add(current);
            if (current.getHour() == 21) {
                dayForecasts++;
                forecasts.add(new DayForecast(getDayOfWeek(current.getDate(), context), bufferDayForecast));
            }
        }
        return forecasts;
    }

    private static String getDayOfWeek(long date, Context context) {
        CALENDAR.setTimeInMillis(date * 1000);
        int day = CALENDAR.get(Calendar.DAY_OF_WEEK);
        Resources res = context.getResources();
        switch (day) {
            case Calendar.SUNDAY:
                return res.getString(R.string.sunday);
            case Calendar.MONDAY:
                return res.getString(R.string.monday);
            case Calendar.TUESDAY:
                return res.getString(R.string.tuesday);
            case Calendar.WEDNESDAY:
                return res.getString(R.string.wednesday);
            case Calendar.THURSDAY:
                return res.getString(R.string.thursday);
            case Calendar.FRIDAY:
                return res.getString(R.string.friday);
            case Calendar.SATURDAY:
                return res.getString(R.string.saturday);
            default:
                return res.getString(R.string.day);
        }
    }

    private static boolean isHourNeeded(int hour) {
        switch (hour) {
            case RESULT_HOUR_1:
            case RESULT_HOUR_2:
            case RESULT_HOUR_3:
                return true;
            default:
                return false;
        }
    }
}
