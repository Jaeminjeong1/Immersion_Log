package com.immersionlog.app.ui.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.immersionlog.app.domain.entity.FocusRecord
import com.immersionlog.app.domain.usecase.GetRecordByIdUseCase
import com.immersionlog.app.domain.usecase.SaveRecordUseCase
import com.immersionlog.app.domain.usecase.UpdateRecordUseCase
import com.immersionlog.app.utils.getTodayAsString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val saveRecordUseCase: SaveRecordUseCase,
    private val updateRecordUseCase: UpdateRecordUseCase
) : ViewModel() {

    @Inject
    lateinit var getRecordByIdUseCase: GetRecordByIdUseCase

    private var editingId: Long? = null

    private val _uiState = MutableStateFlow(RecordUiState())
    val uiState: StateFlow<RecordUiState> = _uiState

    fun updateScore(score: Int) {
        _uiState.update { it.copy(score = score) }
    }

    fun updateMinutes(minutes: String) {
        _uiState.update { it.copy(minutes = minutes) }
    }

    fun updateMemo(memo: String) {
        _uiState.update { it.copy(memo = memo) }
    }

    fun updateCategory(category: String) {
        _uiState.update { it.copy(category = category) }
    }

    fun loadRecord(id: Long) = viewModelScope.launch {
        val record = getRecordByIdUseCase(id) ?: return@launch

        editingId = record.id
        setEditingMode(record)
    }

    fun setEditingMode(record: FocusRecord) {
        _uiState.update {
            it.copy(
                score = record.score,
                minutes = record.minutes.toString(),
                memo = record.memo,
                category = record.category,
                isEditing = true
            )
        }
    }

    fun saveRecord() = viewModelScope.launch {
        val state = _uiState.value
        val record = FocusRecord(
            date = getTodayAsString(),
            score = state.score,
            minutes = state.minutes.toInt(),
            memo = state.memo,
            category = state.category
        )
        saveRecordUseCase(record)

        _uiState.update { it.copy(isSaved = true) }
    }

    fun updateRecord(id: Long) = viewModelScope.launch {
        val state = _uiState.value
        val record = FocusRecord(
            id = id,
            date = getTodayAsString(),
            score = state.score,
            minutes = state.minutes.toInt(),
            memo = state.memo,
            category = state.category
        )
        updateRecordUseCase(record)
        _uiState.update { it.copy(isSaved = true) }
    }
}
