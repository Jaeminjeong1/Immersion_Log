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
    recordId: Long? = null,
    viewModel: RecordViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(recordId) {
        recordId?.let { viewModel.loadRecord(it) }
    }

    if (uiState.value.isSaved) {
        onSaved()
        return
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

        Button(onClick = {
            if (uiState.value.isEditing) {
                // 수정 모드일 때는 업데이트 UseCase 실행
                recordId?.let { viewModel.updateRecord(it) }
            } else {
                // 새로 작성 모드일 때는 저장 UseCase 실행
                viewModel.saveRecord()
            }
        }) {
            Text(if (uiState.value.isEditing) "수정 완료" else "저장하기")
        }
    }
}
