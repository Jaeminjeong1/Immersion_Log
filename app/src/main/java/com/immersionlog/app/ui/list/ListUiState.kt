package com.immersionlog.app.ui.list

import com.immersionlog.app.domain.entity.FocusRecord

data class ListUiState(
    val records: List<FocusRecord> = emptyList()
)