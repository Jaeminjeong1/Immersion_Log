package com.immersionlog.app.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun getTodayAsString(): String {
    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return today.format(formatter)
}