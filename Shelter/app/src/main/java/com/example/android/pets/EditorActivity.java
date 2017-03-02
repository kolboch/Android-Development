/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.pets.data.Pet;
import com.example.android.pets.data.PetContract.PetEntry;
import com.example.android.pets.data.PetDbHelper;

/**
 * Allows user to create a new pet or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {
    /**
     * constant for logs
     */
    private static final String LOG_TAG_EDITOR = EditorActivity.class.getSimpleName();
    /**
     * constant for checking error case
     */
    private static final long NOT_ENOUGH_DATA = -7;
    /**
     * EditText field to enter the pet's name
     */
    private EditText mNameEditText;

    /**
     * EditText field to enter the pet's breed
     */
    private EditText mBreedEditText;

    /**
     * EditText field to enter the pet's weight
     */
    private EditText mWeightEditText;

    /**
     * EditText field to enter the pet's gender
     */
    private Spinner mGenderSpinner;

    /**
     * Gender of the pet. The possible values are:
     * 0 for unknown gender, 1 for male, 2 for female.
     */
    private int mGender = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_pet_name);
        mBreedEditText = (EditText) findViewById(R.id.edit_pet_breed);
        mWeightEditText = (EditText) findViewById(R.id.edit_pet_weight);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = PetEntry.GENDER_MALE; // Male
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = PetEntry.GENDER_FEMALE; // Female
                    } else {
                        mGender = PetEntry.GENDER_UNKNOWN; // Unknown
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 0; // Unknown
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                long rowID = updateOrInsertData();
                if(rowID != this.NOT_ENOUGH_DATA) {
                    showActionSaveToast(rowID);
                    Intent intent = new Intent(EditorActivity.this, CatalogActivity.class);
                    startActivity(intent);
                }
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * updates if modifying or inserts new data based on user input's
     */
    private long updateOrInsertData() {
        long newRowID = NOT_ENOUGH_DATA;
        try {
            Pet pet = getPetFromInputs();

            PetDbHelper helper = new PetDbHelper(this);
            SQLiteDatabase database = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PetEntry.COLUMN_PET_NAME, pet.getName());
            values.put(PetEntry.COLUMN_PET_BREED, pet.getBreed());
            values.put(PetEntry.COLUMN_PET_GENDER, pet.getGender());
            values.put(PetEntry.COLUMN_PET_WEIGHT, pet.getWeight());

            newRowID = database.insert(PetEntry.TABLE_NAME, null, values);
        }
        catch(NotEnoughDataProvidedException e){
            Toast.makeText(this, R.string.toast_must_provide_name, Toast.LENGTH_SHORT).show();
        }
        return newRowID;
    }

    /**
     * getting data from input View's
     *
     * @return Pet, created from user's input
     */
    private Pet getPetFromInputs() throws NotEnoughDataProvidedException{
        String breed = this.mBreedEditText.getText().toString().trim();
        String name = this.mNameEditText.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            throw new NotEnoughDataProvidedException(R.string.toast_must_provide_name + "");
        }
        int weight;
        String weightInput = mWeightEditText.getText().toString().trim();
        if(TextUtils.isEmpty(weightInput)){
            weight = 0;
        }else{
            weight = Integer.parseInt(weightInput);
        }
        Pet pet = new Pet(name, breed, mGender, weight);
        return pet;
    }

    /**
     * shows message based on saving result ( saved successfully or not)
     * @param rowId
     */
    private void showActionSaveToast(long rowId) {
        if (rowId == -1) {
            Toast.makeText(this, R.string.toast_record_save_fail, Toast.LENGTH_SHORT).show();
        } else {
            Log.i(LOG_TAG_EDITOR, rowId + " ");
            Toast.makeText(this, getResources().getString(R.string.toast_record_save_success) + rowId, Toast.LENGTH_SHORT).show();
        }
    }
}