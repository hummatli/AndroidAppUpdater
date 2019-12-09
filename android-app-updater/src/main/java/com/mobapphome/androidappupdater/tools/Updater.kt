package com.mobapphome.androidappupdater.tools

import android.app.Activity
import android.util.Log

import com.google.gson.Gson
import com.mobapphome.androidappupdater.R

import java.io.IOException

class Updater {
    internal var updaterListiner: UpdaterListener? = null
    var loading = false

    fun updateProgramList(act: Activity) {
        Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "Update info called")
        Thread(Runnable {
            synchronized(Updater::class.java) {
                if (loading) {
                    Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "Accept_3")
                    Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "Loading")
                    return@Runnable
                }
                loading = true
                try {
                    Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "Service requested")

                    var programInfo: ProgramInfo? = null

                    if (AAUpdaterController.urlService != null) {
                        programInfo = HttpTools.requestProgramInfo(AAUpdaterController.urlService)

                    } else if (AAUpdaterController.updateInfoResolver != null) {
                        programInfo = AAUpdaterController.updateInfoResolver?.resolveInfo()
                    }

                    Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "Program info name = " + programInfo)

                    val gson = Gson()
                    val json = gson.toJson(programInfo)
                    AAUpdaterController.sharedPref!!.edit().putString(Constants.MAH_UPD_PROGRAM_INFO, json).apply()

                    if (updaterListiner != null) {
                        Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "UpdateListener = $updaterListiner")
                        updaterListiner!!.onResponse(programInfo)
                    }
                    loading = false
                } catch (e: IOException) {
                    Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "Accept_6")

                    if (AAUpdaterController.urlService != null) {
                        Log.d(Constants.MAH_ANDROID_UPDATER_LOG_TAG, " " + e.message + "URL = " + AAUpdaterController.urlService, e)
                    } else if (AAUpdaterController.updateInfoResolver != null) {
                        Log.d(Constants.MAH_ANDROID_UPDATER_LOG_TAG, " " + e.message + "updateInfoResolver =  " + AAUpdaterController.updateInfoResolver?.javaClass?.simpleName, e)

                    }


                    val resultError = StringBuilder()
                    resultError.append(act.resources.getString(
                            R.string.android_app_upd_internet_update_error))
                    if (updaterListiner != null) {
                        val gson = Gson()
                        val json = AAUpdaterController.sharedPref?.getString(Constants.MAH_UPD_PROGRAM_INFO, null)
                        val programInfo = gson.fromJson(json, ProgramInfo::class.java)

                        //Bura bax "?: ProgramInfo()" yazmisham. Onun yerinde bashqa shey ola biler. test ucn qoyusham
                        updaterListiner!!.onResponse(programInfo ?: ProgramInfo(), resultError.toString())
                    }
                    loading = false
                }

            }
        }).start()
    }
}
