package dev.kb.cityweatherforecast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Karol on 2017-02-12.
 */

public class DayForecastAdapter extends BaseExpandableListAdapter {

    private static final String LOG_ADAPTER = DayForecastAdapter.class.getName();
    private Context context;
    private ArrayList<DayForecast> forecastList;

    public DayForecastAdapter(Context context, ArrayList<DayForecast> forecastList) {
        this.context = context;
        this.forecastList = forecastList;
    }

    /**
     * returns expandable with, after clicking shows child Views
     */
    @Override
    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup groupView) {
        DayForecast current = (DayForecast) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.day_forecast_list_item, null);
        }
        /* set icon */
        view.setBackgroundResource(getBackgroundBanner(current.getWeathers()));
        /* set day name */
        TextView dayForecastText = (TextView) view.findViewById(R.id.banner_text_view);
        dayForecastText.setText(current.getDayName());

        return view;
    }

    /**
     * return childView for given group and child position
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.day_forecast_child_item, null);
        }
        Weather current = (Weather) getChild(groupPosition, childPosition);
        if (current != null) {
            String temperature = formatTemperature(current.getTemperature());
            TextView tempTextView = (TextView) view.findViewById(R.id.icon_and_temperature);
            tempTextView.setText(temperature);
            tempTextView.setBackgroundResource(ResourceUtils.getIconResourceFromWeatherCode(current.getWeatherCode()));

            TextView dateTextView = (TextView) view.findViewById(R.id.weather_date);
            dateTextView.setText(dateFromUnixTimestamp(current.getDate()));

            TextView pressureTextView = (TextView) view.findViewById(R.id.pressure_textView);
            pressureTextView.setText(formatPressure(current.getPressure()));

            TextView timeTextView = (TextView) view.findViewById(R.id.weather_time);
            timeTextView.setText(formatHour(current.getHour()));
        }
        return view;
    }

    @Override
    public int getGroupCount() {
        return forecastList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return forecastList.get(groupPosition).getWeathers().size();
    }

    @Override
    public Object getGroup(int i) {
        return forecastList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return forecastList.get(i).getWeathers().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    private int getBackgroundBanner(ArrayList<Weather> weathers) {
        int weatherCode;
        if (weathers != null) {
            weatherCode = weathers.get(0).getWeatherCode();
        } else {
            weatherCode = -1;
        }
        return ResourceUtils.getBackgroundResourceFromWeatherCode(weatherCode);
    }

    private String formatTemperature(double temperature) {
        //TODO check in preferences for format and append K or C to degrees
        return (int) temperature + "";
    }

    private String dateFromUnixTimestamp(long date) {
        //TODO
        return "17-08-2020";
    }

    private String formatPressure(double pressure) {
        //TODO verify if all cases would be right with that approach
        return (int) pressure + " hPa";
    }

    private String formatHour(int hour) {
        //TODO format with some pattern
        return hour + ":00";
    }
}


