package com.sample.singlepointtask.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sample.singlepointtask.presentation.ui.HomeScreen
import com.sample.singlepointtask.presentation.ui.TaskCreateScreen
import com.sample.singlepointtask.presentation.viewmodel.SharedViewModel
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
                        onNavigation ={
                            navController.navigate(NavigationIem.ADD_TASK.route)
                        },
                        onSnackBarMessage={
                            scope.launch {
                                snackBarHostState.showSnackbar(it)
                            }
                        }
                    )
                }
                composable(NavigationIem.ADD_TASK.route) {
                    TaskCreateScreen(
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

