package com.sample.singlepointtask.navigation

enum class Screen {
    HOME, ADD_TASK
}

sealed class NavigationIem(val route: String) {
    data object HOME : NavigationIem(Screen.HOME.name)
    data object ADD_TASK : NavigationIem(Screen.ADD_TASK.name)
}