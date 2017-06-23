package com.mobapphome.mahandroidupdater.tools

import android.app.Activity
import android.util.Log

import com.google.gson.Gson
import com.mobapphome.mahandroidupdater.R

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

                    if (MAHUpdaterController.urlService != null) {
                        programInfo = HttpTools.requestProgramInfo(MAHUpdaterController.urlService)

                    } else if (MAHUpdaterController.updateInfoResolver != null) {
                        programInfo = MAHUpdaterController.updateInfoResolver?.resolveInfo()
                    }

                    Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "Program info name = " + programInfo!!)

                    val gson = Gson()
                    val json = gson.toJson(programInfo)
                    MAHUpdaterController.sharedPref!!.edit().putString(Constants.MAH_UPD_PROGRAM_INFO, json).apply()

                    if (updaterListiner != null) {
                        Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "UpdateListener = $updaterListiner")
                        updaterListiner!!.
                                onResponse(programInfo)
                    }
                    loading = false
                } catch (e: IOException) {
                    Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "Accept_6")

                    if (MAHUpdaterController.urlService != null) {
                        Log.d(Constants.MAH_ANDROID_UPDATER_LOG_TAG, " " + e.message + "URL = " + MAHUpdaterController.urlService, e)
                    } else if (MAHUpdaterController.updateInfoResolver != null) {
                        Log.d(Constants.MAH_ANDROID_UPDATER_LOG_TAG, " " + e.message + "updateInfoResolver =  " + MAHUpdaterController.updateInfoResolver?.javaClass?.simpleName, e)

                    }


                    val resultError = StringBuilder()
                    resultError.append(act.resources.getString(
                            R.string.mah_android_upd_internet_update_error))
                    if (updaterListiner != null) {
                        val gson = Gson()
                        val json = MAHUpdaterController.sharedPref?.getString(Constants.MAH_UPD_PROGRAM_INFO, null)
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
