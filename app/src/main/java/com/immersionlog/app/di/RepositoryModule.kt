package com.immersionlog.app.di

import com.immersionlog.app.data.local.dao.FocusRecordDao
import com.immersionlog.app.data.repository.FocusRecordRepositoryImpl
import com.immersionlog.app.domain.repository.FocusRecordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideFocusRecordRepository(
        dao: FocusRecordDao
    ): FocusRecordRepository = FocusRecordRepositoryImpl(dao)
}
