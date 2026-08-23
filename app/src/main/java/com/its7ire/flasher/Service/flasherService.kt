package com.its7ire.flasher.Service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import com.its7ire.flasher.composables.toggleFlashlight
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class FlasherService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.Default)
    private var blinkJob: Job? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Default to -1 (Power Off) if no speed is provided
        val speed = intent?.getIntExtra("SPEED", -1) ?: -1

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ServiceCompat.startForeground(
                    this,
                    1,
                    createNotification(),
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ServiceInfo.FOREGROUND_SERVICE_TYPE_CAMERA else 0
                )
            } else {
                startForeground(1, createNotification())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (speed == -1) {
            stopSelf()
            return START_NOT_STICKY
        }

        startBlinking(speed)
        return START_STICKY
    }

    private fun startBlinking(speed: Int) {
        blinkJob?.cancel()

        blinkJob = serviceScope.launch {
            if (speed == 0) {
                toggleFlashlight(this@FlasherService, true)
            } else {
                val delayMillis = when (speed) {
                    1 -> 1000L
                    2 -> 800L
                    3 -> 400L
                    4 -> 200L
                    5 -> 100L
                    6 -> 50L
                    else -> 1000L
                }
                var hardwareOn = true
                while (isActive) {
                    toggleFlashlight(this@FlasherService, hardwareOn)
                    delay(delayMillis)
                    hardwareOn = !hardwareOn
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        blinkJob?.cancel()
        toggleFlashlight(this, false)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotification(): android.app.Notification {
        val channelId = "flasher_channel"
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, "Flasher Service", NotificationManager.IMPORTANCE_LOW
            )
            manager.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Flasher is running")
            .setContentText("Tap to open app")
            .setSmallIcon(android.R.drawable.ic_menu_camera)
            .build()
    }
}