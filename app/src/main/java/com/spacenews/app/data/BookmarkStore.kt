package com.spacenews.app.data

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/** Закладки «Читать позже»: хранятся на телефоне (SharedPreferences), поэтому работают без интернета. */
object BookmarkStore {
    private var prefs: SharedPreferences? = null

    var ids: Set<String> by mutableStateOf(emptySet())
        private set

    fun init(context: Context) {
        val p = context.getSharedPreferences("space_news", Context.MODE_PRIVATE)
        prefs = p
        ids = p.getStringSet("bookmarks", emptySet())?.toSet() ?: emptySet()
    }

    fun isSaved(id: String) = id in ids

    fun toggle(id: String) {
        ids = if (id in ids) ids - id else ids + id
        prefs?.edit()?.putStringSet("bookmarks", ids)?.apply()
    }
}
