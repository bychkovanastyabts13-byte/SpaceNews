package com.spacenews.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spacenews.app.ui.theme.Blue
import com.spacenews.app.ui.theme.Inactive

/** Нижняя панель с двумя вкладками: «Лента» и «Закладки». */
@Composable
fun BottomTabBar(selectedFeed: Boolean, onFeed: () -> Unit, onBookmarks: () -> Unit) {
    val shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    Row(
        Modifier.fillMaxWidth().height(84.dp).shadow(14.dp, shape, ambientColor = Blue.copy(alpha = 0.3f), spotColor = Blue.copy(alpha = 0.3f))
            .clip(shape).background(Color.White).padding(top = 12.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Top,
    ) {
        Tab("Лента", selectedFeed, onFeed) { HomeIcon(it) }
        Tab("Закладки", !selectedFeed, onBookmarks) { BookmarkIcon(false, it) }
    }
}

@Composable
private fun Tab(label: String, selected: Boolean, onClick: () -> Unit, icon: @Composable (Color) -> Unit) {
    val color = if (selected) Blue else Inactive
    Column(
        Modifier.width(120.dp).pressable(onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        icon(color)
        AppText(label, size = 12.sp, weight = FontWeight.Bold, color = color)
    }
}
