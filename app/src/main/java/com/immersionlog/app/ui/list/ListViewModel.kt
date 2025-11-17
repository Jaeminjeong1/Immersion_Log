package com.immersionlog.app.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.immersionlog.app.domain.usecase.GetAllRecordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getAllRecordsUseCase: GetAllRecordsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListUiState())
    val uiState = _uiState

    init {
        loadRecords()
    }

    fun loadRecords() = viewModelScope.launch {
        val items = getAllRecordsUseCase()
        _uiState.update { it.copy(records = items) }
    }
}
