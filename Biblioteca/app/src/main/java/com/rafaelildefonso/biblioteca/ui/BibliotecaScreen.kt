package com.rafaelildefonso.biblioteca.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafaelildefonso.biblioteca.viewmodel.BibliotecaUiState
import com.rafaelildefonso.biblioteca.viewmodel.BibliotecaViewModel

@Composable
fun BibliotecaScreen(viewModel: BibliotecaViewModel = viewModel()) {
    var titulo by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }
    var ano by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = autor, onValueChange = { autor = it }, label = { Text("Autor") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = ano, onValueChange = { ano = it }, label = { Text("Ano") }, modifier = Modifier.fillMaxWidth())

        Button(
            onClick = {
                val anoInt = ano.toIntOrNull() ?: return@Button
                viewModel.salvarLivro(titulo, autor, anoInt)
                titulo = ""; autor = ""; ano = ""
            },
            modifier = Modifier.padding(top = 12.dp)
        ) { Text("Salvar Livro") }

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = viewModel.uiState) {
            is BibliotecaUiState.Loading -> CircularProgressIndicator()
            is BibliotecaUiState.Error -> Text("Erro: ${state.message}")
            is BibliotecaUiState.Success -> LazyColumn {
                items(state.livros, key = { it.id }) { livro ->
                    ListItem(
                        headlineContent = { Text(livro.titulo) },
                        supportingContent = { Text("${livro.autor} · ${livro.ano}") }
                    )
                }
            }
        }
    }
}