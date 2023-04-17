package com.jakubrzeznicki.apartmentmanager

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by jrzeznicki on 17/04/2023.
 */
class ApartmentManagerAppState(
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState,
    val snackbarScope: CoroutineScope,
) {
    fun showSnackbar(message: String, duration: SnackbarDuration = SnackbarDuration.Short) {
        snackbarScope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = duration
            )
        }
    }
}

@Composable
fun rememberSnackbarDemoAppState(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navController: NavHostController = rememberNavController(),
    snackbarScope: CoroutineScope = rememberCoroutineScope()
) = remember(snackbarHostState, navController, snackbarScope) {
    ApartmentManagerAppState(
        snackbarHostState = snackbarHostState,
        navController = navController,
        snackbarScope = snackbarScope
    )
}