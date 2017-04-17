package dev.kb.cityweatherforecast;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

/**
 * Created by Karol on 2017-04-14.
 */

public abstract class SavedStringsUtils {

    //my name for file where to store preferences
    private static final String SAVED_PREFERENCES_NAME = "saved_prefs";
    // key to call for saved cities
    public static final String SAVED_CITIES_KEY = "saved_cities_key";
    // separator for saved cities
    public static final String SPLIT_CITIES = ",";
    // limit for saved cities
    public static final int CITIES_LIMIT = 3;

    /**
     * if not present yet method adds given string item under given string key value
     * if you want to limit saved items consider using this method with additional parameter int maxItemsSaved
     */
    public static void addStringItem(Activity activity, String item, String valuesKey, String splitString) {
        String savedItems = getStringFromPreferences(activity, null, valuesKey);
        if (savedItems != null) {
            String[] items = convertStringToArray(savedItems, splitString);
            if (!containsItem(items, item)) {
                savedItems = item + splitString + savedItems;
            }
        } else {
            savedItems = item;
        }
        saveStringToPreferences(activity, savedItems, valuesKey);
    }

    /**
     * if not present yet method adds given string item under given string key value
     * if item is not presented but will exceed maxItemsSaved number, then it replace last one
     */
    public static void addStringItem(Activity activity, String item, String valuesKey, String splitString, int maxItemsSaved) {
        String savedItems = getStringFromPreferences(activity, null, valuesKey);
        if (savedItems != null) {
            String[] items = convertStringToArray(savedItems, splitString);
            if (!containsItem(items, item)) {
                if (items.length < maxItemsSaved) {
                    savedItems = item + splitString + savedItems;
                } else {
                    savedItems = convertArrayToString(replaceLastAndAdd(items, item), splitString);
                }
            }
        } else {
            savedItems = item;
        }
        saveStringToPreferences(activity, savedItems, valuesKey);
    }

    /**
     * returns String array with saved values based on given key and used split
     */
    public static String[] getSavedValues(Activity activity, String valuesKey, String splitString) {
        String savedItems = getStringFromPreferences(activity, null, valuesKey);
        return convertStringToArray(savedItems, splitString);
    }

    /**
     * inserts item into array and moves all elements into one position further replacing last value
     */
    private static <T> T[] replaceLastAndAdd(T[] items, T item) {
        for (int i = items.length - 1; i > 0; i--) {
            items[i] = items[i - 1];
        }
        items[0] = item;
        return items;
    }

    /**
     * saves given value under given key to SharedPreferences
     */
    private static void saveStringToPreferences(Activity activity, String valueToSave, String valueKey) {
        SharedPreferences preferences = activity.getSharedPreferences(SAVED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        Editor preferencesEditor = preferences.edit();
        preferencesEditor.putString(valueKey, valueToSave);
        preferencesEditor.apply();
    }

    /**
     * retrieves value under given key from shared preferences
     */
    private static String getStringFromPreferences(Activity activity, String defaultValue, String key) {
        SharedPreferences preferences = activity.getSharedPreferences(SAVED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        String tempString = preferences.getString(key, defaultValue);
        return tempString;
    }

    /**
     * converts given string to array of strings based on given split string
     */
    private static String[] convertStringToArray(String toConvert, String splitString) {
        String[]items = null;
        if(toConvert != null && !TextUtils.isEmpty(toConvert)) {
            toConvert = capitalizeString(toConvert, splitString);
            items = toConvert.split(splitString);
        }
        return items;
    }

    /**
     * converts given array of Strings into single string using splitString as separator
     */
    private static String convertArrayToString(String[] arrayToConvert, String splitString) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arrayToConvert.length; i++) {
            sb.append(arrayToConvert[i]);
            if (i < arrayToConvert.length - 1) {
                sb.append(splitString);
            }
        }
        return sb.toString();
    }

    /**
     * checks if given String item is already present in given String array
     */
    private static boolean containsItem(String[] items, String item) {
        item = item.trim().toLowerCase();
        for (String i : items) {
            i = i.trim().toLowerCase();
            if (i.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public static String capitalizeString(String string, String splitString) {
        char[] chars = string.toCharArray();
        boolean splitOneChar = splitString.length() == 1 ? true : false;
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || (splitOneChar && splitString.charAt(0) == chars[i])) {
                found = false;
            }
        }
        return String.valueOf(chars);
    }
}
