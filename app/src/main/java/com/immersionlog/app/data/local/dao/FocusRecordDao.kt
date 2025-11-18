package com.immersionlog.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.immersionlog.app.domain.entity.FocusRecordEntity

@Dao
interface FocusRecordDao {

    @Insert
    suspend fun insert(record: FocusRecordEntity)

    @Query("SELECT * FROM focus_records ORDER BY date DESC")
    suspend fun getAll(): List<FocusRecordEntity>

    @Update
    suspend fun update(record: FocusRecordEntity)

    @Delete
    suspend fun delete(record: FocusRecordEntity)

    @Query("SELECT * FROM focus_records WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): FocusRecordEntity?

    // 특정 날짜의 모든 기록을 반환한다. 날짜는 yyyy-MM-dd 문자열 형식을 따른다.
    @Query("SELECT * FROM focus_records WHERE date = :date ORDER BY date DESC")
    suspend fun getByDate(date: String): List<FocusRecordEntity>
}
