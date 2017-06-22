package com.mobapphome.mahandroidupdater.tools

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.support.v4.app.FragmentActivity

fun checkPackageIfExists(context: Context, pckgName: String): Boolean {
    try {
        val info = context.packageManager.getApplicationInfo(pckgName, 0)
        return true
    } catch (e: PackageManager.NameNotFoundException) {
        return false
    }

}

fun getVersionCode(act: FragmentActivity): Int {
    try {
        val pInfo = act.packageManager.getPackageInfo(act.packageName, 0)
        val versionCode = pInfo.versionCode
        return versionCode
    } catch (e: PackageManager.NameNotFoundException) {
        return -1
    }

}





