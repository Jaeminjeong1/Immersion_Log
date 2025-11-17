package com.immersionlog.app.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.immersionlog.app.domain.usecase.DeleteRecordUseCase
import com.immersionlog.app.domain.usecase.GetRecordByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getRecordByIdUseCase: GetRecordByIdUseCase,
    private val deleteRecordUseCase: DeleteRecordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState = _uiState

    fun loadRecord(id: Long) = viewModelScope.launch {
        val item = getRecordByIdUseCase(id)
        _uiState.update { it.copy(record = item) }
    }

    fun deleteRecord() = viewModelScope.launch {
        uiState.value.record?.let {
            deleteRecordUseCase(it)
            _uiState.update { s -> s.copy(isDeleted = true) }
        }
    }
}
