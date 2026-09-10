package com.rafaelildefonso.atividadeapis.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafaelildefonso.atividadeapis.viewmodel.CepUiState
import com.rafaelildefonso.atividadeapis.viewmodel.CepViewModel

private val CorTexto = Color(0xFF3A444D)
private val CorBorda = Color(0xFF333333)
private val CorBotao = Color(0xFF444444)

@Composable
fun CepScreen(viewModel: CepViewModel = viewModel()) {
    var cepBusca by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Busca de Endereco por CEP",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = CorTexto
        )

        OutlinedTextField(
            value = cepBusca,
            onValueChange = { cepBusca = it },
            label = { Text("Digite o CEP (Ex: 01001-000)") },
            singleLine = true,
            textStyle = TextStyle(fontWeight = FontWeight.SemiBold, color = CorTexto),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(onSearch = { viewModel.buscarCep(cepBusca) }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = CorBorda,
                unfocusedBorderColor = CorBorda,
                focusedLabelColor = CorTexto,
                unfocusedLabelColor = CorTexto,
                cursorColor = CorTexto
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.buscarCep(cepBusca) },
            colors = ButtonDefaults.buttonColors(
                containerColor = CorBotao, contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Buscar", fontWeight = FontWeight.SemiBold)
        }

        when (val state = viewModel.cepUiState) {
            is CepUiState.Idle -> {}
            is CepUiState.Loading ->
                Text("Buscando...", color = CorTexto)
            is CepUiState.NotFound ->
                Text("CEP nao encontrado!", color = Color.Red, fontWeight = FontWeight.SemiBold)
            is CepUiState.Success -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val e = state.endereco
                        Text("CEP: ${e.cep}", fontWeight = FontWeight.Bold, color = CorTexto)
                        e.logradouro?.let { Text("Logradouro: $it", color = CorTexto) }
                        e.complemento?.takeIf { it.isNotBlank() }?.let {
                            Text("Complemento: $it", color = CorTexto)
                        }
                        e.bairro?.let { Text("Bairro: $it", color = CorTexto) }
                        e.localidade?.let { Text("Cidade: $it", color = CorTexto) }
                        e.uf?.let { Text("UF: $it", color = CorTexto) }
                        e.ddd?.let { Text("DDD: $it", color = CorTexto) }
                    }
                }
            }
        }
    }
}
