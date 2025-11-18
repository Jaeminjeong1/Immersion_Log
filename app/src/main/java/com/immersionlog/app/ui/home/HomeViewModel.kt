package com.immersionlog.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.immersionlog.app.domain.entity.FocusRecord
import com.immersionlog.app.domain.usecase.GetAllRecordsUseCase
import com.immersionlog.app.utils.getTodayAsString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.DayOfWeek
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllRecordsUseCase: GetAllRecordsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    private var allRecords: List<FocusRecord> = emptyList()

    private var currentWeekStartDate: LocalDate = LocalDate.now()

    init {
        loadHomeData()
    }

    fun refresh() {
        viewModelScope.launch {
            loadHomeData()
        }
    }

    private fun loadHomeData() = viewModelScope.launch {
        val records = getAllRecordsUseCase()
        allRecords = records

        val todayDate = records.firstOrNull()?.date ?: LocalDate.now().toString()
        val today = LocalDate.parse(todayDate)
        val dow = today.dayOfWeek.value
        val startOfWeek = today.minusDays((dow - DayOfWeek.MONDAY.value).toLong())
        currentWeekStartDate = startOfWeek

        updateWeeklyStats(startOfWeek)

        if (records.isNotEmpty()) {
            _uiState.update { state ->
                state.copy(
                    todayRecordExists = isToday(todayDate),
                    growthMessage = buildGrowthMessage(records)
                )
            }
        }
    }

    private fun isToday(date: String): Boolean {
        return date == getTodayAsString()
    }

    private fun buildGrowthMessage(records: List<FocusRecord>): String {
        if (records.size < 2) return "오늘의 몰입을 기록해보세요!"

        val today = records[0]
        val yesterday = records[1]

        return when {
            today.score > yesterday.score -> "몰입 점수가 상승했어요! 🔥"
            today.minutes > yesterday.minutes -> "어제보다 더 오래 집중했어요! 💪"
            else -> "꾸준함이 가장 강력한 힘이에요 ✨"
        }
    }

    private fun updateWeeklyStats(startDate: LocalDate) {
        val grouped = allRecords.groupBy { it.date }
        val formatter = DateTimeFormatter.ofPattern("MM.dd")
        val dayLabelMap = mapOf(
            DayOfWeek.MONDAY to "월",
            DayOfWeek.TUESDAY to "화",
            DayOfWeek.WEDNESDAY to "수",
            DayOfWeek.THURSDAY to "목",
            DayOfWeek.FRIDAY to "금",
            DayOfWeek.SATURDAY to "토",
            DayOfWeek.SUNDAY to "일"
        )
        val stats = (0..6).map { offset ->
            val date = startDate.plusDays(offset.toLong())
            val dateString = date.toString()
            val recordsForDay = grouped[dateString]
            val avgMinutes = recordsForDay?.map { it.minutes }?.average()?.toInt() ?: 0
            val avgScore = recordsForDay?.map { it.score }?.average()?.toInt() ?: 0
            val dayLabel = dayLabelMap[date.dayOfWeek] ?: date.dayOfWeek.name.substring(0, 1)
            val dateLabel = date.format(formatter)
            WeeklyStat(
                date = date,
                dayLabel = dayLabel,
                dateLabel = dateLabel,
                avgMinutes = avgMinutes,
                avgScore = avgScore
            )
        }
        currentWeekStartDate = startDate
        _uiState.update { state ->
            state.copy(
                currentWeekStart = startDate.toString(),
                weeklyStats = stats
            )
        }
    }

    fun previousWeek() {
        if (allRecords.isEmpty()) return
        val newStart = currentWeekStartDate.minusWeeks(1)
        updateWeeklyStats(newStart)
    }

    fun nextWeek() {
        if (allRecords.isEmpty()) return
        val newStart = currentWeekStartDate.plusWeeks(1)
        updateWeeklyStats(newStart)
    }
}
