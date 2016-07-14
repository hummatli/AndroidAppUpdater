package com.mobapphome.mahandroidupdater.tools;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mobapphome.mahandroidupdater.types.ProgramInfo;

import android.util.Log;

public class HttpTools {

	static public ProgramInfo requestProgramInfo(String url)
			throws IOException {

		ProgramInfo ret = null;

		Document doc = Jsoup
				.connect(url.trim())
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
				.header("Referer", url.trim())
				// This is Needed
				.header("Accept-Encoding", "gzip,deflate,sdch")
				.header("Accept-Language", "en-US,en;q=0.8,ru;q=0.6")
				// .userAgent("Mozilla")
				.get();

		
		 String jsonStr = doc.body().text();
		 //Log.i("Test", jsonStr);

		 try{
			 JSONObject reader = new JSONObject(jsonStr);
			 ret = new ProgramInfo();

			 try{
				 ret.setRunMode(reader.getBoolean("is_run_mode"));
			 }catch (JSONException e){
				Log.i("Test", "MAH Updater is_run_mode is not available");
			 }
			 ret.setName(reader.getString("name"));
			 ret.setUpdateDate(reader.getString("update_date"));
			 ret.setUpdateInfo(reader.getString("update_info"));
			 ret.setUriCurrent(reader.getString("uri_current"));
			 ret.setVersionCodeCurrent(reader.getInt("version_code_current"));
			 ret.setVersionCodeMin(reader.getInt("version_code_min"));

		 }catch(JSONException e){
			 Log.i("Test",e.toString());
		 }

		return ret;

	}

}
