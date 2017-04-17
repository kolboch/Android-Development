package dev.kb.cityweatherforecast.listeners;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;

import dev.kb.cityweatherforecast.R;
import dev.kb.cityweatherforecast.SettingsActivity;
import dev.kb.cityweatherforecast.fragments.SettingsPreferenceFragment;

/**
 * Created by Karol on 2017-04-17.
 */

public class SpinnerCitiesListener implements AdapterView.OnItemSelectedListener {

    private Context context;
    private SettingsPreferenceFragment settingsFragment;

    public SpinnerCitiesListener(Context c, SettingsPreferenceFragment settingsFragment){
        this.context = c;
        this.settingsFragment = settingsFragment;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int index, long id) {
        String selected = (String) adapterView.getItemAtPosition(index);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(context.getString(R.string.settings_city_key), selected).apply();

        Preference toUpdate = settingsFragment.findPreference(context.getString(R.string.settings_city_key));
        settingsFragment.onPreferenceChange(toUpdate, selected);
        //remember selection
        sharedPreferences.edit().putInt(SettingsActivity.SELECTION_SPINNER_CITIES_KEY, index).apply();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
