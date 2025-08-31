package com.example.littlelemon.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.littlelemon.ui.screens.*
import com.example.littlelemon.viewmodel.UserViewModel

@Composable
fun Navigation(navController: NavHostController, viewModel: UserViewModel) {
    // Use isOnboarded to determine start destination
    if (viewModel.isLoading) {
        Loading()
        return
    }

    val startDestination = if (viewModel.isOnboarded) {
        Home.route
    } else {
        Onboarding.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Home.route) {
            Home(navController = navController, viewModel = viewModel)
        }
        composable(Profile.route) {
            Profile(navController = navController, viewModel = viewModel)
        }
        composable(Onboarding.route) {
            Onboarding(navController = navController, viewModel = viewModel)
        }
    }
}
