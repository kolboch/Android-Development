package dev.kb.cityweatherforecast.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.preference.PreferenceFragmentCompat;

import dev.kb.cityweatherforecast.R;
import dev.kb.cityweatherforecast.SavedStringsUtils;

/**
 * Created by Karol on 2017-04-17.
 */

public class SettingsPreferenceFragment extends PreferenceFragmentCompat implements android.support.v7.preference.Preference.OnPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_main);

        android.support.v7.preference.Preference temperatureUnit = findPreference(getString(R.string.settings_temp_unit_key));
        bindPreferenceSummaryToValue(temperatureUnit);

        android.support.v7.preference.Preference city = findPreference(getString(R.string.settings_city_key));
        bindPreferenceSummaryToValue(city);
    }

    private void bindPreferenceSummaryToValue(android.support.v7.preference.Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
        String preferenceString = preferences.getString(preference.getKey(), "");
        onPreferenceChange(preference, preferenceString);
    }

    @Override
    public boolean onPreferenceChange(android.support.v7.preference.Preference preference, Object o) {
        String value = null;
        if(o != null) {
            value = o.toString();
        }
        if (preference instanceof android.support.v7.preference.ListPreference) {
            android.support.v7.preference.ListPreference preferencesList = (android.support.v7.preference.ListPreference) preference;
            int prefIndex = preferencesList.findIndexOfValue(value);
            if (prefIndex >= 0) {
                CharSequence[] label = preferencesList.getEntries();
                preference.setSummary(label[prefIndex]);
            }
        } else {
            preference.setSummary(value);
            if (preference.getKey().equals(getString(R.string.settings_city_key))) {
                SavedStringsUtils.addStringItem(getActivity(), value,
                        SavedStringsUtils.SAVED_CITIES_KEY,
                        SavedStringsUtils.SPLIT_CITIES,
                        SavedStringsUtils.CITIES_LIMIT);
            }
        }
        return true;
    }
}