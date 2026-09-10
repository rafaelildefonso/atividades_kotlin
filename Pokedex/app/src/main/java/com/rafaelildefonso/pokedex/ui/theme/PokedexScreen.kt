package com.rafaelildefonso.pokedex.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.rafaelildefonso.pokedex.R
import com.rafaelildefonso.pokedex.viewmodel.PokedexUiState
import com.rafaelildefonso.pokedex.viewmodel.PokedexViewModel

// Cores retiradas do projeto web original
private val CorTexto = Color(0xFF3A444D)
private val CorBorda = Color(0xFF333333)
private val CorBotao = Color(0xFF444444)

// Coordenadas medidas na imagem pokedex.png (em % da largura/altura)
private const val TELA_ESQUERDA = 0.160f
private const val TELA_TOPO = 0.270f
private const val TELA_LARGURA = 0.588f
private const val TELA_ALTURA = 0.261f
private const val MOLDURA_BASE = 0.628f
private const val MOLDURA_ESQUERDA = 0.080f
private const val MOLDURA_LARGURA = 0.732f

@Composable
fun PokedexScreen(viewModel: PokedexViewModel = viewModel()) {
    var termoBusca by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxHeight().aspectRatio(425f / 637f)
        ) {
            val largura = maxWidth
            val altura = maxHeight

            Image(
                painter = painterResource(id = R.drawable.pokedex),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            // O Pokémon, posicionado sobre a grama
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = largura * TELA_ESQUERDA, y = altura * TELA_TOPO)
                    .width(largura * TELA_LARGURA)
                    .height(altura * TELA_ALTURA),
                contentAlignment = Alignment.Center
            ) {
                when (val state = viewModel.uiState) {
                    is PokedexUiState.Loading ->
                        Text("Loading...", fontWeight = FontWeight.SemiBold, color = CorTexto)
                    is PokedexUiState.NotFound ->
                        Text("Not found!", fontWeight = FontWeight.SemiBold, color = CorTexto)
                    is PokedexUiState.Success ->
                        AsyncImage(
                            model = state.pokemon.sprites.other?.showdown?.frontDefault
                                ?: state.pokemon.sprites.frontDefault,
                            contentDescription = state.pokemon.name,
                            modifier = Modifier.size(largura * TELA_LARGURA * 0.55f)
                        )
                }
            }

            // Número e nome, na moldura logo abaixo da tela
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = largura * MOLDURA_ESQUERDA, y = altura * (TELA_TOPO + TELA_ALTURA))
                    .width(largura * MOLDURA_LARGURA)
                    .height(altura * (MOLDURA_BASE - (TELA_TOPO + TELA_ALTURA))),
                contentAlignment = Alignment.Center
            ) {
                val state = viewModel.uiState
                if (state is PokedexUiState.Success) {
                    Text(
                        "${state.pokemon.id} - ${state.pokemon.name.replaceFirstChar { it.uppercase() }}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = CorTexto
                    )
                }
            }

            // Campo de busca
            OutlinedTextField(
                value = termoBusca,
                onValueChange = { termoBusca = it },
                label = { Text("Name or Number") },
                singleLine = true,
                textStyle = TextStyle(fontWeight = FontWeight.SemiBold, color = CorTexto),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { viewModel.buscar(termoBusca) }),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = CorBorda,
                    unfocusedBorderColor = CorBorda,
                    focusedLabelColor = CorTexto,
                    unfocusedLabelColor = CorTexto,
                    cursorColor = CorTexto
                ),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = largura * MOLDURA_ESQUERDA, y = altura * 0.68f)
                    .width(largura * MOLDURA_LARGURA)
            )

            // Botões Prev / Next
            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = largura * MOLDURA_ESQUERDA, y = altura * 0.80f)
                    .width(largura * MOLDURA_LARGURA),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { viewModel.anterior() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CorBotao, contentColor = Color.White
                    )
                ) { Text("Prev <", fontWeight = FontWeight.SemiBold) }

                Button(
                    onClick = { viewModel.proximo() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CorBotao, contentColor = Color.White
                    )
                ) { Text("Next >", fontWeight = FontWeight.SemiBold) }
            }
        }
    }
}