package com.globallogic.trainee.ostefanyshyn.imdbapp.util;

import android.util.Log;

import com.globallogic.trainee.ostefanyshyn.imdbapp.entity.MovieDetailsEntity;

import org.json.JSONException;
import org.json.JSONObject;

public class OpenImdbJsonUtils {

    private final static String TAG = OpenImdbJsonUtils.class.getSimpleName();

    public static MovieDetailsEntity getImdbDataStringsFromJson(String imdbDataString)  {
        JSONObject imdbDataJson = OpenImdbJsonUtils.getJSONObjectFromString(imdbDataString);
        MovieDetailsEntity movieDetailsEntity = new MovieDetailsEntity();
            movieDetailsEntity.setTitle(getDetail(imdbDataJson, ConstantUtil.TITLE));
            movieDetailsEntity.setPlot(getDetail(imdbDataJson, ConstantUtil.PLOT));
            movieDetailsEntity.setPosterString(getDetail(imdbDataJson, ConstantUtil.POSTER));
            Log.d(TAG, movieDetailsEntity.toString());
            return movieDetailsEntity;
    }

    private static JSONObject getJSONObjectFromString(String imdbDataString) {
        JSONObject imdbDataJson = null;
        try {
            imdbDataJson = new JSONObject(imdbDataString);
            return imdbDataJson;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imdbDataJson;
    }

    private static String getDetail(JSONObject imdbDataJson, String detail) {
        try {
            return imdbDataJson.getString(detail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ConstantUtil.DEFAULT_DATA;
    }
}
