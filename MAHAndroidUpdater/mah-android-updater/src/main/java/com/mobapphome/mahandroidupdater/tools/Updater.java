package com.mobapphome.mahandroidupdater.tools;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.mobapphome.mahandroidupdater.R;
import com.mobapphome.mahandroidupdater.types.ProgramInfo;

import java.io.IOException;

public class Updater {
    UpdaterListener updaterListiner;
    public boolean loading = false;

    public void setUpdaterListiner(UpdaterListener updaterListiner) {
        this.updaterListiner = updaterListiner;
    }

    public void updateProgramList(final Activity act) {
        Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "Update info called");
        new Thread(new Runnable() {

            @Override
            public void run() {
                synchronized (Updater.class) {
                    if (loading) {
                        Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "Accept_3");
                        Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "Loading");
                        return;
                    }
                    loading = true;
                    try {
                        Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "Service requested");

                        ProgramInfo programInfo = null;

                        if (MAHUpdaterController.urlService != null) {
                            programInfo = HttpTools.requestProgramInfo(MAHUpdaterController.urlService);

                        } else if (MAHUpdaterController.updateInfoResolver != null) {
                            programInfo = MAHUpdaterController.updateInfoResolver.resolveInfo();
                        }

                        Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "Program info name = " + programInfo);

                        Gson gson = new Gson();
                        String json = gson.toJson(programInfo);
                        MAHUpdaterController.getSharedPref().edit().putString(Constants.MAH_UPD_PROGRAM_INFO, json).apply();

                        if (updaterListiner != null) {
                            updaterListiner.onResponse(programInfo, null);
                        }
                        loading = false;
                    } catch (IOException e) {
                        Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "Accept_6");

                        if (MAHUpdaterController.urlService != null) {
                            Log.d(Constants.MAH_ANDROID_UPDATER_LOG_TAG, " " + e.getMessage() + "URL = " + MAHUpdaterController.urlService, e);
                        } else if (MAHUpdaterController.updateInfoResolver != null) {
                            Log.d(Constants.MAH_ANDROID_UPDATER_LOG_TAG, " " + e.getMessage() + "updateInfoResolver =  " + MAHUpdaterController.updateInfoResolver.getClass().getSimpleName(), e);

                        }


                        StringBuilder resultError = new StringBuilder();
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
