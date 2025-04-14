package com.example.tabdealinterviewproject.cargo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tabdealinterviewproject.cargo.ui.CargoListScreen

val CARGO_LIST:String = "cargo_list"

@Composable
fun CargoNavHost(
    navController: NavHostController,
    viewModel: CargoViewModel
) {
    NavHost(navController = navController, startDestination = CARGO_LIST) {
        composable(CARGO_LIST) {
            CargoListScreen(
                viewModel = viewModel
            )
        }
    }
}