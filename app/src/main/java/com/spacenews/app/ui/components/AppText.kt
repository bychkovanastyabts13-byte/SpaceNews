package com.spacenews.app.ui.components

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.spacenews.app.ui.theme.Navy

/** Собственный текстовый компонент (без Material). */
@Composable
fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    size: TextUnit = 15.sp,
    weight: FontWeight = FontWeight.Medium,
    color: Color = Navy,
    lineHeight: TextUnit = TextUnit.Unspecified,
    align: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    letterSpacing: TextUnit = TextUnit.Unspecified,
) {
    BasicText(
        text = text,
        modifier = modifier,
        style = TextStyle(
            color = color, fontSize = size, fontWeight = weight, lineHeight = lineHeight,
            textAlign = align, letterSpacing = letterSpacing,
        ),
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
    )
}
