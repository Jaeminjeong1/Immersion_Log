package com.immersionlog.app.domain.usecase

import com.immersionlog.app.domain.entity.FocusRecord
import com.immersionlog.app.domain.repository.FocusRecordRepository

// 특정 날짜의 기록 목록을 반환하는 유스케이스
class GetRecordsByDateUseCase(
    private val repository: FocusRecordRepository
) {
    suspend operator fun invoke(date: String): List<FocusRecord> {
        return repository.getByDate(date)
    }
}
