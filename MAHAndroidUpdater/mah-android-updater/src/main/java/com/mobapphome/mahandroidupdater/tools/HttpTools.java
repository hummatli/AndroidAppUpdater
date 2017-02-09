package com.mobapphome.mahandroidupdater.tools;

import android.util.Log;

import com.mobapphome.mahandroidupdater.types.ProgramInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpTools {

    static public ProgramInfo requestProgramInfo(String url)
            throws IOException {

        ProgramInfo ret = null;

        URL uri = new URL(url.trim());
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();

        conn.setRequestMethod("GET");
        conn.setConnectTimeout(3000);
        conn.setReadTimeout(3000);

        conn.setRequestProperty("Cache-Control", "max-age=0");
        conn.setRequestProperty("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        conn.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Referer", url.trim());
        // This is Needed
        conn.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.8,ru;q=0.6");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        String jsonStr = response.toString();
        //Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, jsonStr);

        try {
            JSONObject reader = new JSONObject(jsonStr);
            ret = new ProgramInfo();

            try {
                ret.setRunMode(reader.getBoolean("is_run_mode"));
            } catch (JSONException e) {
                Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "MAH Updater is_run_mode is not available");
            }
            ret.setName(reader.getString("name"));
            ret.setUpdateDate(reader.getString("update_date"));
            ret.setUpdateInfo(reader.getString("update_info"));
            ret.setUriCurrent(reader.getString("uri_current"));
            ret.setVersionCodeCurrent(reader.getInt("version_code_current"));
            ret.setVersionCodeMin(reader.getInt("version_code_min"));

        } catch (JSONException e) {
            Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, e.toString());
        }

        return ret;

    }

}
