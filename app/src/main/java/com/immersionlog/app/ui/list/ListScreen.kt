package com.immersionlog.app.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.immersionlog.app.domain.entity.FocusRecord

@Composable
fun ListScreen(
    onRecordClick: (Long) -> Unit,
    viewModel: ListViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        items(uiState.value.records) { item ->
            ListItemCard(item = item, onClick = { onRecordClick(item.id) })
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ListItemCard(item: FocusRecord, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.date, style = MaterialTheme.typography.titleMedium)
            Text("점수: ${item.score}")
            Text("시간: ${item.minutes}분")
            Text("카테고리: ${item.category}")
        }
    }
}
