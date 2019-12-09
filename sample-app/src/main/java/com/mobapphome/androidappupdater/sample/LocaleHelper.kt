package com.mobapphome.androidappupdater.sample

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log

import com.mobapphome.androidappupdater.tools.Constants

import java.util.Locale

/**
 * This class is used to change your application locale and persist this change for the next time
 * that your app is going to be used.
 *
 *
 * You can also change the locale of your application on the fly by using the setLocale method.
 *
 *
 * Created by gunhansancar on 07/10/15.
 */
object LocaleHelper {

    private val SELECTED_LANGUAGE = "MAHAds.Locale.Helper.Selected.Language"

    @JvmStatic
    fun onCreate(context: Context, defaultLanguage: String = Locale.getDefault().language) {
        val lang = getPersistedData(context, defaultLanguage)
        setLocale(context, lang)
    }

    @JvmStatic
    fun getLanguage(context: Context): String {
        return getPersistedData(context, Locale.getDefault().language)
    }

    @JvmStatic
    fun setLocale(context: Context, language: String) {
        Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "Language = " + language)
        persist(context, language)
        updateResources(context, language)
    }

    private fun getPersistedData(context: Context, defaultLanguage: String): String {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage)
    }

    private fun persist(context: Context, language: String) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()

        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
    }

    private fun updateResources(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources

        val configuration = resources.configuration
        configuration.locale = locale

        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}