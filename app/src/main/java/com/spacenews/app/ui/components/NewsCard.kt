package com.spacenews.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spacenews.app.data.Article
import com.spacenews.app.ui.theme.Blue
import com.spacenews.app.ui.theme.Muted
import com.spacenews.app.ui.theme.Navy
import com.spacenews.app.ui.theme.Sky
import com.spacenews.app.ui.theme.ThumbA
import com.spacenews.app.ui.theme.ThumbB

val CardShape = RoundedCornerShape(20.dp)

fun Modifier.cardShadow(shape: androidx.compose.ui.graphics.Shape = CardShape) =
    this.shadow(8.dp, shape, ambientColor = Blue.copy(alpha = 0.25f), spotColor = Blue.copy(alpha = 0.25f))

/** Строка новости: миниатюра, категория, заголовок, время чтения и кнопка-закладка. */
@Composable
fun NewsCard(article: Article, saved: Boolean, onOpen: () -> Unit, onToggleSaved: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().cardShadow().clip(CardShape).background(Color.White)
            .pressable(onOpen).padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier.size(84.dp).clip(RoundedCornerShape(16.dp))
                .background(Brush.linearGradient(listOf(ThumbA, ThumbB))),
        ) {
            Illustration(article.art, Modifier.size(84.dp), scale = 0.85f)
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            AppText(
                article.category.title.uppercase(), size = 11.sp, weight = FontWeight.ExtraBold,
                color = Blue, letterSpacing = 0.6.sp,
            )
            AppText(
                article.title, Modifier.padding(top = 3.dp, bottom = 6.dp), size = 15.sp,
                weight = FontWeight.ExtraBold, lineHeight = 20.sp, maxLines = 3,
            )
            AppText("${article.minutes} мин чтения", size = 12.sp, weight = FontWeight.SemiBold, color = Muted)
        }
        Box(
            Modifier.size(44.dp).clip(CircleShape).pressable(onToggleSaved),
            contentAlignment = Alignment.Center,
        ) {
            BookmarkIcon(saved, Blue, iconSize = 22.dp)
        }
    }
}

/** Большая карточка главной новости. */
@Composable
fun FeaturedCard(article: Article, onOpen: () -> Unit) {
    val shape = RoundedCornerShape(24.dp)
    Box(
        Modifier.fillMaxWidth().height(190.dp).cardShadow(shape).clip(shape)
            .background(Brush.linearGradient(listOf(Navy, Blue)))
            .pressable(onOpen),
    ) {
        Illustration(article.art, Modifier.fillMaxWidth().height(190.dp), cx = 0.78f, scale = 0.9f)
        Column(Modifier.align(Alignment.BottomStart).padding(18.dp)) {
            AppText(
                "ГЛАВНОЕ", size = 11.sp, weight = FontWeight.ExtraBold, color = Sky, letterSpacing = 0.6.sp,
            )
            AppText(
                article.title, Modifier.fillMaxWidth(0.82f).padding(top = 4.dp, bottom = 6.dp), size = 19.sp,
                weight = FontWeight.ExtraBold, lineHeight = 25.sp, color = Color.White, maxLines = 3,
            )
            AppText("${article.minutes} мин чтения", size = 12.sp, weight = FontWeight.SemiBold, color = Sky)
        }
    }
}
