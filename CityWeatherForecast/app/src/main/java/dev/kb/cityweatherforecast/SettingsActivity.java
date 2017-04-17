package dev.kb.cityweatherforecast;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import dev.kb.cityweatherforecast.fragments.SettingsPreferenceFragment;
import dev.kb.cityweatherforecast.listeners.SpinnerCitiesListener;

/**
 * Created by Karol on 2017-02-16.
 */

public class SettingsActivity extends AppCompatActivity {

    private Spinner lastUsedCitiesSpinner;
    private TextView lastUsedCitiesLabel;
    private SettingsPreferenceFragment settingsFragment;
    private SpinnerCitiesListener spinnerListener;
    public static final String SELECTION_SPINNER_CITIES_KEY = "SELECTION_LAST_USED_CITIES";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        settingsFragment = new SettingsPreferenceFragment();
        spinnerListener = new SpinnerCitiesListener(this, settingsFragment);

        FragmentManager sfm = getSupportFragmentManager();
        sfm.beginTransaction().add(R.id.fragment_preferences, settingsFragment).commit();
        initSettingsViews();
    }

    private void initSettingsViews() {
        this.lastUsedCitiesSpinner = (Spinner) findViewById(R.id.spinner_cities_last_used);
        if (lastUsedCitiesSpinner != null) {
            initSpinnerWithCities();
            setSpinnerWithCitiesListeners();
        }
        this.lastUsedCitiesLabel = (TextView) findViewById(R.id.label_cities_last_used);
    }

    private void initSpinnerWithCities() {
        String[] lastUsedCities = SavedStringsUtils.getSavedValues(this,
                SavedStringsUtils.SAVED_CITIES_KEY,
                SavedStringsUtils.SPLIT_CITIES);
        if (lastUsedCities != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item,
                    lastUsedCities);
            this.lastUsedCitiesSpinner.setAdapter(adapter);
            setSavedSpinnerSelection();
        } else {
            this.lastUsedCitiesSpinner.setVisibility(View.GONE);
            this.lastUsedCitiesLabel.setVisibility(View.GONE);
        }
    }

    private void setSpinnerWithCitiesListeners() {
        if (spinnerListener != null) {
            lastUsedCitiesSpinner.setOnItemSelectedListener(spinnerListener);
        }
    }

    private void setSavedSpinnerSelection(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int selection = sp.getInt(SELECTION_SPINNER_CITIES_KEY, -1);
        if(selection != -1){
            this.lastUsedCitiesSpinner.setSelection(selection);
        }
    }

}
