package com.immersionlog.app.domain.usecase

import com.immersionlog.app.domain.entity.FocusRecord
import com.immersionlog.app.domain.repository.FocusRecordRepository

class GetRecordsByDateUseCase(
    private val repository: FocusRecordRepository
) {
    suspend operator fun invoke(date: String): List<FocusRecord> {
        return repository.getByDate(date)
    }
}
