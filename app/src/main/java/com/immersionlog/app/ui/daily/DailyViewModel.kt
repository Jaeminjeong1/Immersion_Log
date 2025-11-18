package com.immersionlog.app.ui.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.immersionlog.app.domain.entity.FocusRecord
import com.immersionlog.app.domain.usecase.DeleteRecordUseCase
import com.immersionlog.app.domain.usecase.GetRecordsByDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(
    private val getRecordsByDateUseCase: GetRecordsByDateUseCase,
    private val deleteRecordUseCase: DeleteRecordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DailyUiState())
    val uiState = _uiState

    fun loadRecords(date: String) = viewModelScope.launch {
        val items = getRecordsByDateUseCase(date)
        _uiState.update { it.copy(date = date, records = items) }
    }

    fun deleteRecord(record: FocusRecord) = viewModelScope.launch {
        deleteRecordUseCase(record)
        // 삭제 후 다시 목록을 로드하거나, 플래그를 설정해 화면을 닫음
        _uiState.update { it.copy(isDeleted = true) }
    }
}
