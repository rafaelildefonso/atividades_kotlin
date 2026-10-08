package com.rafaelildefonso.diariotreinos.userinterface

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rafaelildefonso.diariotreinos.viewmodel.DiarioViewModel

@Composable
fun EstatisticasScreen(modifier: Modifier = Modifier, viewModel: DiarioViewModel) {
    val total = viewModel.diarios.size
    val concluidos = viewModel.diarios.count { it.concluidoHoje }

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Progresso de hoje", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(24.dp))
        Text("$concluidos de $total diarios concluídos", style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(16.dp))
        LinearProgressIndicator(
            progress = { if (total == 0) 0f else concluidos.toFloat() / total },
            modifier = Modifier.fillMaxWidth().height(10.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}