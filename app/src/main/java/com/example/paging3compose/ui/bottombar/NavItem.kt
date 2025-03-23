package com.example.testcompose.ui.bottombar

import com.example.paging3compose.R
import com.example.paging3compose.util.Const

sealed class NavItem(val icon: Int, val label: String, val route: String) {

    data object Home : NavItem(
        icon = R.drawable.ic_home_disable, label = "Home", route = Const.HOME
    )

    data object Search : NavItem(
        icon = R.drawable.ic_search_disable,
        label = "Search",
        route = Const.SEARCH,
    )

    data object Analytics : NavItem(
        icon = R.drawable.ic_pie_chart_disable,
        label = "Analytics",
        route = Const.ANALYTICS,
    )

    data object Clock : NavItem(
        icon = R.drawable.ic_clock_disable, label = "Clock", route = Const.CLOCK,
    )

    data object Profile : NavItem(
        icon = R.drawable.ic_user_disable, label = "Profile", route = Const.PROFILE,
    )

}