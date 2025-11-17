package com.immersionlog.app.di

import com.immersionlog.app.domain.repository.FocusRecordRepository
import com.immersionlog.app.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideSaveRecordUseCase(
        repository: FocusRecordRepository
    ) = SaveRecordUseCase(repository)

    @Provides
    @Singleton
    fun provideGetAllRecordsUseCase(
        repository: FocusRecordRepository
    ) = GetAllRecordsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetRecordByIdUseCase(
        repository: FocusRecordRepository
    ) = GetRecordByIdUseCase(repository)

    @Provides
    @Singleton
    fun provideUpdateRecordUseCase(
        repository: FocusRecordRepository
    ) = UpdateRecordUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteRecordUseCase(
        repository: FocusRecordRepository
    ) = DeleteRecordUseCase(repository)
}
