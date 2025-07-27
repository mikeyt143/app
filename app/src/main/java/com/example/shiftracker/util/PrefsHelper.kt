package com.example.shiftracker.util

import android.content.Context
import android.content.SharedPreferences

object PrefsHelper {
    private const val PREFS_NAME = "shift_prefs"
    private const val KEY_WAGE = "last_wage"
    private const val KEY_CURRENCY = "last_currency"

    fun getPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveLastWage(context: Context, wage: Double) {
        getPrefs(context).edit().putFloat(KEY_WAGE, wage.toFloat()).apply()
    }

    fun saveLastCurrency(context: Context, currency: String) {
        getPrefs(context).edit().putString(KEY_CURRENCY, currency).apply()
    }

    fun getLastWage(context: Context): Double =
        getPrefs(context).getFloat(KEY_WAGE, 0f).toDouble()

    fun getLastCurrency(context: Context): String =
        getPrefs(context).getString(KEY_CURRENCY, "$") ?: "$"
}