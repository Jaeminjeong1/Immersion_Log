package com.immersionlog.app.ui.daily

import com.immersionlog.app.domain.entity.FocusRecord

data class DailyUiState(
    val date: String = "",
    val records: List<FocusRecord> = emptyList(),
    val isDeleted: Boolean = false
)
