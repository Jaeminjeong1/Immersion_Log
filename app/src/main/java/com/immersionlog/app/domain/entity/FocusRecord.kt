package com.immersionlog.app.domain.entity

data class FocusRecord(
    val id: Long = 0,
    val date: String,
    val score: Int,
    val minutes: Int,
    val category: String,
    val memo: String
)
