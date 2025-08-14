package com.ordain.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.ui.graphics.vector.ImageVector

sealed class OrdainBottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object TodoList : OrdainBottomNavItem("todoList", "Todos", Icons.AutoMirrored.Filled.Assignment)
    object Journaling : OrdainBottomNavItem(
        "journalingTemplates", "Journal",
        Icons.AutoMirrored.Filled.EventNote
    )

    object Pomodoro : OrdainBottomNavItem("pomodoro", "Pomodoro", Icons.Default.Timeline)
    object Goals : OrdainBottomNavItem("goalsList", "Goals", Icons.AutoMirrored.Filled.List)
}