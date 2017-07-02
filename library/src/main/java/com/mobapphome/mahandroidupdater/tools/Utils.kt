package com.mobapphome.mahandroidupdater.tools

import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.FragmentActivity

fun checkPackageIfExists(context: Context, pckgName: String): Boolean =
        try {
            context.packageManager.getApplicationInfo(pckgName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }

fun getVersionCode(act: FragmentActivity): Int =
        try {
            val pInfo = act.packageManager.getPackageInfo(act.packageName, 0)
            val versionCode = pInfo.versionCode
            versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            -1
        }







