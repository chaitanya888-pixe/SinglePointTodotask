package com.sample.singlepointtask.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sample.todocore.presentation.SharedViewModel
import com.sample.todohome.presentation.HomeScreen
import kotlinx.coroutines.launch


@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = NavigationIem.HOME.route
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val sharedViewModel = hiltViewModel<SharedViewModel>()
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) {
        Column {
            NavHost(
                modifier = Modifier,
                navController = navController,
                startDestination = startDestination
            ) {

                composable(NavigationIem.HOME.route) {
                    HomeScreen(
                        sharedViewModel,
                        onNavigation = {
                            navController.navigate(NavigationIem.ADD_TASK.route)
                        },
                        onSnackBarMessage = {
                            scope.launch {
                                snackBarHostState.showSnackbar(it)
                            }
                        }
                    )
                }
                composable(NavigationIem.ADD_TASK.route) {
                    com.sample.todocreate.presentation.TaskCreateScreen(
                        sharedViewModel,
                        onNavigation = {
                            navController.popBackStack()
                        },
                        onSnackBarMessage = {
                            scope.launch {
                                snackBarHostState.showSnackbar(it)
                            }
                        }
                    )
                }
            }
        }
    }
}

