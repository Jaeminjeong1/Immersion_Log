package com.immersionlog.app.ui.detail

import com.immersionlog.app.domain.entity.FocusRecord

data class DetailUiState(
    val record: FocusRecord? = null,
    val isDeleted: Boolean = false
)
