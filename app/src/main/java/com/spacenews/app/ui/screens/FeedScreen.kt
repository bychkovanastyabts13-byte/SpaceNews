package com.spacenews.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spacenews.app.data.ArticlesData
import com.spacenews.app.data.BookmarkStore
import com.spacenews.app.data.Category
import com.spacenews.app.ui.components.AppText
import com.spacenews.app.ui.components.FeaturedCard
import com.spacenews.app.ui.components.NewsCard
import com.spacenews.app.ui.components.PullToRefresh
import com.spacenews.app.ui.components.pressable
import com.spacenews.app.ui.theme.Blue
import com.spacenews.app.ui.theme.Muted
import kotlinx.coroutines.delay

/** Экран 1: лента актуальных новостей космонавтики. */
@Composable
fun FeedScreen(onOpen: (String) -> Unit) {
    var tab by rememberSaveable { mutableStateOf(0) }    // 0 = «Все», дальше категории
    var shift by rememberSaveable { mutableStateOf(0) }  // сдвиг ленты при обновлении
    val categories = Category.values()
    val saved = BookmarkStore.ids

    val news = remember(tab, shift) {
        val all = ArticlesData.all
        val k = shift % all.size
        val rotated = all.drop(k) + all.take(k)
        if (tab == 0) rotated else rotated.filter { it.category == categories[tab - 1] }
    }

    PullToRefresh(onRefresh = { delay(900); shift += 1 }) {
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 110.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Column {
                    AppText("Космические новости", size = 26.sp, weight = FontWeight.ExtraBold)
                    AppText(
                        "Запуски и исследования космоса", Modifier.padding(top = 4.dp),
                        size = 12.sp, weight = FontWeight.SemiBold, color = Muted,
                    )
                }
            }
            item {
                Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Chip("Все", tab == 0) { tab = 0 }
                    categories.forEachIndexed { i, c -> Chip(c.title, tab == i + 1) { tab = i + 1 } }
                }
            }
            if (news.isNotEmpty()) {
                item { FeaturedCard(news.first()) { onOpen(news.first().id) } }
                items(news.drop(1), key = { it.id }) { article ->
                    NewsCard(
                        article, saved = article.id in saved,
                        onOpen = { onOpen(article.id) },
                        onToggleSaved = { BookmarkStore.toggle(article.id) },
                    )
                }
            }
        }
    }
}

@Composable
private fun Chip(title: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        Modifier.clip(CircleShape).background(if (selected) Blue else Color.White)
            .pressable(onClick).padding(horizontal = 16.dp, vertical = 9.dp),
    ) {
        AppText(title, size = 13.sp, weight = FontWeight.Bold, color = if (selected) Color.White else Blue)
    }
}
