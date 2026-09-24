package com.rafaelildefonso.listatarefas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafaelildefonso.listatarefas.viewmodel.ListaTarefasViewModel
import com.rafaelildefonso.listatarefas.viewmodel.SyncStatus

@Composable
fun ListaTarefasScreen(viewModel: ListaTarefasViewModel = viewModel()) {
    val tarefas by viewModel.tarefas.collectAsState()
    val syncStatus by viewModel.syncStatus.collectAsState()

    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var concluido by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Minhas Tarefas",
                style = MaterialTheme.typography.headlineSmall
            )
            Button(onClick = { viewModel.sincronizar() }) {
                Text("Sincronizar")
            }
        }

        when (syncStatus) {
            is SyncStatus.Loading -> {
                Spacer(Modifier.height(8.dp))
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            is SyncStatus.Error -> {
                Spacer(Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Falha ao sincronizar: ${(syncStatus as SyncStatus.Error).message}",
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(Modifier.height(4.dp))
                        OutlinedButton(onClick = { viewModel.limparErro() }) {
                            Text("Dispensar")
                        }
                    }
                }
            }

            is SyncStatus.Idle -> {}
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text("Descrição") },
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Checkbox(checked = concluido, onCheckedChange = { concluido = it })
            Text("Concluída")
        }

        Button(
            onClick = {
                viewModel.salvarTarefa(titulo, descricao, concluido)
                titulo = ""
                descricao = ""
                concluido = false
            },
            enabled = titulo.isNotBlank(),
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Text("Salvar Tarefa")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (tarefas.isEmpty()) {
            Text(
                text = "Nenhuma tarefa. Toque em Sincronizar para buscar na API.",
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(tarefas, key = { it.id }) { tarefa ->
                    ListItemTarefa(
                        titulo = tarefa.titulo,
                        descricao = tarefa.descricao,
                        concluido = tarefa.concluido,
                        remota = tarefa.remoteId != null
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
private fun ListItemTarefa(
    titulo: String,
    descricao: String,
    concluido: Boolean,
    remota: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = concluido, onCheckedChange = null)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.bodyLarge
            )
            val apoio = buildList {
                if (descricao.isNotBlank()) add(descricao)
                if (remota) add("JSONPlaceholder")
                if (concluido) add("concluída")
            }.joinToString(" · ")
            if (apoio.isNotBlank()) {
                Text(
                    text = apoio,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
