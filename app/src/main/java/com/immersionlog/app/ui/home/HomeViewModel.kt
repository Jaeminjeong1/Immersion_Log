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
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllRecordsUseCase: GetAllRecordsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadHomeData()
    }

    private fun loadHomeData() = viewModelScope.launch {
        val records = getAllRecordsUseCase()

        if (records.isNotEmpty()) {
            val today = records.first().date
            _uiState.update {
                it.copy(
                    todayRecordExists = isToday(today),
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
}