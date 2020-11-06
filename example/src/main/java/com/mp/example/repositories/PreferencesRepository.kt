package com.jamitlabs.remoteui_sdk.repositories

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferencesRepository(private val context: Context) {
    private val prefs: SharedPreferences
        get() = context.getSharedPreferences("main", Context.MODE_PRIVATE)

    var showOnboarding: Boolean
        get() = prefs.getBoolean("show_onboarding", true)
        set(value) = prefs.edit { putBoolean("show_onboarding", value) }
}