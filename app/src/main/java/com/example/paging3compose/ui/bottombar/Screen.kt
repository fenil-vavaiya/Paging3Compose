package com.example.paging3compose.ui.bottombar

import com.example.paging3compose.R

sealed class Screen(val route: String, val icon: Int, val label: String) {
    data object Home : Screen("home", R.drawable.ic_home, "Home")
    data object Month : Screen("month", R.drawable.ic_month, "Month")
    data object Active : Screen("active", R.drawable.ic_active, "Active")
    data object Bookmark : Screen("bookmarks", R.drawable.ic_bookmark, "Bookmarks")
    data object Setting : Screen("setting", R.drawable.ic_setting, "Setting")
}
