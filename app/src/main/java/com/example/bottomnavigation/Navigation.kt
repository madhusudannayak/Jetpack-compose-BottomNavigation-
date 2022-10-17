package com.example.bottomnavigation

import androidx.annotation.DrawableRes
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class Screens(val title: String,
                     val route: String,
                     @DrawableRes val icons: Int) {
    object Home : Screens(
        title = "Home", "home_route",
        icons = R.drawable.ic_baseline_home
    )

    object Notification : Screens(
        title = "Notification", "notification_route",
        icons = R.drawable.ic_baseline_notifications
    )

    object Message :
        Screens(
            title = "Message",
            "message_route",
            icons = R.drawable.ic_baseline_message
        )
}

@Composable
fun BottomNavHost(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Screens.Message.route) {
        composable(route = Screens.Home.route) {
            Home()
        }
        composable(route = Screens.Notification.route) {
            Notification()
        }
        composable(route = Screens.Message.route) {
            Message()
        }
    }

}

@Composable
fun BottomNavigationScreen(navController: NavController, items: List<Screens>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    BottomNavigation() {
        items.forEach { screens ->
            BottomNavigationItem(
                selected = currentDestination?.route == screens.route,
                onClick = {
                    navController.navigate(screens.route) {
                        launchSingleTop = true
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = screens.icons),
                        contentDescription = null
                    )
                },
                label = { Text(text = screens.title) },

            )

        }

    }
}