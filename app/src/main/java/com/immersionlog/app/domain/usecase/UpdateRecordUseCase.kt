package com.immersionlog.app.domain.usecase

import com.immersionlog.app.domain.entity.FocusRecord
import com.immersionlog.app.domain.repository.FocusRecordRepository
import javax.inject.Inject

class UpdateRecordUseCase @Inject constructor(
    private val repository: FocusRecordRepository
) {
    suspend operator fun invoke(record: FocusRecord) {
        repository.update(record)
    }
}
