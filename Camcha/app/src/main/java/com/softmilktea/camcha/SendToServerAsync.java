package com.softmilktea.camcha;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

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

public class SendToServerAsync extends AsyncTask<String/*parameter of doInBackground*/, Void/*parameter of onPreExecute, onProgressUpdate*/, Void/*return data of doInBackground*/> {
    private String mQuery;
    private final Map<String, String> QUERY_MAP;

    public SendToServerAsync(String query) {
        this.mQuery = query;

        QUERY_MAP = new HashMap<>();
        QUERY_MAP.put("SEND_DETECTION_RESULT", "/detections/");
    }

    @Override
    protected Void doInBackground(String... params) {
        String jsonString = params[0];
        Dlog.e(jsonString);

        try {
            URL url = new URL(BaseApplication.SERVER_ADDRESS + QUERY_MAP.get(mQuery));
            Dlog.e(BaseApplication.SERVER_ADDRESS + QUERY_MAP.get(mQuery));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            byte[] outputBytes = jsonString.getBytes("UTF-8");
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.connect();

            OutputStream os = urlConnection.getOutputStream();
            os.write(outputBytes);
            os.close();

            if (urlConnection.getResponseCode() != HttpsURLConnection.HTTP_CREATED) {
                Dlog.e("망함");
            }
        }
        catch (Exception e) {
            Dlog.e(e.getMessage());
        }

        return null;
    }
}