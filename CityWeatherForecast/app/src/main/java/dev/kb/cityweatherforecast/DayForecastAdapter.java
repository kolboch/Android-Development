package dev.kb.cityweatherforecast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
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

    private static final String LOG_ADAPTER = DayForecastAdapter.class.getName();

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
        listItemView.setBackgroundResource(getBackgroundBanner(current.getWeathers()));
        /* set day name */
        TextView dayForecastText = (TextView) listItemView.findViewById(R.id.banner_text_view);
        dayForecastText.setText(current.getDayName());

        return listItemView;
    }

    private int getBackgroundBanner(ArrayList<Weather> weathers) {
        int code;
        if(weathers != null){
            code = weathers.get(0).getWeatherID();
        }
        else{
            code = -1;
        }
        return getBackgroundResourceFromWeatherCode(code);
    }

    /* get relevant resource for given weather code */
    private int getBackgroundResourceFromWeatherCode(int weatherCode) {
        int imageResource;
        Log.i(LOG_ADAPTER, "Weathercode: " + weatherCode);
        Log.i(LOG_ADAPTER, "Weathercode / 100: " + weatherCode / 100);
        switch (weatherCode / 100) {
            case 8:
                imageResource = R.drawable.bluesky_banner_800;
                break;
            case 7:
                imageResource = R.drawable.atmosphere_banner_700;
                break;
            case 6:
                imageResource = R.drawable.snow_banner_600;
                break;
            case 5:
                imageResource = R.drawable.rain_banner_500;
                break;
            case 2:
                imageResource = R.drawable.thunderstorm_banner_200;
                break;
            default:
                imageResource = R.drawable.default_banner;
                break;
        }
        return imageResource;
    }

    private int getIconFromWeatherCode(int weatherCode){
        //TODO
        return 0;
    }
}
