@file:JvmName("CustomWindow")

package com.rahul.imgur.ui.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahul.imgur.R
import com.rahul.imgur.ui.adapters.ImagesListAdapter

/**
 * extension [debounce] generates the debounce
 * effect cancels the callbacks with in 250 milliseconds
 */
fun String?.debounce(callback: () -> Unit) {
    if (debounceRunnable != null) {
        debounceHandler.removeCallbacks(debounceRunnable!!)
    }
    debounceRunnable = Runnable {
        callback()
    }
    debounceHandler.postDelayed(debounceRunnable!!, 250)
}

var debounceRunnable: Runnable? = null
var debounceHandler: Handler = Handler(Looper.getMainLooper())

/**
 * extension [getGridLayoutManager] consists code for dynamic [GridLayoutManager]
 * to provide different spanCount for different views
 */
fun Context.getGridLayoutManager(
    orientationPortrait: Int,
    adapter: ImagesListAdapter
): GridLayoutManager {

    var spanCount = when (orientationPortrait) {
        Configuration.ORIENTATION_PORTRAIT -> 2
        Configuration.ORIENTATION_LANDSCAPE -> 3
        else -> 3
    }
    return GridLayoutManager(
        this, spanCount,
        GridLayoutManager.VERTICAL, false
    ).apply {
        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    R.layout.images_row -> 1
                    R.layout.network_state_item -> spanCount
                    else -> 1
                }
            }
        }
    }
}

/**
 * extension [getLinearLayoutManager] consists code for dynamic [LinearLayoutManager]
 * to provide different spanCount for different views
 */
fun Context.getLinearLayoutManager(
    orientationPortrait: Int,
    adapter: ImagesListAdapter
): LinearLayoutManager {

    return LinearLayoutManager(
        this,
        LinearLayoutManager.VERTICAL, false
    ).apply {

    }
}

/**
 * extension [setupTheme] calls the [AppCompatDelegate] methods which
 * setup the theme whenever the configuration is changed
 */
fun SharedPreferences?.setupTheme(key: String?, resources: Resources) {
    var value = this?.getString(key, "")
    val def = if (resources.configuration.uiMode and
        Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    ) {
        AppCompatDelegate.MODE_NIGHT_YES

    } else {
        AppCompatDelegate.MODE_NIGHT_NO
    }

    when (value) {
        "2" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        "1" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        else -> AppCompatDelegate.setDefaultNightMode(def)
    }
}
