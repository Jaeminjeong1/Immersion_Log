package com.immersionlog.app.ui.daily

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.immersionlog.app.domain.entity.FocusRecord

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyScreen(
    date: String,
    onBack: () -> Unit,
    onEdit: (Long) -> Unit,
    viewModel: DailyViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(date) {
        viewModel.loadRecords(date)
    }

    if (uiState.value.isDeleted) {
        onBack()
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(date) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        val records = uiState.value.records
        if (records.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("기록이 없습니다.")
            }
        } else {
            LazyColumn(
                contentPadding = innerPadding,
                modifier = Modifier.fillMaxSize()
            ) {
                items(records) { item ->
                    DailyRecordCard(
                        record = item,
                        onEdit = { onEdit(item.id) },
                        onDelete = { viewModel.deleteRecord(item) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun DailyRecordCard(
    record: FocusRecord,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("점수: ${record.score}")
            Text("시간: ${record.minutes}분")
            Text("카테고리: ${record.category}")
            Text("메모: ${record.memo}")

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Button(onClick = onEdit) {
                    Text("수정")
                }
                Spacer(modifier = Modifier.width(12.dp))
                Button(onClick = onDelete) {
                    Text("삭제", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
