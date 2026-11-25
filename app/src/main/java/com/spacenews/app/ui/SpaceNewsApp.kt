package com.spacenews.app.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.spacenews.app.navigation.Routes
import com.spacenews.app.ui.components.BottomTabBar
import com.spacenews.app.ui.screens.ArticleScreen
import com.spacenews.app.ui.screens.BookmarksScreen
import com.spacenews.app.ui.screens.FeedScreen
import com.spacenews.app.ui.theme.AppBg

@Composable
fun SpaceNewsApp() {
    val nav = rememberNavController()
    val route = nav.currentBackStackEntryAsState().value?.destination?.route
    val showTabs = route == Routes.FEED || route == Routes.BOOKMARKS

    Box(Modifier.fillMaxSize().background(AppBg)) {
        NavHost(
            navController = nav,
            startDestination = Routes.FEED,
            modifier = Modifier.fillMaxSize(),
            enterTransition = { fadeIn(tween(220)) },
            exitTransition = { fadeOut(tween(150)) },
            popEnterTransition = { fadeIn(tween(220)) },
            popExitTransition = { fadeOut(tween(150)) },
        ) {
            composable(Routes.FEED) { FeedScreen(onOpen = { nav.navigate(Routes.article(it)) }) }
            composable(Routes.BOOKMARKS) { BookmarksScreen(onOpen = { nav.navigate(Routes.article(it)) }) }
            composable(Routes.ARTICLE, arguments = listOf(navArgument("id") { type = NavType.StringType })) { entry ->
                ArticleScreen(
                    articleId = entry.arguments?.getString("id").orEmpty(),
                    onBack = { if (nav.previousBackStackEntry != null) nav.popBackStack() },
                )
            }
        }
        if (showTabs) {
            Box(Modifier.align(Alignment.BottomCenter)) {
                BottomTabBar(
                    selectedFeed = route == Routes.FEED,
                    onFeed = { switchTab(nav, Routes.FEED) },
                    onBookmarks = { switchTab(nav, Routes.BOOKMARKS) },
                )
            }
        }
    }
}

private fun switchTab(nav: NavHostController, route: String) {
    nav.navigate(route) {
        popUpTo(Routes.FEED) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
