package com.mobapphome.androidappupdater.commons

import android.graphics.Typeface
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mobapphome.androidappupdater.tools.AAUpdaterController

/**
 * Created by settar on 6/23/17.
 */


fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
    TODO("Bu metod hele ishlemir, context == null")
}


fun TextView.setControllerFont() {
    if (AAUpdaterController.fontName == null) return

    try {
        val font = Typeface.createFromAsset(this.context.assets, AAUpdaterController.fontName)
        this.typeface = font
    } catch (r: RuntimeException) {
        Log.e("test", "Error " + r.message)
    }
}

fun TextView.decorateAsLink() {
    this.movementMethod = LinkMovementMethod.getInstance()
}