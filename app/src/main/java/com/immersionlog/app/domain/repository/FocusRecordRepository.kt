package com.immersionlog.app.domain.repository

import com.immersionlog.app.domain.entity.FocusRecord

interface FocusRecordRepository {
    suspend fun insert(record: FocusRecord)
    suspend fun getAll(): List<FocusRecord>
    suspend fun getById(id: Long): FocusRecord?
    suspend fun update(record: FocusRecord)
    suspend fun delete(record: FocusRecord)
    suspend fun getByDate(date: String): List<FocusRecord>
}
