package com.mobapphome.mahandroidupdater.tools

import java.io.IOException

import org.json.JSONException
import org.json.JSONObject
import org.jsoup.Jsoup

import android.util.Log

object HttpTools {

    @Throws(IOException::class)
    fun requestProgramInfo(url: String): ProgramInfo? {

        val doc = Jsoup
                .connect(url.trim { it <= ' ' })
                .ignoreContentType(true)
                .timeout(3000)
                // .header("Host", "85.132.44.28")
                .header("Connection", "keep-alive")
                // .header("Content-Length", "111")
                .header("Cache-Control", "max-age=0")
                .header("Accept",
                        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                // .header("Origin", "http://85.132.44.28")
                .header("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Referer", url.trim { it <= ' ' })
                // This is Needed
                .header("Accept-Encoding", "gzip,deflate,sdch")
                .header("Accept-Language", "en-US,en;q=0.8,ru;q=0.6")
                // .userAgent("Mozilla")
                .get()


        val jsonStr = doc.body().text()
        //Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, jsonStr);

        try {
            val reader = JSONObject(jsonStr)

//            try {
               // ret.isRunMode =
//            } catch (e: JSONException) {
//                Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "MAH Updater is_run_mode is not available")
//            }

            return ProgramInfo(
                    name = reader.getString("name"),
                    updateDate = reader.getString("update_date"),
                    updateInfo = reader.getString("update_info"),
                    uriCurrent = reader.getString("uri_current"),
                    versionCodeCurrent = reader.getInt("version_code_current"),
                    versionCodeMin = reader.getInt("version_code_min"),
                    isRunMode = reader.getBoolean("is_run_mode"))
        } catch (e: JSONException) {
            Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, e.toString())
            return null
        }
    }
}
