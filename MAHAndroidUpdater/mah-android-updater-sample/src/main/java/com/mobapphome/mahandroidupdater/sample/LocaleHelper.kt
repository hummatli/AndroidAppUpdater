package com.mobapphome.mahandroidupdater.sample

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.preference.PreferenceManager
import android.util.Log

import com.mobapphome.mahandroidupdater.tools.Constants
import com.mobapphome.mahandroidupdater.tools.MAHUpdaterController

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

    fun onCreate(context: Context) {
        val lang = getPersistedData(context, Locale.getDefault().language)
        setLocale(context, lang)
    }

    fun onCreate(context: Context, defaultLanguage: String) {
        val lang = getPersistedData(context, defaultLanguage)
        setLocale(context, lang)
    }

    fun getLanguage(context: Context): String {
        return getPersistedData(context, Locale.getDefault().language)
    }

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


//    //This for tip
//    //This for refreshing strings when lacale has changed.
//    //Write this kind of method to activity or fragment
//    private void updateViews() {
//        // if you want you just call activity to restart itself to redraw all the widgets with the correct locale
//        // however, it will cause a bad look and feel for your users
//        //
//        // this.recreate();
//
//        //or you can just update the visible text on your current layout
//        Resources resources = getResources();
//
//        mTitleTextView.setText(resources.getString(R.string.title_text));
//        mDescTextView.setText(resources.getString(R.string.desc_text));
//        mAboutTextView.setText(resources.getString(R.string.about_text));
//    }