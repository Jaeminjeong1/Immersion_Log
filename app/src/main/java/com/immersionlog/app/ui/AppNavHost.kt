package com.immersionlog.app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.immersionlog.app.ui.daily.DailyScreen
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

        // 새 기록 작성 화면
        composable("record") {
            RecordScreen(
                onSaved = { navController.popBackStack() }
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
                onRecordClick = { id ->
                    navController.navigate("detail/$id")
                }
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


    }
}
