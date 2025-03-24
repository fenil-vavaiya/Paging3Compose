package com.example.paging3compose.ui.bottombar

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.paging3compose.ui.theme.BottomItemDisable
import com.example.paging3compose.ui.theme.Jost
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import androidx.compose.material.ripple.rememberRipple

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun CustomMinimalBottomNav(
    navController: NavController,
    modifier: Modifier = Modifier,
    hazeState: HazeState,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box(
        modifier = modifier
            .fillMaxWidth()
//            .height(50.dp) // Adjust height for better visibility
        , // Floating effect
        contentAlignment = Alignment.Center
    ) {

        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
//                .clip(RoundedCornerShape(50))
                .hazeEffect(
                    state = hazeState, style = HazeMaterials.ultraThin()
                ) {
                    progressive =
                        HazeProgressive.verticalGradient(startIntensity = 0.5f, endIntensity = 0.5f)
                }
                // Keep the same shape
//                .background(Color(0xFF1E1E1E).copy(alpha = 0.6f)) // Darker overlay for contrast
                .padding(horizontal = 5.dp, vertical = 10.dp)


        ) {
            listOf(
                Screen.Home,
                Screen.Month,
                Screen.Active,
                Screen.Bookmark,
                Screen.Setting,
            ).forEach { screen ->
                val isSelected = currentRoute == screen.route

                NavItem(isSelected, screen, Modifier.weight(1f).height(60.dp)) {
                    if (!isSelected) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NavItem(isSelected: Boolean, screen: Screen, modifier: Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp)) // Apply rounded corners
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current // Uses default ripple effect
            ) { onClick() }
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Icon(
            painterResource(screen.icon),
            contentDescription = screen.label,
            tint = if (isSelected) Color.Black else Color.White
        )
        Text(
            text = screen.label,
            style = TextStyle(fontFamily = Jost, fontWeight = FontWeight.Normal),
            fontSize = 14.sp, color = if (isSelected) Color.Black else Color.White
        )
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(2.dp)
                    .background(color = Color.Black)
                    .align(Alignment.CenterHorizontally),
            )
        }
    }
}

