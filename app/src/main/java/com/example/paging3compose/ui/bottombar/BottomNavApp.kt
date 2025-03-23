package com.example.paging3compose.ui.bottombar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.paging3compose.ui.screen.home.HomeScreen
import com.example.testcompose.ui.screen.profile.ProfileScreen
import com.example.testcompose.ui.screen.setting.SettingsScreen
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource


@Composable
fun CustomBottomNavApp() {
    val navController = rememberNavController()
    val hazeState = remember { HazeState() }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(state = hazeState, zIndex = 0f),
//            containerColor = Color.Black // Dark background for contrast
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Screen.Home.route) { HomeScreen() }
                composable(Screen.Month.route) { ProfileScreen() }
                composable(Screen.Active.route) { ProfileScreen() }
                composable(Screen.Bookmark.route) { ProfileScreen() }
                composable(Screen.Setting.route) { SettingsScreen() }
            }
        }

        CustomMinimalBottomNav(
            navController, modifier = Modifier
                .align(Alignment.BottomCenter) // Stick to bottom
                .fillMaxWidth()
                .padding(vertical = 10.dp)
            , hazeState
        )
    }

}









