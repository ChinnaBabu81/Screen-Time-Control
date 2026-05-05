kotlin
package com.example.smartscreentime

import android.content.Context

class TimeManager(private val context: Context) {

    private val prefsManager = PrefsManager(context)

    fun isInCooldown(): Boolean {
        val lastBlockTime = prefsManager.getLastBlockTime()
        if (lastBlockTime == 0L) return false

        val currentTime = System.currentTimeMillis()
        val cooldownMillis = prefsManager.getCooldownPeriod() * 60 * 1000L
        val timeSinceBlock = currentTime - lastBlockTime

        return timeSinceBlock < cooldownMillis
    }

    fun getRemainingCooldownMinutes(): Int {
        if (!isInCooldown()) return 0

        val lastBlockTime = prefsManager.getLastBlockTime()
        val currentTime = System.currentTimeMillis()
        val cooldownMillis = prefsManager.getCooldownPeriod() * 60 * 1000L
        val remainingMillis = cooldownMillis - (currentTime - lastBlockTime)

        return (remainingMillis / 60000).toInt()
    }

    fun startSession() {
        prefsManager.setSessionStartTime(System.currentTimeMillis())
    }

    fun hasExceededTimeLimit(): Boolean {
        val sessionStart = prefsManager.getSessionStartTime()
        if (sessionStart == 0L) return false

        val currentTime = System.currentTimeMillis()
        val sessionDuration = currentTime - sessionStart
        val timeLimitMillis = prefsManager.getTimeLimit() * 60 * 1000L

        return sessionDuration >= timeLimitMillis
    }

    fun getSessionDurationMinutes(): Int {
        val sessionStart = prefsManager.getSessionStartTime()
        if (sessionStart == 0L) return 0

        val currentTime = System.currentTimeMillis()
        val sessionDuration = currentTime - sessionStart

        return (sessionDuration / 60000).toInt()
    }

    fun blockApp() {
        prefsManager.setLastBlockTime(System.currentTimeMillis())
        prefsManager.resetSession()
    }

    fun resetCooldown() {
        prefsManager.setLastBlockTime(0)
    }

    fun shouldBlockApp(): Boolean {
        return isInCooldown() || hasExceededTimeLimit()
    }
}
