package com.immersionlog.app.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.immersionlog.app.domain.entity.FocusRecord
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign

@Composable
fun HomeScreen(
    onRecordClick: () -> Unit,
    onListClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    val selectedIndexState = remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = uiState.value.growthMessage, style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(20.dp))

        WeeklyNavigationRow(
            weeklyStats = uiState.value.weeklyStats,
            onPrevious = { viewModel.previousWeek() },
            onNext = { viewModel.nextWeek() }
        )

        Spacer(modifier = Modifier.height(8.dp))

        WeeklyStatsChartSection(
            stats = uiState.value.weeklyStats,
            selectedIndex = selectedIndexState.value,
            onPointSelected = { idx -> selectedIndexState.value = idx }
        )

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

@Composable
private fun WeeklyLineChart(
    stats: List<WeeklyStat>,
    selectedIndex: Int?,
    onPointSelected: (Int) -> Unit
) {
    if (stats.isEmpty()) return

    val maxMinutes = stats.maxOf { it.avgMinutes }.coerceAtLeast(1)
    val maxScore   = stats.maxOf { it.avgScore }.coerceAtLeast(1)

    val minuteTicks = (0..4).map { (maxMinutes * it / 4f).toInt() }
    val scoreTicks  = (0..maxScore).toList()

    val minutesColor = MaterialTheme.colorScheme.primary
    val scoreColor   = MaterialTheme.colorScheme.tertiary

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            // 왼쪽 축: 시간(분)
            Column(
                modifier = Modifier
                    .width(48.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                minuteTicks.sortedDescending().forEach { tick ->
                    Text(
                        text = tick.toString(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            // 그래프 영역 (터치 이벤트 처리 포함)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .pointerInput(stats) {
                        detectTapGestures { offset ->
                            val totalWidth = size.width
                            val count = stats.size
                            if (count > 1) {
                                val step = totalWidth / (count - 1)
                                val index = kotlin.math.round(offset.x / step).toInt()
                                    .coerceIn(0, count - 1)
                                onPointSelected(index)
                            } else {
                                onPointSelected(0)
                            }
                        }
                    }
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val h = size.height
                    val w = size.width
                    val stepX = if (stats.size > 1) w / (stats.size - 1) else 0f

                    val minutesPath = Path()
                    val scorePath   = Path()

                    stats.forEachIndexed { index, stat ->
                        val x = stepX * index
                        val yMinutes = h - (stat.avgMinutes.toFloat() / maxMinutes) * h
                        val yScore   = h - (stat.avgScore.toFloat()   / maxScore)   * h
                        if (index == 0) {
                            minutesPath.moveTo(x, yMinutes)
                            scorePath.moveTo(x, yScore)
                        } else {
                            minutesPath.lineTo(x, yMinutes)
                            scorePath.lineTo(x, yScore)
                        }
                    }

                    drawPath(minutesPath, color = minutesColor, style = Stroke(width = 2.dp.toPx()))
                    drawPath(scorePath,   color = scoreColor,   style = Stroke(width = 2.dp.toPx()))

                    stats.forEachIndexed { index, stat ->
                        val x = stepX * index
                        val yMinutes = h - (stat.avgMinutes.toFloat() / maxMinutes) * h
                        val yScore   = h - (stat.avgScore.toFloat()   / maxScore)   * h
                        val isSelected = (selectedIndex == index)
                        val radiusMinutes = if (isSelected) 6.dp.toPx() else 4.dp.toPx()
                        val radiusScore   = if (isSelected) 6.dp.toPx() else 4.dp.toPx()
                        drawCircle(minutesColor, radius = radiusMinutes, center = Offset(x, yMinutes))
                        drawCircle(scoreColor,   radius = radiusScore,   center = Offset(x, yScore))
                    }
                }
            }

            // 오른쪽 축: 몰입 점수
            Column(
                modifier = Modifier
                    .width(48.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                scoreTicks.sortedDescending().forEach { tick ->
                    Text(
                        text = tick.toString(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 하단 X축 라벨 (요일 + 날짜)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            stats.forEach { stat ->
                Text(
                    text = "${stat.dayLabel}\n${stat.dateLabel}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun WeeklyNavigationRow(
    weeklyStats: List<WeeklyStat>,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPrevious) {
            Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "지난 주")
        }

        // 주간 범위 텍스트: 예) 11.11(월) ~ 11.17(일)
        val start = weeklyStats.firstOrNull()
        val end   = weeklyStats.lastOrNull()
        Text(
            text = if (start != null && end != null)
                "${start.dateLabel}(${start.dayLabel}) ~ ${end.dateLabel}(${end.dayLabel})"
            else "",
            style = MaterialTheme.typography.bodyMedium
        )

        IconButton(onClick = onNext) {
            Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "다음 주")
        }
    }
}

@Composable
fun WeeklyStatsChartSection(
    stats: List<WeeklyStat>,
    selectedIndex: Int?,
    onPointSelected: (Int) -> Unit
) {
    if (stats.isEmpty()) return

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LegendItem(color = MaterialTheme.colorScheme.primary, label = "시간(분)")
        LegendItem(color = MaterialTheme.colorScheme.tertiary, label = "몰입 점수")
    }

    WeeklyLineChart(
        stats = stats,
        selectedIndex = selectedIndex,
        onPointSelected = onPointSelected
    )

    Spacer(modifier = Modifier.height(8.dp))

    selectedIndex?.let { idx ->
        val stat = stats[idx]
        Text(
            text = "${stat.dayLabel}(${stat.dateLabel}): ${stat.avgScore}점 / ${stat.avgMinutes}분",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun LegendItem(color: androidx.compose.ui.graphics.Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color = color, shape = CircleShape)
        )
        Text(
            text = " $label",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

