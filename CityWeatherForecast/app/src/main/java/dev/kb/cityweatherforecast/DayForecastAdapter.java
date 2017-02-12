package dev.kb.cityweatherforecast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Karol on 2017-02-12.
 */

public class DayForecastAdapter extends ArrayAdapter<DayForecast> {

    public DayForecastAdapter(Context context, ArrayList<DayForecast> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.day_forecast_list_item, parent, false);
        }
        DayForecast current = getItem(position);
        /* set icon */
        listItemView.setBackgroundResource(getRelevantIconResource(current.getWeathers()));
        /* set day name */
        TextView dayForecastText = (TextView) listItemView.findViewById(R.id.banner_text_view);
        dayForecastText.setText(current.getDayName());

        return listItemView;
    }

    /* icon should be based on most appearing weather, now for test it takes first one */
    private int getRelevantIconResource(Weather[] weathers) {
        return getResourceForCode(weathers[0].getWeatherID());
    }

    /* base case not fully developed */
    private int getResourceForCode(int weatherCode) {
        int imageResource;
        switch (weatherCode) {
            case 800:
                imageResource = R.drawable.bluesky_banner;
                break;
            default:
                imageResource = R.drawable.rain_banner;
                break;
        }
        return imageResource;
    }
}
