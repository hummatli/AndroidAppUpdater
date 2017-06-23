package com.mobapphome.mahandroidupdater.commons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by settar on 6/23/17.
 */


fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}