package com.immersionlog.app.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.immersionlog.app.ui.daily.DailyScreen
import com.immersionlog.app.ui.detail.DetailScreen
import com.immersionlog.app.ui.home.HomeScreen
import com.immersionlog.app.ui.home.HomeViewModel
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

        // 새 기록 작성 화면
        composable("record") {
            val parentEntry = navController.getBackStackEntry("home")
            val homeViewModel: HomeViewModel = hiltViewModel(parentEntry)
            RecordScreen(
                onSaved = {
                    homeViewModel.refresh()
                    navController.popBackStack()
                }
            )
        }

        // 기록 편집 화면 - id 파라미터 사용
        composable(
            route = "record/{recordId}",
            arguments = listOf(navArgument("recordId") { type = NavType.LongType })
        ) { backStackEntry ->
            val recordId = backStackEntry.arguments?.getLong("recordId")
            RecordScreen(
                onSaved = { navController.popBackStack() },
                recordId = recordId
            )
        }

        composable("list") {
            ListScreen(
                onDateClick = { date ->
                    navController.navigate("daily/$date")
                },
            )
        }

        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: return@composable
            DetailScreen(
                recordId = id,
                onBack = { navController.popBackStack() },
                onEdit = { recordId -> navController.navigate("record/$recordId") }
            )
        }

        composable(
            route = "daily/{date}",
            arguments = listOf(navArgument("date") { type = NavType.StringType })
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date").orEmpty()
            DailyScreen(
                date = date,
                onBack = { navController.popBackStack() },
                onEdit = { recordId ->
                    navController.navigate("record/$recordId")
                }
            )
        }

    }
}
