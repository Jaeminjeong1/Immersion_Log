package com.immersionlog.app.ui.record

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RecordScreen(
    onSaved: () -> Unit,
    viewModel: RecordViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    if (uiState.value.isSaved) {
        onSaved()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text("몰입 점수 선택", style = MaterialTheme.typography.titleMedium)

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            (1..5).forEach { score ->
                Button(onClick = { viewModel.updateScore(score) }) {
                    Text("$score")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.value.minutes,
            onValueChange = { viewModel.updateMinutes(it) },
            label = { Text("집중 시간(분)") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.value.category,
            onValueChange = { viewModel.updateCategory(it) },
            label = { Text("카테고리") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.value.memo,
            onValueChange = { viewModel.updateMemo(it) },
            label = { Text("메모") },
            minLines = 3
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { viewModel.saveRecord() }) {
            Text("저장하기")
        }
    }
}
