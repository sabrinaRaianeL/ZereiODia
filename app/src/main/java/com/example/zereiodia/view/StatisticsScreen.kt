package com.example.zereiodia.view

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zereiodia.viewmodel.StatisticsUiState

@Composable
fun StatisticsScreen(uiState: StatisticsUiState) {

    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color(0xFF121212) else Color.White

    val primaryColor = if (isDark) Color(0xFFFFB2C9) else Color(0xFFF06292)
    val primaryContainerColor = if (isDark) Color(0xFF702B3E) else Color(0xFFFCE4EC)
    val onPrimaryContainerColor = if (isDark) Color(0xFFFFD9E2) else Color(0xFF880E4F)

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Dashboard de Produtividade",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )

            // Card para exibir Taxa de Conclusão Diária com a data atual
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = primaryContainerColor
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Taxa de Conclusão Diária: ${uiState.currentDate}",
                        fontSize = 14.sp,
                        color = onPrimaryContainerColor,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${String.format("%.1f", uiState.completionPercentage)}%",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = onPrimaryContainerColor
                    )
                }
            }

            // Cards de contagem de tasks do dia (Total, Concluídas, Pendentes)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    title = "Total Hoje",
                    value = uiState.totalTasks.toString(),
                    containerColor = primaryContainerColor,
                    contentColor = onPrimaryContainerColor,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Concluídas",
                    value = uiState.completedTasks.toString(),
                    containerColor = primaryContainerColor,
                    contentColor = onPrimaryContainerColor,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Pendentes",
                    value = uiState.pendingTasks.toString(),
                    containerColor = primaryContainerColor,
                    contentColor = onPrimaryContainerColor,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 13.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}