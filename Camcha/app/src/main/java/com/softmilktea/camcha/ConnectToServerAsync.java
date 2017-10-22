package com.softmilktea.camcha;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class ConnectToServerAsync extends AsyncTask<Void,Void,Void> {
    private String mJsonString;
    private String mQuery;
    private final Map<String, String> QUERY_MAP;

    public ConnectToServerAsync(String query, String jsonString) {
        this.mQuery = query;
        this.mJsonString = jsonString;

        QUERY_MAP = new HashMap<>();
        QUERY_MAP.put("SEND_DETECTION_RESULT", "/detections/");
        QUERY_MAP.put("RECEIVE_DETECTION_DATA", "/detections/?format=json");
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {
            /* forming th java.net.URL object */
            URL url = new URL(BaseApplication.SERVER_ADDRESS + QUERY_MAP.get(mQuery));
            Dlog.e(BaseApplication.SERVER_ADDRESS + QUERY_MAP.get(mQuery));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            /* pass post data */
            byte[] OutputBytes = mJsonString.getBytes("UTF-8");
            if(mQuery == BaseApplication.QUERY_LIST[0]) {
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
            }
            else if(mQuery == BaseApplication.QUERY_LIST[1]) {
                urlConnection.setRequestMethod("GET");
            }
            urlConnection.connect();
            int statusCode = urlConnection.getResponseCode();

            if (statusCode == HttpsURLConnection.HTTP_CREATED) {
                OutputStream os = urlConnection.getOutputStream();
                os.write(OutputBytes);
                os.close();
            }

            if (statusCode == HttpsURLConnection.HTTP_OK) {
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                String responseData = convertStreamToString(inputStream);
                Gson gson = new Gson();
                BaseApplication.RESPONSE_DATA = gson.toJson(responseData);
                Dlog.e(BaseApplication.RESPONSE_DATA);
            }
        }
        catch (Exception e) {
            Dlog.e(e.getMessage());
        }

        return null;
    }

    /**
     * Convert InputStream type data into String.
     * @param is
     * @return
     */
    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append((line + "\n"));
            }
        } catch (IOException e) {
            Dlog.e(e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Dlog.e(e.getMessage());
            }
        }
        return sb.toString();
    }
}