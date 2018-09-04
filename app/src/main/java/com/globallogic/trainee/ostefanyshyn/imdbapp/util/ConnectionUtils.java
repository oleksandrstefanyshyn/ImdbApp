package com.globallogic.trainee.ostefanyshyn.imdbapp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionUtils {

    private final static String TAG = ConnectionUtils.class.getSimpleName();

    public static String getStringResultFromUrl(String urlString) {
        HttpURLConnection connection = null;

        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(getInputStream(urlString)))
        ) {
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
                Log.d(TAG, "> " + line);

            }
            return builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    public static Bitmap getBitmapFromUrl(String stringUrl) {
        return BitmapFactory.decodeStream(getInputStream(stringUrl));
    }

    private static InputStream getInputStream(String stringUrl) {
        InputStream stream = null;
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            stream = connection.getInputStream();
            return stream;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream;
    }
}
