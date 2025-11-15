package com.immersionlog.app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.immersionlog.app.data.local.dao.FocusRecordDao
import com.immersionlog.app.domain.entity.FocusRecordEntity


@Database(
    entities = [FocusRecordEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun focusRecordDao(): FocusRecordDao
}
