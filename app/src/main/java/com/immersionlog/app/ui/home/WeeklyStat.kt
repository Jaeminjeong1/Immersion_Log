package com.immersionlog.app.ui.home

import java.time.LocalDate

data class WeeklyStat(
    val date: LocalDate,
    val dayLabel: String,
    val dateLabel: String,
    val avgMinutes: Int,
    val avgScore: Int
)