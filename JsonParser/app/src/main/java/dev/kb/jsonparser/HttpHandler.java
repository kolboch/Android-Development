package dev.kb.jsonparser;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Karol on 2017-01-17.
 */

public class HttpHandler {

    private static final String LOGS_TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {
    }

    public String makeServiceCall(String requestURL) {
        String response = null;

        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream is = new BufferedInputStream(connection.getInputStream());
            response = convertStreamToString(is);
        } catch (MalformedURLException e) {
            Log.e(LOGS_TAG, "MalformedURLException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(LOGS_TAG, "IOException: " + e.getMessage());
        }

        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            Log.e(LOGS_TAG, "IOException: " + e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.e(LOGS_TAG, "IOException when closing InputStream: " + e.getMessage());
            }
        }

        return sb.toString();
    }
}