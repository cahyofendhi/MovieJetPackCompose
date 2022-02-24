package com.bcr.moviejetpackcompose.core.data

import android.content.Context
import android.content.SharedPreferences
import com.bcr.moviejetpackcompose.utils.PreferenceHelper
import com.bcr.moviejetpackcompose.utils.PreferenceHelper.get
import com.bcr.moviejetpackcompose.utils.PreferenceHelper.set

class Session(context: Context) {
    private val APP_ONBOARD = "on_board"
    private val APP_LOGIN = "user_login"

    private val preferences: SharedPreferences = PreferenceHelper.defaultPrefs(context)

    var isOnboardDone: Boolean
        get() = preferences[APP_ONBOARD, false]
        set(value) = preferences.set(APP_ONBOARD, value)

    var isLoginDone: Boolean
        get() = preferences[APP_LOGIN, false]
        set(value) = preferences.set(APP_LOGIN, value)
}
