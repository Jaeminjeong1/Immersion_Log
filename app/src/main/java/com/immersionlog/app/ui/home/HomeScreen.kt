package com.immersionlog.app.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    onRecordClick: () -> Unit,
    onListClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = uiState.value.growthMessage, style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onRecordClick) {
            Text("오늘의 몰입 기록하기")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onListClick) {
            Text("기록 목록 보기")
        }
    }
}
