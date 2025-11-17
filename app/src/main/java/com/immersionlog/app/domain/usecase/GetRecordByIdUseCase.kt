package com.immersionlog.app.domain.usecase

import com.immersionlog.app.domain.entity.FocusRecord
import com.immersionlog.app.domain.repository.FocusRecordRepository
import javax.inject.Inject

class GetRecordByIdUseCase @Inject constructor(
    private val repository: FocusRecordRepository
) {
    suspend operator fun invoke(id: Long): FocusRecord? {
        return repository.getById(id)
    }
}
