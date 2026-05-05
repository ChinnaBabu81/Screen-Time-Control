kotlin
package com.example.smartscreentime

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvUsageAccessStatus: TextView
    private lateinit var tvOverlayStatus: TextView
    private lateinit var tvAccessibilityStatus: TextView
    private lateinit var tvMonitoringStatus: TextView
    private lateinit var btnRequestPermissions: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        updatePermissionStatus()

        btnRequestPermissions.setOnClickListener {
            requestAllPermissions()
        }
    }

    override fun onResume() {
        super.onResume()
        updatePermissionStatus()
    }

    private fun initViews() {
        tvUsageAccessStatus = findViewById(R.id.tvUsageAccessStatus)
        tvOverlayStatus = findViewById(R.id.tvOverlayStatus)
        tvAccessibilityStatus = findViewById(R.id.tvAccessibilityStatus)
        tvMonitoringStatus = findViewById(R.id.tvMonitoringStatus)
        btnRequestPermissions = findViewById(R.id.btnRequestPermissions)
    }

    private fun updatePermissionStatus() {
        val hasUsageAccess = hasUsageStatsPermission()
        val hasOverlay = canDrawOverlays()
        val hasAccessibility = isAccessibilityServiceEnabled()

        tvUsageAccessStatus.text = if (hasUsageAccess) "✅ Usage Access" else "❌ Usage Access"
        tvOverlayStatus.text = if (hasOverlay) "✅ Draw Over Apps" else "❌ Draw Over Apps"
        tvAccessibilityStatus.text = if (hasAccessibility) "✅ Accessibility Service" else "❌ Accessibility Service"

        if (hasUsageAccess && hasOverlay && hasAccessibility) {
            tvMonitoringStatus.text = getString(R.string.monitoring_active)
            tvMonitoringStatus.setTextColor(getColor(android.R.color.holo_green_dark))
        } else {
            tvMonitoringStatus.text = getString(R.string.monitoring_inactive)
            tvMonitoringStatus.setTextColor(getColor(R.color.block_bg))
        }
    }

    private fun requestAllPermissions() {
        when {
            !hasUsageStatsPermission() -> requestUsageStatsPermission()
            !canDrawOverlays() -> requestOverlayPermission()
            !isAccessibilityServiceEnabled() -> requestAccessibilityPermission()
            else -> {
                Toast.makeText(this, "All permissions granted!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hasUsageStatsPermission(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOps.unsafeCheckOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),
                packageName
            )
        } else {
            appOps.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),
                packageName
            )
        }
        return mode == AppOpsManager.MODE_ALLOWED
    }

    private fun canDrawOverlays(): Boolean {
        return Settings.canDrawOverlays(this)
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        val service = "${packageName}/${AppUsageService::class.java.canonicalName}"
        val enabledServices = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )
        return enabledServices?.contains(service) == true
    }

    private fun requestUsageStatsPermission() {
        Toast.makeText(this, "Please enable Usage Access", Toast.LENGTH_LONG).show()
        startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
    }

    private fun requestOverlayPermission() {
        Toast.makeText(this, "Please enable Draw Over Apps", Toast.LENGTH_LONG).show()
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:$packageName")
        )
        startActivity(intent)
    }

    private fun requestAccessibilityPermission() {
        Toast.makeText(this, "Please enable Accessibility Service", Toast.LENGTH_LONG).show()
        startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
    }
}
