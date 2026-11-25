package com.spacenews.app.navigation

object Routes {
    const val FEED = "feed"
    const val BOOKMARKS = "bookmarks"
    const val ARTICLE = "article/{id}"

    fun article(id: String) = "article/$id"
}
