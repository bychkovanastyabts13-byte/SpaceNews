package com.spacenews.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spacenews.app.ui.theme.Muted
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/** Собственный «pull-to-refresh»: потяните список вниз и отпустите — запустится обновление. */
@Composable
fun PullToRefresh(onRefresh: suspend () -> Unit, content: @Composable () -> Unit) {
    val scope = rememberCoroutineScope()
    val threshold = with(LocalDensity.current) { 72.dp.toPx() }
    var pull by remember { mutableFloatStateOf(0f) }
    var refreshing by remember { mutableStateOf(false) }
    var dragging by remember { mutableStateOf(false) }
    var updated by remember { mutableStateOf(false) }
    val refreshAction by rememberUpdatedState(onRefresh)

    val shown by animateFloatAsState(
        targetValue = if (refreshing) threshold * 0.7f else pull,
        animationSpec = if (dragging) snap<Float>() else tween<Float>(250),
        label = "pull",
    )

    val connection = remember(threshold) {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (refreshing) return Offset.Zero
                if (source == NestedScrollSource.Drag && pull > 0f && available.y < 0f) {
                    val used = maxOf(available.y, -pull)
                    pull += used
                    return Offset(0f, used)
                }
                return Offset.Zero
            }

            override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
                if (refreshing) return Offset.Zero
                if (source == NestedScrollSource.Drag && available.y > 0f) {
                    dragging = true
                    pull = minOf(pull + available.y * 0.5f, threshold * 1.6f)
                    return Offset(0f, available.y)
                }
                return Offset.Zero
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                dragging = false
                if (!refreshing && pull >= threshold) {
                    refreshing = true
                    pull = 0f
                    scope.launch {
                        refreshAction()
                        refreshing = false
                        updated = true
                    }
                } else {
                    pull = 0f
                }
                return Velocity.Zero
            }
        }
    }

    val hint = when {
        refreshing -> "Обновляем…"
        pull >= threshold -> "Отпустите, чтобы обновить"
        pull == 0f && updated -> "Лента обновлена"
        else -> "Потяните вниз, чтобы обновить"
    }

    Box(Modifier.fillMaxSize().nestedScroll(connection)) {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier.fillMaxWidth().height(36.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ArrowDownIcon(Muted, Modifier.rotate(if (pull >= threshold) 180f else 0f))
                Spacer(Modifier.width(6.dp))
                AppText(hint, size = 12.sp, color = Muted)
            }
            Box(Modifier.weight(1f).fillMaxWidth().offset { IntOffset(0, shown.roundToInt()) }) {
                content()
            }
        }
    }
}
