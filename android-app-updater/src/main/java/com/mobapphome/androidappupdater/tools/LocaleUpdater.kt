package com.mobapphome.androidappupdater.tools

import android.content.Context

import java.util.Locale

object LocaleUpdater {
    private fun updateLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources

        val configuration = resources.configuration
        configuration.locale = locale

        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}