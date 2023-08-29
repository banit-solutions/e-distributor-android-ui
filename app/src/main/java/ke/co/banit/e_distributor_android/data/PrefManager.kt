package ke.co.banit.e_distributor_android.data

import android.content.Context
import android.content.SharedPreferences
import ke.co.banit.e_distributor_android.App
import ke.co.banit.e_distributor_android.util.encryptKey

/**
 * @Author: Angatia Benson
 * @Date: 17/08/2022
 * Copyright (c) 2022 Bantechnis
 */
class PrefManager {
    private val PREF_NAME = "digipay"
    private val isLoggedIn = "isLoggedIn"
    private val sessionToken = "session-token"
    private val KEY_DARK_MODE = "dark_mode"

    private var pref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null


    init {
        pref =
            App.application.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = pref!!.edit()
    }

    fun getPref(): SharedPreferences? {
        return pref
    }

    fun isLoggedIn(): Boolean {
        return pref!!.getBoolean(isLoggedIn, false)
    }

    fun getSessionToken(): String {
        return pref!!.getString(sessionToken, encryptKey())!!
    }

    fun setLoggedIn(mSessionToken: String) {
        editor!!.putString(sessionToken, mSessionToken)
        editor!!.putBoolean(isLoggedIn, true)
        editor!!.commit()
    }

    fun logout() {
        editor!!.clear()
        editor!!.apply()
    }

    fun setDarkModeEnabled(enabled: Boolean) {
        editor!!.putBoolean(KEY_DARK_MODE, enabled).apply()
    }

    fun isDarkModeEnabled(): Boolean {
        return pref!!.getBoolean(KEY_DARK_MODE, false)
    }
}