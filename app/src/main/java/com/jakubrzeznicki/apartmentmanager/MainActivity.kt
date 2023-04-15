package com.jakubrzeznicki.apartmentmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.jakubrzeznicki.apartmentmanager.home.screen.HomeRoute
import com.jakubrzeznicki.apartmentmanager.home.viewmodel.HomeViewModel
import com.jakubrzeznicki.apartmentmanager.ui.theme.ApartmentManagerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApartmentManagerTheme {
                val viewModel: HomeViewModel = hiltViewModel()
                HomeRoute(viewModel = viewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ApartmentManagerTheme {
        HomeRoute(viewModel = hiltViewModel())
    }
}