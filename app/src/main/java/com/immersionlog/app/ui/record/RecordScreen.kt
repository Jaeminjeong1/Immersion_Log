package com.immersionlog.app.ui.record

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            (1..5).forEach { score ->
                val isSelected = uiState.value.score == score
                Button(
                    onClick = { viewModel.updateScore(score) },
                    colors = if (isSelected) {
                        // 선택된 버튼은 주색(primary)으로 표시
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor   = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor   = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "$score")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.value.minutes,
            onValueChange = { viewModel.updateMinutes(it) },
            label = { Text("집중 시간(분)") },
            isError = uiState.value.minutesError != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        uiState.value.minutesError?.let { msg ->
            Text(
                text = msg,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.value.category,
            onValueChange = { viewModel.updateCategory(it) },
            label = { Text("카테고리") },
            isError = uiState.value.categoryError != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )
        uiState.value.categoryError?.let { msg ->
            Text(
                text = msg,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }


        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.value.memo,
            onValueChange = { viewModel.updateMemo(it) },
            label = { Text("메모") },
            minLines = 3,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
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
