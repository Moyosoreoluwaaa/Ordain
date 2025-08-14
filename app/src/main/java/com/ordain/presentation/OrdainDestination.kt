package com.ordain.presentation

sealed class OrdainDestination(val route: String) {
    object TodoList : OrdainDestination("todoList")
    object JournalingEntry :
        OrdainDestination("journalingEntry?entryId={entryId}&templateId={templateId}") {
        fun createRoute(templateId: String? = null, entryId: String? = null): String {
            val baseRoute = "journalingEntry"
            val params = mutableListOf<String>()

            if (entryId != null) {
                params.add("entryId=$entryId")
            }
            if (templateId != null) {
                params.add("templateId=$templateId")
            }

            return if (params.isNotEmpty()) {
                "$baseRoute?${params.joinToString("&")}"
            } else {
                baseRoute
            }
        }
    }

    object Pomodoro : OrdainDestination("pomodoro")
    object GoalDetail : OrdainDestination("goalDetail/{goalId}") {
        fun createRoute(goalId: String) = "goalDetail/$goalId"
    }
    object AddGoal : OrdainDestination("addGoal")
}
