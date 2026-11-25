package com.spacenews.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spacenews.app.data.ArticlesData
import com.spacenews.app.data.BookmarkStore
import com.spacenews.app.ui.components.AppText
import com.spacenews.app.ui.components.BackIcon
import com.spacenews.app.ui.components.BookmarkIcon
import com.spacenews.app.ui.components.Illustration
import com.spacenews.app.ui.components.cardShadow
import com.spacenews.app.ui.components.pressable
import com.spacenews.app.ui.theme.Blue
import com.spacenews.app.ui.theme.BodyText
import com.spacenews.app.ui.theme.Muted
import com.spacenews.app.ui.theme.Navy

/** Экран 2: детальное чтение публикации с иллюстрацией. */
@Composable
fun ArticleScreen(articleId: String, onBack: () -> Unit) {
    val article = remember(articleId) { ArticlesData.byId(articleId) }
    if (article == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { AppText("Новость не найдена", size = 18.sp) }
        return
    }
    val saved = article.id in BookmarkStore.ids
    val heroShape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
    val iconShape = RoundedCornerShape(14.dp)

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Box(
                Modifier.fillMaxWidth().height(300.dp).clip(heroShape)
                    .background(Brush.linearGradient(listOf(Navy, Blue))),
            ) {
                Illustration(article.art, Modifier.fillMaxSize(), scale = 1.9f)
                Box(
                    Modifier.padding(20.dp).size(44.dp).align(Alignment.TopStart)
                        .cardShadow(iconShape).clip(iconShape).background(Color.White).pressable(onBack),
                    contentAlignment = Alignment.Center,
                ) { BackIcon(Navy) }
                Box(
                    Modifier.padding(20.dp).size(44.dp).align(Alignment.TopEnd)
                        .cardShadow(iconShape).clip(iconShape).background(Color.White)
                        .pressable { BookmarkStore.toggle(article.id) },
                    contentAlignment = Alignment.Center,
                ) { BookmarkIcon(saved, Blue) }
            }
            Column(Modifier.padding(horizontal = 20.dp, vertical = 20.dp)) {
                AppText(
                    article.category.title.uppercase(), size = 11.sp, weight = FontWeight.ExtraBold,
                    color = Blue, letterSpacing = 0.6.sp,
                )
                AppText(
                    article.title, Modifier.padding(top = 6.dp, bottom = 6.dp), size = 22.sp,
                    weight = FontWeight.ExtraBold, lineHeight = 29.sp,
                )
                AppText(
                    "${article.minutes} мин чтения", Modifier.padding(bottom = 14.dp), size = 12.sp,
                    weight = FontWeight.SemiBold, color = Muted,
                )
                AppText(
                    article.lead, Modifier.padding(bottom = 12.dp), size = 16.sp,
                    weight = FontWeight.Bold, lineHeight = 24.sp, color = Navy,
                )
                article.body.forEach {
                    AppText(it, Modifier.padding(bottom = 12.dp), size = 15.sp, lineHeight = 23.sp, color = BodyText)
                }
                Spacer(Modifier.height(90.dp))
            }
        }
        Row(
            Modifier.align(Alignment.BottomCenter).padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                .fillMaxWidth().height(56.dp)
                .cardShadow(RoundedCornerShape(18.dp)).clip(RoundedCornerShape(18.dp))
                .background(if (saved) Blue else Color.White)
                .pressable { BookmarkStore.toggle(article.id) },
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BookmarkIcon(saved, if (saved) Color.White else Blue, iconSize = 22.dp)
            Spacer(Modifier.width(10.dp))
            AppText(
                if (saved) "Сохранено для чтения офлайн" else "Сохранить, чтобы читать позже",
                size = 16.sp, weight = FontWeight.ExtraBold, color = if (saved) Color.White else Blue,
            )
        }
    }
}
