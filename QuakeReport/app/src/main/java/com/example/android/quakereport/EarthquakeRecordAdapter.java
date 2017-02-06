package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
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
        double magnitude = currentEarthquakeRecord.getMagnitude();
        magnitudeTextView.setText(formatMagnitude(magnitude));
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();
        magnitudeCircle.setColor(getMagnitudeColor(magnitude));

        TextView placeOffsetTextView = (TextView) listItemView.findViewById(R.id.list_item_place_offset);
        TextView placeTextView = (TextView) listItemView.findViewById(R.id.list_item_place);
        placeOffsetTextView.setText(getPlaceOffset(currentEarthquakeRecord.getPlace()));
        placeTextView.setText(getPlace(currentEarthquakeRecord.getPlace()));

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.list_item_date);
        TextView dateHourTextView = (TextView) listItemView.findViewById(R.id.list_item_date_hour);
        dateTextView.setText(formatDate(currentEarthquakeRecord.getDate()));
        dateHourTextView.setText(formatHour(currentEarthquakeRecord.getDate()));

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

    /**
     * retrieves only city/ region from place string
     *
     * @param place
     */
    private String getPlace(String place) {
        Matcher m = PLACE_PATTERN.matcher(place);
        String result = m.find() ? m.group(1) : "";
        return result;
    }

    /**
     * retrieves offset of place, if no provided returns 'Near the' string
     *
     * @param place
     */
    private String getPlaceOffset(String place) {
        Matcher m = PLACE_OFFSET_PATTERN.matcher(place);
        String result = m.find() ? m.group() : "Near the";
        return result;
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        int colorID;
        switch ((int) Math.floor(magnitude)) {
            case 0:
            case 1:
                colorID = R.color.magnitude2_less;
                break;
            case 2:
                colorID = R.color.magnitude2;
                break;
            case 3:
                colorID = R.color.magnitude3;
                break;
            case 4:
                colorID = R.color.magnitude4;
                break;
            case 5:
                colorID = R.color.magnitude5;
                break;
            case 6:
                colorID = R.color.magnitude6;
                break;
            case 7:
                colorID = R.color.magnitude7;
                break;
            case 8:
                colorID = R.color.magnitude8;
                break;
            case 9:
                colorID = R.color.magnitude9;
                break;
            default:
                colorID = R.color.magnitude10;
                break;
        }
        return ContextCompat.getColor(getContext(), colorID);
    }
}
