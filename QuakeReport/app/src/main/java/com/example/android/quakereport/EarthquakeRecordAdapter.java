package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Karol on 2017-02-04.
 */

public class EarthquakeRecordAdapter extends ArrayAdapter<EarthquakeRecord> {

    private static final String LOG_TAG = EarthquakeRecordAdapter.class.getSimpleName();
    /*
    *   SimpleDateFormat to get data in format like:
    *   Jul 17, 2017
    *   10:09 AM
    **/
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM d, YYYY");
    private static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("K:mm a");
    private static final Pattern PLACE_OFFSET_PATTERN = Pattern.compile("(.*\\sof)");
    private static final Pattern PLACE_PATTERN = Pattern.compile("^(?:.*\\sof\\s)?(.*)$");

    public EarthquakeRecordAdapter(Context context, ArrayList<EarthquakeRecord> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        EarthquakeRecord currentEarthquakeRecord = getItem(position);

        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.list_item_magnitude);
        magnitudeTextView.setText("" + currentEarthquakeRecord.getMagnitude());

        TextView placeOffsetTextView = (TextView) listItemView.findViewById(R.id.list_item_place_offset);
        TextView placeTextView = (TextView) listItemView.findViewById(R.id.list_item_place);
        placeOffsetTextView.setText(getPlaceOffset(currentEarthquakeRecord.getPlace()));
        placeTextView.setText(getPlace(currentEarthquakeRecord.getPlace()));

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.list_item_date);
        TextView dateHourTextView = (TextView) listItemView.findViewById(R.id.list_item_date_hour);
        dateTextView.setText("" + formatDate(currentEarthquakeRecord.getDate()));
        dateHourTextView.setText("" + formatHour(currentEarthquakeRecord.getDate()));

        return listItemView;
    }

    /**
     * formats given time in unix to string date representation
     *
     * @param date
     */
    private String formatDate(long date) {
        return DATE_FORMAT.format(new Date(date));
    }

    /**
     * formats givent ime in unix to string hour representation
     *
     * @param date
     */
    private String formatHour(long date) {
        return HOUR_FORMAT.format(new Date(date));
    }

    private String getPlace(String place) {
        Matcher m = PLACE_PATTERN.matcher(place);
        String result = m.find() ? m.group(1) : "";
        return result;
    }

    private String getPlaceOffset(String place) {
        Matcher m = PLACE_OFFSET_PATTERN.matcher(place);
        String result = m.find() ? m.group() : "Near the";
        return result;
    }
}
