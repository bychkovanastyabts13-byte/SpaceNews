package com.spacenews.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spacenews.app.data.ArticlesData
import com.spacenews.app.data.BookmarkStore
import com.spacenews.app.ui.components.AppText
import com.spacenews.app.ui.components.BookmarkIcon
import com.spacenews.app.ui.components.NewsCard
import com.spacenews.app.ui.theme.Blue
import com.spacenews.app.ui.theme.Muted

/** Экран 3: закладки «Читать позже». Хранятся на телефоне, поэтому доступны без интернета. */
@Composable
fun BookmarksScreen(onOpen: (String) -> Unit) {
    val savedIds = BookmarkStore.ids
    val news = ArticlesData.all.filter { it.id in savedIds }

    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 110.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Column {
                AppText("Читать позже", size = 26.sp, weight = FontWeight.ExtraBold)
                AppText(
                    "Сохранённые новости доступны без интернета", Modifier.padding(top = 4.dp, bottom = 4.dp),
                    size = 12.sp, weight = FontWeight.SemiBold, color = Muted,
                )
            }
        }
        if (news.isEmpty()) {
            item {
                Column(
                    Modifier.fillMaxWidth().padding(top = 80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    BookmarkIcon(false, Blue, iconSize = 48.dp)
                    Spacer(Modifier.height(14.dp))
                    AppText(
                        "Пока ничего нет.\nНажмите на закладку у новости, чтобы сохранить её.",
                        size = 15.sp, color = Muted, align = TextAlign.Center, lineHeight = 22.sp,
                    )
                }
            }
        } else {
            items(news, key = { it.id }) { article ->
                NewsCard(
                    article, saved = true,
                    onOpen = { onOpen(article.id) },
                    onToggleSaved = { BookmarkStore.toggle(article.id) },
                )
            }
        }
    }
}
