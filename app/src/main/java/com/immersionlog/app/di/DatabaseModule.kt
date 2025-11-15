package com.immersionlog.app.di

import android.content.Context
import androidx.room.Room
import com.immersionlog.app.data.local.dao.FocusRecordDao
import com.immersionlog.app.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "immersion_log.db"
    ).build()

    @Provides
    fun provideFocusRecordDao(db: AppDatabase): FocusRecordDao =
        db.focusRecordDao()
}
