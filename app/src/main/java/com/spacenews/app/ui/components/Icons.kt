package com.spacenews.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BookmarkIcon(filled: Boolean, color: Color, modifier: Modifier = Modifier, iconSize: Dp = 24.dp) {
    Canvas(modifier.size(iconSize)) {
        val s = size.width / 24f
        val path = Path().apply {
            moveTo(6f * s, 3f * s); lineTo(18f * s, 3f * s); lineTo(18f * s, 21f * s)
            lineTo(12f * s, 17f * s); lineTo(6f * s, 21f * s); close()
        }
        if (filled) drawPath(path, color)
        else drawPath(path, color, style = Stroke(2f * s, cap = StrokeCap.Round, join = StrokeJoin.Round))
    }
}

@Composable
fun HomeIcon(color: Color, modifier: Modifier = Modifier, iconSize: Dp = 24.dp) {
    Canvas(modifier.size(iconSize)) {
        val s = size.width / 24f
        val path = Path().apply {
            moveTo(4f * s, 11f * s); lineTo(12f * s, 4f * s); lineTo(20f * s, 11f * s)
            lineTo(20f * s, 20f * s); lineTo(4f * s, 20f * s); close()
        }
        drawPath(path, color, style = Stroke(2f * s, cap = StrokeCap.Round, join = StrokeJoin.Round))
    }
}

@Composable
fun BackIcon(color: Color, modifier: Modifier = Modifier, iconSize: Dp = 24.dp) {
    Canvas(modifier.size(iconSize)) {
        val s = size.width / 24f
        val w = 2f * s
        drawLine(color, Offset(19f * s, 12f * s), Offset(5f * s, 12f * s), w, StrokeCap.Round)
        drawLine(color, Offset(11f * s, 6f * s), Offset(5f * s, 12f * s), w, StrokeCap.Round)
        drawLine(color, Offset(11f * s, 18f * s), Offset(5f * s, 12f * s), w, StrokeCap.Round)
    }
}

@Composable
fun ArrowDownIcon(color: Color, modifier: Modifier = Modifier, iconSize: Dp = 18.dp) {
    Canvas(modifier.size(iconSize)) {
        val s = size.width / 24f
        val w = 2f * s
        drawLine(color, Offset(12f * s, 5f * s), Offset(12f * s, 17f * s), w, StrokeCap.Round)
        drawLine(color, Offset(7f * s, 12f * s), Offset(12f * s, 17f * s), w, StrokeCap.Round)
        drawLine(color, Offset(17f * s, 12f * s), Offset(12f * s, 17f * s), w, StrokeCap.Round)
    }
}
