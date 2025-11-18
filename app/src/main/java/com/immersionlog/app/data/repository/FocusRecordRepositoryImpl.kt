package com.immersionlog.app.data.repository

import com.immersionlog.app.data.local.dao.FocusRecordDao
import com.immersionlog.app.domain.entity.FocusRecord
import com.immersionlog.app.domain.repository.FocusRecordRepository
import com.immersionlog.app.utils.toDomain
import com.immersionlog.app.utils.toEntity
import javax.inject.Inject

class FocusRecordRepositoryImpl @Inject constructor(
    private val dao: FocusRecordDao
) : FocusRecordRepository {

    override suspend fun insert(record: FocusRecord) {
        dao.insert(record.toEntity())
    }

    override suspend fun getAll(): List<FocusRecord> =
        dao.getAll().map { it.toDomain() }

    override suspend fun getById(id: Long): FocusRecord? =
        dao.getById(id)?.toDomain()

    override suspend fun update(record: FocusRecord) {
        dao.update(record.toEntity())
    }

    override suspend fun delete(record: FocusRecord) {
        dao.delete(record.toEntity())
    }

    override suspend fun getByDate(date: String): List<FocusRecord> =
        dao.getByDate(date).map { it.toDomain() }
}
