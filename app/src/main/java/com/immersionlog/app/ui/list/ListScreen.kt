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
import kotlin.math.roundToInt

@Composable
fun ListScreen(
    onDateClick: (String) -> Unit,
    viewModel: ListViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val grouped = uiState.value.records.groupBy { it.date }
    val sortedDates = grouped.keys.sortedDescending()

    LaunchedEffect(Unit) {
        viewModel.loadRecords()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(sortedDates) { date ->
            val dayRecords = grouped[date] ?: emptyList()
            val totalMinutes = dayRecords.sumOf { it.minutes }
            val averageScore = if (dayRecords.isNotEmpty()) {
                dayRecords.map { it.score }.average().roundToInt()
            } else 0

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDateClick(date) }
                    .padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = date, style = MaterialTheme.typography.titleMedium)
                    Text("평균 점수: $averageScore")
                    Text("총 집중 시간: ${totalMinutes}분")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
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
}


