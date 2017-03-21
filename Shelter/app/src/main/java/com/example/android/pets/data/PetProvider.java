package com.example.android.pets.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import com.example.android.pets.NotEnoughDataProvidedException;
import com.example.android.pets.WeightNegativeException;
import com.example.android.pets.data.PetContract.PetEntry;

/**
 * Created by Karol on 2017-03-03.
 */

public class PetProvider extends ContentProvider {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG_PET_PROVIDER = PetProvider.class.getSimpleName();
    private PetDbHelper petHelper;
    private static final int PETS = 100;
    private static final int PET_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetContract.PATH_PETS, PETS);
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetContract.PATH_PETS + "/#", PET_ID);
    }

    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        petHelper = new PetDbHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database = petHelper.getReadableDatabase();

        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                cursor = database.query(PetEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PET_ID:
                selection = PetContract.PetEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(PetEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                return insertPet(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch(match){
            case PETS:
                return updatePet(uri, contentValues, selection, selectionArgs);
            case PET_ID:
                selection = PetEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updatePet(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for: " + uri);
        }
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = petHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                return database.delete(PetEntry.TABLE_NAME, selection, selectionArgs);
            case PET_ID:
                selection = PetEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return database.delete(PetEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri){
        final int match = sUriMatcher.match(uri);
        switch(match){
            case PETS:
                return PetEntry.CONTENT_LIST_TYPE;
            case PET_ID:
                return PetEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknow URI: " + uri + " with match " + match);
        }
    }

    private Uri insertPet(Uri uri, ContentValues values) {
        int weight = values.getAsInteger(PetEntry.COLUMN_PET_WEIGHT);
        if(weight < 0){
            throw new WeightNegativeException("Weight cannot be negative value.");
        }
        SQLiteDatabase db = petHelper.getWritableDatabase();
        long id = db.insert(PetEntry.TABLE_NAME, null, values);
        return ContentUris.withAppendedId(uri, id);
    }

    private int updatePet(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs){
        if(contentValues.size() == 0){
            return 0;
        }
        SQLiteDatabase db = petHelper.getWritableDatabase();
        if(contentValues.containsKey(PetEntry.COLUMN_PET_WEIGHT)){
            int weight = contentValues.getAsInteger(PetEntry.COLUMN_PET_WEIGHT);
            if(weight < 0){
                throw new WeightNegativeException("Weight cannot be negative value.");
            }
        }
        if(contentValues.containsKey(PetEntry.COLUMN_PET_NAME)){
            String name = contentValues.getAsString(PetEntry.COLUMN_PET_NAME);
            if(name == null || TextUtils.isEmpty(name)){
                throw new NotEnoughDataProvidedException();
            }
        }
        long rowsAffected = db.update(PetEntry.TABLE_NAME, contentValues, selection, selectionArgs);
        return (int)rowsAffected;
    }
}
