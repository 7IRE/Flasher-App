package com.its7ire.flasher.Screen

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Looks3
import androidx.compose.material.icons.filled.Looks4
import androidx.compose.material.icons.filled.Looks5
import androidx.compose.material.icons.filled.Looks6
import androidx.compose.material.icons.filled.LooksOne
import androidx.compose.material.icons.filled.LooksTwo
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.its7ire.flasher.Service.FlasherService
import com.its7ire.flasher.composables.GlowingPowerButton
import com.its7ire.flasher.viewmodel.FlashViewModel

@Preview
@Composable
fun AppScreen(modifier: Modifier = Modifier, viewModel: FlashViewModel = viewModel()) {
    val isOn by viewModel.isOn.collectAsState()
    val flashSpeed by viewModel.flashSpeed.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(isOn, flashSpeed) {
        val intent = Intent(context, FlasherService::class.java).apply {
            putExtra("SPEED", if (isOn) flashSpeed else 0)
        }

        if (isOn) {
            context.startForegroundService(intent)
        } else {
            context.stopService(intent)
        }

    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- POWER BUTTON ---
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            GlowingPowerButton(
                isOn = isOn,
                onClick = { viewModel.togglePower() },
                Icon = Icons.Filled.PowerSettingsNew
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            GlowingPowerButton(isOn = (flashSpeed == 1), onClick = { viewModel.setSpeed(1) }, Icon = Icons.Filled.LooksOne)
            GlowingPowerButton(isOn = (flashSpeed == 2), onClick = { viewModel.setSpeed(2) }, Icon = Icons.Filled.LooksTwo)
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            GlowingPowerButton(isOn = (flashSpeed == 3), onClick = { viewModel.setSpeed(3) }, Icon = Icons.Filled.Looks3)
            GlowingPowerButton(isOn = (flashSpeed == 4), onClick = { viewModel.setSpeed(4) }, Icon = Icons.Filled.Looks4)
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            GlowingPowerButton(isOn = (flashSpeed == 5), onClick = { viewModel.setSpeed(5) }, Icon = Icons.Filled.Looks5)
            GlowingPowerButton(isOn = (flashSpeed == 6), onClick = { viewModel.setSpeed(6) }, Icon = Icons.Filled.Looks6)
        }
    }
}