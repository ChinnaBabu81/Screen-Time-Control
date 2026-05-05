kotlin
package com.example.smartscreentime

import android.content.Context
import android.content.SharedPreferences

class PrefsManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "SmartScreenTimePrefs"
        private const val KEY_TIME_LIMIT_MINUTES = "time_limit_minutes"
        private const val KEY_COOLDOWN_MINUTES = "cooldown_minutes"
        private const val KEY_LAST_BLOCK_TIME = "last_block_time"
        private const val KEY_SESSION_START_TIME = "session_start_time"
        private const val KEY_TOTAL_USAGE_TIME = "total_usage_time"

        const val DEFAULT_TIME_LIMIT = 10 // 10 minutes
        const val DEFAULT_COOLDOWN = 30 // 30 minutes
    }

    fun getTimeLimit(): Int {
        return prefs.getInt(KEY_TIME_LIMIT_MINUTES, DEFAULT_TIME_LIMIT)
    }

    fun setTimeLimit(minutes: Int) {
        prefs.edit().putInt(KEY_TIME_LIMIT_MINUTES, minutes).apply()
    }

    fun getCooldownPeriod(): Int {
        return prefs.getInt(KEY_COOLDOWN_MINUTES, DEFAULT_COOLDOWN)
    }

    fun setCooldownPeriod(minutes: Int) {
        prefs.edit().putInt(KEY_COOLDOWN_MINUTES, minutes).apply()
    }

    fun getLastBlockTime(): Long {
        return prefs.getLong(KEY_LAST_BLOCK_TIME, 0)
    }

    fun setLastBlockTime(timestamp: Long) {
        prefs.edit().putLong(KEY_LAST_BLOCK_TIME, timestamp).apply()
    }

    fun getSessionStartTime(): Long {
        return prefs.getLong(KEY_SESSION_START_TIME, 0)
    }

    fun setSessionStartTime(timestamp: Long) {
        prefs.edit().putLong(KEY_SESSION_START_TIME, timestamp).apply()
    }

    fun getTotalUsageTime(): Long {
        return prefs.getLong(KEY_TOTAL_USAGE_TIME, 0)
    }

    fun setTotalUsageTime(milliseconds: Long) {
        prefs.edit().putLong(KEY_TOTAL_USAGE_TIME, milliseconds).apply()
    }

    fun resetSession() {
        prefs.edit()
            .putLong(KEY_SESSION_START_TIME, 0)
            .putLong(KEY_TOTAL_USAGE_TIME, 0)
            .apply()
    }
}
