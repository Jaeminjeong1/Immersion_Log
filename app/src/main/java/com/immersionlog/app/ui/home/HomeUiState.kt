package com.immersionlog.app.ui.home


data class HomeUiState(
    val todayRecordExists: Boolean = false,
    val growthMessage: String = "",
    val currentWeekStart: String = "",
    val weeklyStats: List<WeeklyStat> = emptyList()
)