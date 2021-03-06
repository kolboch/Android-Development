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

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.pets.data.Pet;
import com.example.android.pets.data.PetContract.PetEntry;

/**
 * Allows user to create a new pet or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
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

    private Uri mPetToEditUri;
    private final int EXISTING_LOADER_ID = 1;
    private boolean mChangeMade = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        if (isEditPetCall()) {
            setActivityTitle(R.string.editor_activity_title_edit_pet);
            mPetToEditUri = (Uri) getIntent().getExtras().get(CatalogActivity.EDIT_ITEM_URI_EXTRA);
            getSupportLoaderManager().initLoader(EXISTING_LOADER_ID, null, this);
        } else {
            invalidateOptionsMenu();
        }

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_pet_name);
        mBreedEditText = (EditText) findViewById(R.id.edit_pet_breed);
        mWeightEditText = (EditText) findViewById(R.id.edit_pet_weight);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);

        setMyTouchListener(mNameEditText);
        setMyTouchListener(mBreedEditText);
        setMyTouchListener(mWeightEditText);
        setMyTouchListener(mGenderSpinner);

        setupSpinner();
    }

    private boolean isEditPetCall() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(CatalogActivity.EDIT_ITEM_URI_EXTRA)) {
                return true;
            }
        }
        return false;
    }

    private void setMyTouchListener(View v) {
        v.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mChangeMade = true;
                return false;
            }
        });
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (mPetToEditUri == null) {
            MenuItem itemToHide = menu.findItem(R.id.action_delete); //hiding delete option if in insert mode
            itemToHide.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_save:
                long rowID = updateOrInsertData();
                if (rowID != this.NOT_ENOUGH_DATA) {
                    showActionSaveToast(rowID);
                    finish();
                }
                return true;
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            case android.R.id.home:
                if (!mChangeMade) {
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
                }
                DialogInterface.OnClickListener discardButtonClickListener = new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    }
                };
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!mChangeMade) {
            super.onBackPressed();
            return;
        } else {
            DialogInterface.OnClickListener discardButtonClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            };
            showUnsavedChangesDialog(discardButtonClickListener);
        }
    }

    /**
     * updates if modifying or inserts new data based on user input's
     */
    private long updateOrInsertData() {
        long newRowID = NOT_ENOUGH_DATA;
        try {
            ContentValues values;
            if (isEditPetCall()) {
                Pet pet = getDataFromInputs();
                values = new ContentValues();
                values.put(PetEntry.COLUMN_PET_NAME, pet.getName());
                values.put(PetEntry.COLUMN_PET_BREED, pet.getBreed());
                values.put(PetEntry.COLUMN_PET_GENDER, pet.getGender());
                values.put(PetEntry.COLUMN_PET_WEIGHT, pet.getWeight());
                int rowsAffected = getContentResolver().update(mPetToEditUri, values, null, null);
                mChangeMade = false;
                showRowsAffectedToast(rowsAffected, R.string.toast_update_success, R.string.toast_update_failure);
            } else {
                Pet pet = getDataFromInputs();

                values = new ContentValues();
                values.put(PetEntry.COLUMN_PET_NAME, pet.getName());
                values.put(PetEntry.COLUMN_PET_BREED, pet.getBreed());
                values.put(PetEntry.COLUMN_PET_GENDER, pet.getGender());
                values.put(PetEntry.COLUMN_PET_WEIGHT, pet.getWeight());

                Uri resultUri = getContentResolver().insert(PetEntry.CONTENT_URI, values);
                mChangeMade = false;
                long id = ContentUris.parseId(resultUri);
                showActionSaveToast(id);
            }

        } catch (NotEnoughDataProvidedException e) {
            Toast.makeText(this, R.string.toast_must_provide_name, Toast.LENGTH_SHORT).show();
        } catch (WeightNegativeException e) {
            Toast.makeText(this, R.string.toast_negative_weight, Toast.LENGTH_SHORT).show();
        }
        return newRowID;
    }

    /**
     * getting data from input View's
     *
     * @return Pet, created from user's input
     */
    private Pet getDataFromInputs() throws NotEnoughDataProvidedException {
        String breed = this.mBreedEditText.getText().toString().trim();
        String name = this.mNameEditText.getText().toString().trim();
        if (name == null || TextUtils.isEmpty(name)) {
            throw new NotEnoughDataProvidedException(R.string.toast_must_provide_name + "");
        }
        int weight;
        String weightInput = mWeightEditText.getText().toString().trim();
        if (TextUtils.isEmpty(weightInput)) {
            weight = 0;
        } else {
            weight = Integer.parseInt(weightInput);
        }
        Pet pet = new Pet(name, breed, mGender, weight);
        return pet;
    }

    /**
     * shows message based on saving result ( saved successfully or not)
     *
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

    private void showRowsAffectedToast(int rowsAffected, int messageSuccessResource, int messageFailureResource) {
        if (rowsAffected > 0) {
            Toast.makeText(this, getResources().getString(messageSuccessResource), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getResources().getString(messageFailureResource), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] columns = new String[]{PetEntry._ID, PetEntry.COLUMN_PET_NAME, PetEntry.COLUMN_PET_BREED, PetEntry.COLUMN_PET_GENDER, PetEntry.COLUMN_PET_WEIGHT};
        return new CursorLoader(this, mPetToEditUri, columns, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {
            String petName = data.getString(data.getColumnIndex(PetEntry.COLUMN_PET_NAME));
            String petBreed = data.getString(data.getColumnIndex(PetEntry.COLUMN_PET_BREED));
            int weight = data.getInt(data.getColumnIndex(PetEntry.COLUMN_PET_WEIGHT));
            int gender = data.getInt(data.getColumnIndex(PetEntry.COLUMN_PET_BREED));

            mNameEditText.setText(petName);
            mBreedEditText.setText(petBreed);
            mWeightEditText.setText("" + weight);
            switch (gender) {
                case PetEntry.GENDER_MALE:
                    mGenderSpinner.setSelection(1);
                    break;
                case PetEntry.GENDER_FEMALE:
                    mGenderSpinner.setSelection(2);
                    break;
                default:
                    mGenderSpinner.setSelection(0);
                    break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mNameEditText.setText("");
        mBreedEditText.setText("");
        mWeightEditText.setText("");
        mGenderSpinner.setSelection(0); // unknown gender
    }

    private void setActivityTitle(int stringResource) {
        setTitle(getResources().getString(stringResource));
    }

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_message);
        builder.setPositiveButton(R.string.discard_changes, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_message);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deletePet();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the deletion of the pet in the database.
     */
    private void deletePet() {
        if (mPetToEditUri != null) {
            if (!mChangeMade) {
                int rowsDeleted = getContentResolver().delete(mPetToEditUri, null, null);
                showRowsAffectedToast(rowsDeleted, R.string.editor_delete_pet_successful, R.string.editor_delete_pet_failed);
                finish();
            } else {
                Toast.makeText(this, R.string.save_changes_before_deleting, Toast.LENGTH_SHORT).show();
            }
        }
    }

}