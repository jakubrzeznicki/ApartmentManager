package com.jakubrzeznicki.apartmentmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jakubrzeznicki.apartmentmanager.createpin.screen.CreatePinRoute
import com.jakubrzeznicki.apartmentmanager.home.screen.HomeRoute
import com.jakubrzeznicki.apartmentmanager.navigation.ApartmentManagerDestinations
import com.jakubrzeznicki.apartmentmanager.utils.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as ApartmentManagerApplication).appComponent.inject(this)
        setContent {
            val appState: ApartmentManagerAppState = rememberSnackbarDemoAppState()
            Scaffold(
                snackbarHost = { SnackbarHost(hostState = appState.snackbarHostState) }
            ) {
                CustomNavHost(
                    activity = this,
                    application = application as ApartmentManagerApplication,
                    navController = appState.navController,
                    showSnackbar = { message, duration ->
                        appState.showSnackbar(message = message, duration = duration)
                    }
                )
            }
        }
    }
}

@Composable
private fun CustomNavHost(
    activity: MainActivity,
    application: ApartmentManagerApplication,
    navController: NavHostController,
    showSnackbar: (String, SnackbarDuration) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = ApartmentManagerDestinations.HOME_ROUTE
    ) {
        composable(ApartmentManagerDestinations.HOME_ROUTE) {
            val homeViewModel by viewModel(activity) { application.appComponent.homeViewModel }
            HomeRoute(
                viewModel = homeViewModel,
                showSnackbar = showSnackbar,
                onCreatePinClick = {
                    navController.navigate(ApartmentManagerDestinations.CREATE_PIN_ROUTE)
                }
            )
        }
        composable(ApartmentManagerDestinations.CREATE_PIN_ROUTE) {
            val createPinViewModel by viewModel(activity) { application.appComponent.createPinViewModel }
            CreatePinRoute(
                viewModel = createPinViewModel,
                showSnackbar = showSnackbar,
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}