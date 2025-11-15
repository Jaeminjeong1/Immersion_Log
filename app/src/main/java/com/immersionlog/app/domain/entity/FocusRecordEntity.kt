package com.immersionlog.app.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "focus_records")
data class FocusRecordEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val date: String,
    val score: Int,
    val minutes: Int,
    val category: String,
    val memo: String
)
