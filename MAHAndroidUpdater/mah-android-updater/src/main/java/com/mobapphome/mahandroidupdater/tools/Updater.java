package com.mobapphome.mahandroidupdater.tools;

import java.io.IOException;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.mobapphome.mahandroidupdater.R;
import com.mobapphome.mahandroidupdater.types.ProgramInfo;

public class Updater {
	UpdaterListener updaterListiner;
	public boolean loading = false;

	public void setUpdaterListiner(UpdaterListener updaterListiner) {
		this.updaterListiner = updaterListiner;
	}

	public void updateProgramList(final Activity act) {
		Log.i("Test", "Update info called");
		new Thread(new Runnable() {

			@Override
			public void run() {
				synchronized (Updater.class) {
					if (loading) {
						Log.i("Test", "Accept_3");
						Log.i("Test", "Loading");
						return;
					}
					loading = true;
					final StringBuffer resultError = new StringBuffer();
					try {
						ProgramInfo programInfo = HttpTools
								.requestProgramInfo(MAHUpdaterController.urlService);


						Gson gson = new Gson();
						String json = gson.toJson(programInfo);
						MAHUpdaterController.getSharedPref().edit().putString(Constants.MAH_UPD_PROGRAM_INFO, json).apply();

						if (updaterListiner != null) {
							updaterListiner.onResponse(programInfo, null);
						}
						loading = false;
					} catch (IOException e) {
						Log.i("Test", "Accept_6");

						Log.i("Test", " " + e.getMessage() +  "URL = " + MAHUpdaterController.urlService);
						
						resultError.append(act.getResources().getString(
								R.string.mah_android_upd_internet_update_error));
						if (updaterListiner != null) {
							Gson gson = new Gson();
							String json = MAHUpdaterController.getSharedPref().getString(Constants.MAH_UPD_PROGRAM_INFO, null);
							ProgramInfo programInfo = gson.fromJson(json, ProgramInfo.class);

							updaterListiner.onResponse(programInfo, resultError.toString());
						}
						loading = false;
					}
				}
			}
		}).start();
	}

}
