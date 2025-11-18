package com.immersionlog.app.utils

import com.immersionlog.app.domain.entity.FocusRecord
import com.immersionlog.app.domain.entity.FocusRecordEntity

fun FocusRecord.toEntity(): FocusRecordEntity {
    return FocusRecordEntity(
        id = this.id,
        date = this.date,
        score = this.score,
        minutes = this.minutes,
        category = this.category,
        memo = this.memo
    )
}

fun FocusRecordEntity.toDomain(): FocusRecord {
    return FocusRecord(
        id = this.id,
        date = this.date,
        score = this.score,
        minutes = this.minutes,
        category = this.category,
        memo = this.memo
    )
}
