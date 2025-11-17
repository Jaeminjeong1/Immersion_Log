package com.immersionlog.app.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DetailScreen(
    onBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    val record = uiState.value.record

    if (uiState.value.isDeleted) {
        onBack()
    }

    if (record != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {

            Text("날짜: ${record.date}")
            Text("점수: ${record.score}")
            Text("시간: ${record.minutes}분")
            Text("카테고리: ${record.category}")
            Text("메모: ${record.memo}")

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { viewModel.deleteRecord() }) {
                Text("삭제", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
