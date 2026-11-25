package com.spacenews.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.lerp
import androidx.compose.runtime.Composable
import com.spacenews.app.data.Art
import kotlin.math.cos
import kotlin.math.sin

private val STARS = listOf(
    Triple(0.08f, 0.15f, 0.9f), Triple(0.22f, 0.80f, 0.7f), Triple(0.35f, 0.10f, 0.6f),
    Triple(0.55f, 0.88f, 0.9f), Triple(0.68f, 0.12f, 0.7f), Triple(0.90f, 0.30f, 1.0f),
    Triple(0.93f, 0.78f, 0.7f), Triple(0.12f, 0.50f, 0.6f), Triple(0.78f, 0.55f, 0.5f),
    Triple(0.45f, 0.30f, 0.5f),
)

/**
 * Иллюстрация, нарисованная кодом (картинки не нужны — приложение лёгкое и работает офлайн).
 * Рисуется поверх фона; cx — положение центра по ширине (0..1), scale — масштаб.
 */
@Composable
fun Illustration(art: Art, modifier: Modifier = Modifier, cx: Float = 0.5f, scale: Float = 1f) {
    Canvas(modifier) {
        val unit = size.minDimension / 100f
        for ((fx, fy, r) in STARS) {
            drawCircle(Color.White.copy(alpha = 0.55f), r * unit, Offset(size.width * fx, size.height * fy))
        }
        val u = unit * scale
        val c = Offset(size.width * cx, size.height / 2f)
        when (art) {
            Art.RINGED -> {
                ball(c, 20 * u, Color(0xFFBBD4FF))
                rotate(-20f, c) {
                    drawOval(
                        Color.White.copy(alpha = 0.9f), Offset(c.x - 38 * u, c.y - 8 * u),
                        Size(76 * u, 16 * u), style = Stroke(3 * u),
                    )
                }
            }
            Art.MARS -> {
                ball(c, 22 * u, Color(0xFFE58B6B))
                drawCircle(Color(0xFFC96F52), 4 * u, Offset(c.x - 8 * u, c.y - 5 * u))
                drawCircle(Color(0xFFC96F52), 3 * u, Offset(c.x + 8 * u, c.y + 6 * u))
                drawCircle(Color(0xFFC96F52), 2 * u, Offset(c.x - 4 * u, c.y + 11 * u))
            }
            Art.JUPITER -> {
                ball(c, 24 * u, Color(0xFFF2CFA6))
                val clip = Path().apply { addOval(Rect(c, 24 * u)) }
                clipPath(clip) {
                    val band = Color(0xFFD99B6A).copy(alpha = 0.65f)
                    drawRect(band, Offset(c.x - 24 * u, c.y - 12 * u), Size(48 * u, 5 * u))
                    drawRect(band, Offset(c.x - 24 * u, c.y - 1 * u), Size(48 * u, 7 * u))
                    drawRect(band, Offset(c.x - 24 * u, c.y + 12 * u), Size(48 * u, 4 * u))
                    drawOval(Color(0xFFC7694F), Offset(c.x + 4 * u, c.y + 7 * u), Size(10 * u, 6 * u))
                }
            }
            Art.MOON -> {
                ball(c, 24 * u, Color(0xFFE3ECFF))
                val crater = Color(0xFFC9D6F2)
                drawCircle(crater, 5 * u, Offset(c.x - 8 * u, c.y - 7 * u))
                drawCircle(crater, 3.5f * u, Offset(c.x + 9 * u, c.y + 3 * u))
                drawCircle(crater, 2.5f * u, Offset(c.x - 3 * u, c.y + 11 * u))
            }
            Art.PLANET -> {
                ball(c, 22 * u, Color(0xFF8FB8FF))
                drawCircle(Color.White.copy(alpha = 0.9f), 4 * u, Offset(c.x + 30 * u, c.y - 18 * u))
            }
            Art.HOLE -> {
                drawCircle(Color.White.copy(alpha = 0.16f), 30 * u, c)
                drawCircle(Color(0xFF8FB8FF), 21 * u, c, style = Stroke(4 * u))
                drawCircle(Color(0xFF050B1F), 16 * u, c)
            }
            Art.ROCKET -> rotate(35f, c) {
                val body = Path().apply {
                    moveTo(c.x, c.y - 30 * u)
                    quadraticBezierTo(c.x + 14 * u, c.y - 8 * u, c.x + 10 * u, c.y + 18 * u)
                    lineTo(c.x - 10 * u, c.y + 18 * u)
                    quadraticBezierTo(c.x - 14 * u, c.y - 8 * u, c.x, c.y - 30 * u)
                    close()
                }
                val finL = Path().apply {
                    moveTo(c.x - 10 * u, c.y + 2 * u); lineTo(c.x - 21 * u, c.y + 22 * u)
                    lineTo(c.x - 10 * u, c.y + 18 * u); close()
                }
                val finR = Path().apply {
                    moveTo(c.x + 10 * u, c.y + 2 * u); lineTo(c.x + 21 * u, c.y + 22 * u)
                    lineTo(c.x + 10 * u, c.y + 18 * u); close()
                }
                val flame = Path().apply {
                    moveTo(c.x - 6 * u, c.y + 18 * u); lineTo(c.x, c.y + 36 * u)
                    lineTo(c.x + 6 * u, c.y + 18 * u); close()
                }
                drawPath(flame, Color(0xFFFFC857))
                drawPath(finL, Color(0xFFBBD4FF)); drawPath(finR, Color(0xFFBBD4FF))
                drawPath(body, Color.White)
                drawCircle(Color(0xFF4C8DFF), 5 * u, Offset(c.x, c.y - 8 * u))
            }
            Art.SATELLITE -> rotate(-25f, c) {
                for (side in listOf(-1, 1)) {
                    val x0 = if (side < 0) c.x - 34 * u else c.x + 10 * u
                    drawRect(Color(0xFFBBD4FF), Offset(x0, c.y - 7 * u), Size(24 * u, 14 * u))
                    for (k in 1..3) {
                        drawLine(
                            Color(0xFF4C8DFF), Offset(x0 + k * 6 * u, c.y - 7 * u),
                            Offset(x0 + k * 6 * u, c.y + 7 * u), u,
                        )
                    }
                }
                drawRoundRect(Color.White, Offset(c.x - 9 * u, c.y - 9 * u), Size(18 * u, 18 * u), CornerRadius(3 * u))
                drawLine(Color.White, Offset(c.x, c.y - 9 * u), Offset(c.x, c.y - 21 * u), 2 * u, StrokeCap.Round)
                drawCircle(Color.White, 2.5f * u, Offset(c.x, c.y - 22 * u))
            }
            Art.TELESCOPE -> {
                val r = 9 * u
                val gold = Color(0xFFF4C95D)
                hex(c, r * 0.92f, gold)
                for (k in 0..5) {
                    val a = Math.toRadians(60.0 * k)
                    val p = Offset(c.x + (r * 1.74f * cos(a)).toFloat(), c.y + (r * 1.74f * sin(a)).toFloat())
                    hex(p, r * 0.92f, gold)
                }
            }
            Art.DISH -> {
                rotate(-30f, c) {
                    drawArc(
                        Color.White.copy(alpha = 0.3f), 20f, 140f, true,
                        Offset(c.x - 28 * u, c.y - 34 * u), Size(56 * u, 56 * u),
                    )
                    drawArc(
                        Color.White, 20f, 140f, false,
                        Offset(c.x - 28 * u, c.y - 34 * u), Size(56 * u, 56 * u), style = Stroke(3.5f * u, cap = StrokeCap.Round),
                    )
                    drawLine(Color.White, Offset(c.x, c.y - 6 * u), Offset(c.x, c.y - 30 * u), 2 * u, StrokeCap.Round)
                    drawCircle(Color.White, 3 * u, Offset(c.x, c.y - 31 * u))
                }
                drawLine(Color.White, Offset(c.x, c.y + 14 * u), Offset(c.x, c.y + 34 * u), 3 * u, StrokeCap.Round)
                drawLine(Color.White, Offset(c.x - 12 * u, c.y + 34 * u), Offset(c.x + 12 * u, c.y + 34 * u), 3 * u, StrokeCap.Round)
            }
        }
    }
}

private fun DrawScope.ball(c: Offset, r: Float, color: Color) {
    drawCircle(
        Brush.radialGradient(
            listOf(lerp(color, Color.White, 0.4f), color),
            center = Offset(c.x - r * 0.35f, c.y - r * 0.35f), radius = r * 1.5f,
        ),
        r, c,
    )
}

private fun DrawScope.hex(center: Offset, r: Float, color: Color) {
    val p = Path()
    for (k in 0..5) {
        val a = Math.toRadians(30.0 + 60.0 * k)
        val x = center.x + (r * cos(a)).toFloat()
        val y = center.y + (r * sin(a)).toFloat()
        if (k == 0) p.moveTo(x, y) else p.lineTo(x, y)
    }
    p.close()
    drawPath(p, color)
}
