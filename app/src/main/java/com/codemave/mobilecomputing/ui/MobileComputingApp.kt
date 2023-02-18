package com.codemave.mobilecomputing.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codemave.mobilecomputing.ui.home.Home
import com.codemave.mobilecomputing.ui.login.Login
import com.codemave.mobilecomputing.ui.maps.PaymentLocationMap
import com.codemave.mobilecomputing.ui.payment.Payment
import com.codemave.mobilecomputing.ui.reminder.Reminder
import com.codemave.mobilecomputing.ui.profile.Profile

@Composable
fun MobileComputingApp(
    appState: MobileComputingAppState = rememberMobileComputingAppState()
) {
    NavHost(
        navController = appState.navController,
        startDestination = "login"
    ) {
        composable(route = "login") {
            Login(navController = appState.navController)
        }
        composable(route = "home") {
            Home(
                navController = appState.navController
            )
        }
        composable(route = "profile") {
            Profile(navController = appState.navController)
        }
        composable(route = "payment") {
            Payment(onBackPress = appState::navigateBack, navController = appState.navController)
        }
        composable(route = "reminder") {
            Reminder(onBackPress = appState::navigateBack, navController = appState.navController)
        }
        composable(route = "map") {
            PaymentLocationMap(navController = appState.navController)
        }

    }
}