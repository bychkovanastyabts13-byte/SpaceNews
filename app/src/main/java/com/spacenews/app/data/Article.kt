package com.spacenews.app.data

enum class Category(val title: String) {
    LAUNCHES("Запуски"),
    RESEARCH("Исследования"),
    TELESCOPES("Телескопы"),
}

/** Какая иллюстрация рисуется для статьи. */
enum class Art { ROCKET, RINGED, MARS, JUPITER, MOON, HOLE, SATELLITE, TELESCOPE, DISH, PLANET }

data class Article(
    val id: String,
    val category: Category,
    val title: String,
    val lead: String,
    val body: List<String>,
    val minutes: Int,
    val art: Art,
)
