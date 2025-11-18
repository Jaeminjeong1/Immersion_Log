package com.immersionlog.app.ui.record

data class RecordUiState(
    val score: Int = 0,
    val minutes: String = "",
    val memo: String = "",
    val category: String = "",
    val isEditing: Boolean = false,
    val isSaved: Boolean = false,
    val minutesError: String? = null,
    val categoryError: String? = null
)
