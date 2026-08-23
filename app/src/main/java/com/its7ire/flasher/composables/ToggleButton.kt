package com.its7ire.flasher.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext

@Composable
fun GlowingPowerButton(
    isOn: Boolean,
    onClick: () -> Unit,
    Icon: ImageVector,
    modifier: Modifier = Modifier // The parameter
) {
    val glowAlpha by animateFloatAsState(
        targetValue = if (isOn) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "GlowAnimation"
    )

    val neonBlue = MaterialTheme.colorScheme.primary
    val darkGray = Color(0xFF333333)
    val buttonSurface = Color(0xFF1E1E1E)
    val iconTint = if (isOn) neonBlue else darkGray

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(160.dp)
    ) {

        Box(
            modifier = Modifier
                .matchParentSize()
                .drawBehind {
                    val buttonRadius = 40.dp.toPx()
                    val ringWidth = 4.dp.toPx()
                    val ringRadius = buttonRadius + 12.dp.toPx()

                    if (glowAlpha > 0f) {
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    neonBlue.copy(alpha = 0.6f * glowAlpha),
                                    Color.Transparent
                                ),
                                radius = ringRadius + (30.dp.toPx() * glowAlpha)
                            ),
                            radius = ringRadius + 30.dp.toPx()
                        )
                    }

                    drawCircle(
                        color = if (isOn) neonBlue else darkGray,
                        radius = ringRadius,
                        style = Stroke(width = ringWidth)
                    )
                }
        )

        // The clickable button
        Box(
            modifier = Modifier // <-- Capital 'M'
                .size(80.dp)
                .clip(CircleShape)
                .background(buttonSurface)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icon,
                contentDescription = "Power",
                tint = iconTint,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}

@Preview
@Composable
fun preview() {
    var isOn by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center) {
        GlowingPowerButton(
            isOn = isOn,
            onClick = {
                isOn = !isOn
                toggleFlashlight(context, isOn)
            },
            Icon = Icons.Filled.PowerSettingsNew
        )
    }
}