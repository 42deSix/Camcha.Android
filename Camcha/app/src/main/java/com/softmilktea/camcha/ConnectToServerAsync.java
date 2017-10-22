package com.softmilktea.camcha;

import android.os.AsyncTask;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ConnectToServerAsync extends AsyncTask<Void,Void,Void> {
    InputStream inputStream;
    HttpURLConnection urlConnection;
    byte[] outputBytes;
    String query;
    String responseData = "fail";

    public ConnectToServerAsync(String query) {
        this.query = query;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            /* forming th java.net.URL object */
            URL url = new URL(BaseApplication.SERVER_ADDRESS);
            urlConnection = (HttpURLConnection) url.openConnection();

            /* pass post data */
            outputBytes = query.getBytes("UTF-8");
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.connect();
            OutputStream os = urlConnection.getOutputStream();
            os.write(outputBytes);
            os.close();

            /* Get Response and execute WebService request*/
            int statusCode = urlConnection.getResponseCode();
            Dlog.e(statusCode+"");

            /* 200 represents HTTP OK */
            if (statusCode == HttpsURLConnection.HTTP_OK) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
//                    responseData= convertStreamToString(inputStream);
                responseData = "success";
            }
        }
        catch (Exception e) {
//                e.printStackTrace();
            Dlog.e(e.getMessage());
        }

        Dlog.e(responseData);

        return null;
    }
}