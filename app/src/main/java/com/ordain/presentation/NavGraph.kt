package com.ordain.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ordain.ui.addgoal.AddGoalScreen
import com.ordain.ui.goals.GoalsScreen
import com.ordain.ui.goals.detail.GoalDetailScreen
import com.ordain.ui.journaling.entry.JournalingScreen
import com.ordain.ui.journaling.templates.JournalingTemplatesScreen
import com.ordain.ui.pomodoro.PomodoroScreen
import com.ordain.ui.todo.TodoListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = OrdainDestination.TodoList.route) {
        composable(OrdainDestination.TodoList.route) {
            TodoListScreen()
        }
        composable(OrdainBottomNavItem.Journaling.route) {
            JournalingTemplatesScreen(
                onTemplateSelected = { templateId ->
                    navController.navigate(OrdainDestination.JournalingEntry.createRoute(templateId = templateId))
                },
                onEntrySelected = { entryId ->
                    navController.navigate(OrdainDestination.JournalingEntry.createRoute(entryId = entryId))
                }
            )
        }
        composable(
            route = OrdainDestination.JournalingEntry.route,
            arguments = listOf(
                navArgument("templateId") { type = NavType.StringType; nullable = true },
                navArgument("entryId") { type = NavType.StringType; nullable = true }
            )
        ) { backStackEntry ->
            JournalingScreen(
                onSaveSuccess = {
                    navController.popBackStack()
                }
            )
        }
        composable(OrdainDestination.Pomodoro.route) {
            PomodoroScreen()
        }

        composable(OrdainBottomNavItem.Goals.route) {
            GoalsScreen(
                onAddGoalClick = { navController.navigate(OrdainDestination.AddGoal.route) },
                onGoalClick = { goalId ->
                    navController.navigate(OrdainDestination.GoalDetail.createRoute(goalId))
                }
            )
        }
        composable(OrdainDestination.AddGoal.route) {
            AddGoalScreen(onGoalAdded = { navController.popBackStack() })
        }
        composable(
            route = OrdainDestination.GoalDetail.route,
            arguments = listOf(navArgument("goalId") { type = NavType.StringType })
        ) { backStackEntry ->
            val goalId = backStackEntry.arguments?.getString("goalId") ?: ""
            GoalDetailScreen(goalId = goalId)
        }
    }
}