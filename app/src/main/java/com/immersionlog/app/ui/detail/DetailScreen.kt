package com.immersionlog.app.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DetailScreen(
    recordId: Long,
    onBack: () -> Unit,
    onEdit: (Long) -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(recordId) {
        viewModel.loadRecord(recordId)
    }

    if (uiState.value.isDeleted) {
        onBack()
    }

    val record = uiState.value.record

    record?.let { rec ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text("날짜: ${rec.date}")
            Text("점수: ${rec.score}")
            Text("시간: ${rec.minutes}분")
            Text("카테고리: ${rec.category}")
            Text("메모: ${rec.memo}")

            Spacer(modifier = Modifier.height(20.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = { onEdit(rec.id) }
                ) {
                    Text("수정")
                }
                Button(
                    onClick = { viewModel.deleteRecord() }
                ) {
                    // 삭제 버튼은 빨간색으로 표시
                    Text("삭제", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    } ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("기록을 불러오는 중...")
        }
    }
}