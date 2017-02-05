package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Karol on 2017-02-04.
 */

public class EarthquakeRecordAdapter extends ArrayAdapter<EarthquakeRecord> {

    private static final String LOG_TAG = EarthquakeRecordAdapter.class.getSimpleName();


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

        TextView cityTextView = (TextView) listItemView.findViewById(R.id.list_item_city);
        cityTextView.setText("" + currentEarthquakeRecord.getCity());

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.list_item_date);
        dateTextView.setText("" + currentEarthquakeRecord.getDate());

        return listItemView;
    }
}
