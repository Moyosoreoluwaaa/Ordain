package com.ordain.ui.pomodoro

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ordain.R
import com.ordain.domain.model.PomodoroSession
import com.ordain.presentation.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PomodoroScreen( navController: NavHostController,
    viewModel: PomodoroViewModel = hiltViewModel()
                  ) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.pomodoro_timer_title)) }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = formatTime(uiState.timeLeftInSeconds),
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = viewModel::onStartPauseClicked,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = if (uiState.isRunning) stringResource(R.string.pause_timer) else stringResource(
                            R.string.start_timer
                        )
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = viewModel::onResetClicked,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.reset_timer))
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.sessions_today),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            DailySessionsChart(sessions = uiState.sessionsToday)
        }
    }
}

@Composable
fun DailySessionsChart(sessions: List<PomodoroSession>) {
    if (sessions.isNotEmpty()) {
        val maxDuration = sessions.maxOfOrNull { it.durationMinutes } ?: 0
        val chartHeight = 200.dp

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(chartHeight)
                .padding(16.dp)
        ) {
            val barWidth = size.width / sessions.size
            val scaleY = size.height / maxDuration

            sessions.forEachIndexed { index, session ->
                val barHeight = session.durationMinutes * scaleY
                val x = index * barWidth

                drawRect(
                    color = Color.Yellow,
                    topLeft = Offset(x, size.height - barHeight),
                    size = Size(barWidth * 0.8f, barHeight)
                )

                // Draw value text
                drawContext.canvas.nativeCanvas.drawText(
                    "${session.durationMinutes}m",
                    x + barWidth * 0.4f,
                    size.height - barHeight - 10f,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 32f
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }
        }
    } else {
        Text(text = "No sessions completed yet.")
    }
}

@SuppressLint("DefaultLocale")
private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}