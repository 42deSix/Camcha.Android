package com.softmilktea.camcha;

import android.app.ProgressDialog;
import android.content.Context;
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

public class ReceiveFromServerAsync extends AsyncTask<Void, Void, String> {
    private String mQuery;
    private Context mContext;
    private final Map<String, String> QUERY_MAP;
    private ProgressDialog mProgressDialog;

    public ReceiveFromServerAsync(String query, Context context) {
        this.mQuery = query;
        this.mContext = context;

        QUERY_MAP = new HashMap<>();
        QUERY_MAP.put("RECEIVE_DETECTION_DATA", "/detections/?format=json");
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("서버로부터 데이터를 받아오고 있습니다.");
        mProgressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        String responseData = "";
        try {
            URL url = new URL(BaseApplication.SERVER_ADDRESS + QUERY_MAP.get(mQuery));
            Dlog.e(BaseApplication.SERVER_ADDRESS + QUERY_MAP.get(mQuery));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            int statusCode = urlConnection.getResponseCode();

            if (statusCode == HttpsURLConnection.HTTP_OK) {
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                Gson gson = new Gson();
                responseData = convertStreamToString(inputStream);
            }
        }
        catch (Exception e) {
            Dlog.e(e.getMessage());
        }

        return responseData;
    }

    @Override
    protected void onPostExecute(String responseData) {
        mProgressDialog.dismiss();
        super.onPostExecute(responseData);
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