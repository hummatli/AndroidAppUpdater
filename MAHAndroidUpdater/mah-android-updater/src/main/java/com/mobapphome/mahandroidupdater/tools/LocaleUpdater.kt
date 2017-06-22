package com.mobapphome.mahandroidupdater.tools

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources

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