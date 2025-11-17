package com.immersionlog.app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.immersionlog.app.ui.detail.DetailScreen
import com.immersionlog.app.ui.home.HomeScreen
import com.immersionlog.app.ui.list.ListScreen
import com.immersionlog.app.ui.record.RecordScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(
                onRecordClick = { navController.navigate("record") },
                onListClick = { navController.navigate("list") }
            )
        }

        composable("record") {
            RecordScreen(
                onSaved = { navController.popBackStack() }
            )
        }

        composable("list") {
            ListScreen(
                onRecordClick = { id ->
                    navController.navigate("detail/$id")
                }
            )
        }

        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) {
            DetailScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
