kotlin
package com.example.smartscreentime

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class AppUsageService : AccessibilityService() {

    private lateinit var timeManager: TimeManager
    private var currentMonitoredApp: String? = null
    private var isBlocking = false

    companion object {
        private const val TAG = "AppUsageService"
        private const val INSTAGRAM_PACKAGE = "com.instagram.android"
        private val MONITORED_PACKAGES = listOf(INSTAGRAM_PACKAGE)
    }

    override fun onCreate() {
        super.onCreate()
        timeManager = TimeManager(this)
        Log.d(TAG, "AppUsageService Created")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName?.toString() ?: return
            handleAppChange(packageName)
        }
    }

    private fun handleAppChange(packageName: String) {
        // Ignore system apps and our own overlay
        if (packageName == this.packageName || packageName.startsWith("com.android.")) {
            return
        }

        // Check if this is a monitored app
        if (packageName in MONITORED_PACKAGES) {
            handleMonitoredApp(packageName)
        } else {
            // User switched to different app - stop session
            if (currentMonitoredApp != null) {
                Log.d(TAG, "User left monitored app: $currentMonitoredApp")
                currentMonitoredApp = null
                isBlocking = false
            }
        }
    }

    private fun handleMonitoredApp(packageName: String) {
        Log.d(TAG, "Monitored app detected: $packageName")

        // Check if we should block this app
        if (timeManager.isInCooldown()) {
            Log.d(TAG, "App is in cooldown - blocking")
            showBlockOverlay()
            return
        }

        // Start new session if not already tracking
        if (currentMonitoredApp != packageName) {
            currentMonitoredApp = packageName
            timeManager.startSession()
            Log.d(TAG, "Started new session for: $packageName")
        }

        // Check if time limit exceeded
        if (timeManager.hasExceededTimeLimit()) {
            Log.d(TAG, "Time limit exceeded - blocking app")
            timeManager.blockApp()
            showBlockOverlay()
        }
    }

    private fun showBlockOverlay() {
        if (isBlocking) return // Prevent multiple overlays

        isBlocking = true
        val intent = Intent(this, BlockOverlayService::class.java)
        startService(intent)

        // Navigate to home screen
        performGlobalAction(GLOBAL_ACTION_HOME)

        Log.d(TAG, "Block overlay displayed")
    }

    override fun onInterrupt() {
        Log.d(TAG, "Service Interrupted")
    }

    override fun onDestroy() {
        super.onDestroy()
        currentMonitoredApp = null
        isBlocking = false
        Log.d(TAG, "AppUsageService Destroyed")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d(TAG, "AppUsageService Connected")
    }
}
