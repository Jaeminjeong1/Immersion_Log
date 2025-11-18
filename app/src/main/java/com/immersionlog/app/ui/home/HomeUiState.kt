package com.immersionlog.app.ui.home

import com.immersionlog.app.domain.entity.FocusRecord

data class HomeUiState(
    val todayRecordExists: Boolean = false,
    val growthMessage: String = "",
    val currentWeekStart: String = "",
    val weeklyStats: List<WeeklyStat> = emptyList()
)