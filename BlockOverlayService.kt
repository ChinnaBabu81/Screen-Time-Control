kotlin
package com.example.smartscreentime

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat

class BlockOverlayService : Service() {

    private var windowManager: WindowManager? = null
    private var overlayView: View? = null
    private lateinit var timeManager: TimeManager
    private val handler = Handler(Looper.getMainLooper())
    private var timerRunnable: Runnable? = null

    override fun onCreate() {
        super.onCreate()
        timeManager = TimeManager(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (overlayView == null) {
            showOverlay()
            startCooldownTimer()
        }
        return START_STICKY
    }

    private fun showOverlay() {
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            PixelFormat.TRANSLUCENT
        )

        layoutParams.gravity = Gravity.CENTER

        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        overlayView = inflater.inflate(R.layout.overlay_block, null)

        val tvCooldownTimer = overlayView?.findViewById<TextView>(R.id.tvCooldownTimer)
        val btnGoHome = overlayView?.findViewById<Button>(R.id.btnGoHome)

        // Update initial timer
        updateTimerDisplay(tvCooldownTimer)

        btnGoHome?.setOnClickListener {
            val homeIntent = Intent(Intent.ACTION_MAIN)
            homeIntent.addCategory(Intent.CATEGORY_HOME)
            homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(homeIntent)
        }

        windowManager?.addView(overlayView, layoutParams)
    }

    private fun startCooldownTimer() {
        val tvCooldownTimer = overlayView?.findViewById<TextView>(R.id.tvCooldownTimer)

        timerRunnable = object : Runnable {
            override fun run() {
                if (timeManager.isInCooldown()) {
                    updateTimerDisplay(tvCooldownTimer)
                    handler.postDelayed(this, 1000) // Update every second
                } else {
                    // Cooldown finished
                    removeOverlay()
                    stopSelf()
                }
            }
        }

        handler.post(timerRunnable!!)
    }

    private fun updateTimerDisplay(textView: TextView?) {
        val remainingMinutes = timeManager.getRemainingCooldownMinutes()
        textView?.text = "Time remaining: $remainingMinutes min"
    }

    private fun removeOverlay() {
        handler.removeCallbacks(timerRunnable!!)
        if (overlayView != null) {
            windowManager?.removeView(overlayView)
            overlayView = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeOverlay()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
