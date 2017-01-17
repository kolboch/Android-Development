package dev.kb.jsonparser;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DisplayData extends AppCompatActivity {

    private static final String LOGS_TAG = DisplayData.class.getSimpleName();

    private ArrayList<HashMap<String, String>> listOfPeople;
    private ListView listView;
    private ProgressDialog progressDialog;
    private String dataTitle;

    private static String dataURL = "http://www.mocky.io/v2/58628ab00f0000350e175575";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        listOfPeople = new ArrayList<>();
        listView = (ListView) findViewById(R.id.dataList);
        new GetPeople().execute();
    }

    public void showHideCountryView(View view){
        View holder = (View)view.getParent();
        View country = holder.findViewById(R.id.country);
        if(country.getVisibility() == View.GONE){
            country.setVisibility(View.VISIBLE);
        }
        else{
            country.setVisibility(View.GONE);
        }
    }

    private class GetPeople extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(DisplayData.this);
            progressDialog.setMessage("Loading data...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler handler = new HttpHandler();
            String jsonString = handler.makeServiceCall(dataURL);
            Log.i(LOGS_TAG, "URL response: " + jsonString);

            if (jsonString != null) {

                try {
                    JSONObject mainObj = new JSONObject(jsonString);
                    dataTitle = mainObj.getString("title");

                    JSONArray people = mainObj.getJSONArray("data");
                    for (int i = 0; i < people.length(); i++) {
                        JSONObject personJSON = people.getJSONObject(i);

                        String firstName = personJSON.getString("name");
                        String lastName = personJSON.getString("last_name");
                        String country = personJSON.getString("country");

                        HashMap<String, String> personMap = new HashMap<>();
                        personMap.put("firstName", firstName);
                        personMap.put("lastName", lastName);
                        personMap.put("country", country);
                        listOfPeople.add(personMap);
                    }
                } catch (final JSONException e) { // final to access in toast
                    Log.e(LOGS_TAG, "JSON parsing exception: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "JSON parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                Log.i(LOGS_TAG, "Couldn't get data from server.");
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext()
                                , "Couldn't get json from server. Check LogCat for possible errors!"
                                , Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if(dataTitle != null){
                TextView title = (TextView)findViewById(R.id.dataTitle);
                title.setText(dataTitle);
            }
            ListAdapter adapter = new SimpleAdapter(DisplayData.this, listOfPeople, R.layout.list_item
                    , new String[]{"firstName", "lastName", "country"}
                    , new int[]{R.id.firstName, R.id.lastName, R.id.country
            });
            listView.setAdapter(adapter);
        }
    }
}
