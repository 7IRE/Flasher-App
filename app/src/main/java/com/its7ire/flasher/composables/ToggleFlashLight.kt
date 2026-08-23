package com.its7ire.flasher.composables

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager

/**
 * Turns the device flashlight on or off.
 *
 * @param context The application or activity context.
 * @param turnOn True to turn the flashlight on, false to turn it off.
 */
fun toggleFlashlight(context: Context, turnOn: Boolean) {
    try {
        val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

        // Ensure the device actually has cameras
        if (cameraManager.cameraIdList.isNotEmpty()) {
            // Camera at index 0 is typically the primary rear camera (which has the flash)
            val cameraId = cameraManager.cameraIdList[0]

            cameraManager.setTorchMode(cameraId, turnOn)
        }
    } catch (e: CameraAccessException) {
        // The camera is currently in use by another app or unavailable
        e.printStackTrace()
    } catch (e: IllegalArgumentException) {
        // The camera does not have a flash unit
        e.printStackTrace()
    } catch (e: Exception) {
        // Catch-all for any other unexpected hardware errors
        e.printStackTrace()
    }
}