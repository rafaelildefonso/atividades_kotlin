package com.rafaelildefonso.listatarefas.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafaelildefonso.listatarefas.viewmodel.ListaTarefasUiState
import com.rafaelildefonso.listatarefas.viewmodel.ListaTarefasViewModel

@Composable
fun ListaTarefasScreen(viewModel: ListaTarefasViewModel = viewModel()) {
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = descricao, onValueChange = { descricao = it }, label = { Text("Descrição") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = status, onValueChange = { status = it }, label = { Text("Status") }, modifier = Modifier.fillMaxWidth())

        Button(
            onClick = {
                viewModel.salvarTarefa(titulo, descricao, status)
                titulo = ""; descricao = ""; status = ""
            },
            modifier = Modifier.padding(top = 12.dp)
        ) { Text("Salvar Tarefa") }

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = viewModel.uiState) {
            is ListaTarefasUiState.Loading -> CircularProgressIndicator()
            is ListaTarefasUiState.Error -> Text("Erro: ${state.message}")
            is ListaTarefasUiState.Success -> LazyColumn {
                items(state.tarefas, key = { it.id }) { tarefa ->
                    ListItem(
                        headlineContent = { Text(tarefa.titulo) },
                        supportingContent = { Text("${tarefa.descricao} · ${tarefa.status}") }
                    )
                }
            }
        }
    }
}