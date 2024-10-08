package edu.nitt.delta.orientation22.compose.navigation

import androidx.compose.ui.graphics.Color

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: Int,
    val color: Color,
)