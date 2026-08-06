package com.example.catlogodeprodutos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import com.example.catlogodeprodutos.ui.theme.CatálogoDeProdutosTheme

// Rota simples sem dados
@Serializable
object RotaListagem
// TODO 1: O Jetpack Navigation exige uma anotação específica do Kotlin Serialization
// para que a rota abaixo possa transportar dados. Adicione a anotação em falta na linha abaixo:
@Serializable
data class RotaDetalhes(val produtoId: Int)

@Composable
fun TelaDeListagem(onIrParaDetalhes: (Int) -> Unit) {
    Column(
        modifier =
            Modifier.fillMaxSize().background(Color(0xFFF8FAFC)).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Catálogo de Eletrônicos", fontSize = 22.sp, fontWeight =
            FontWeight.Bold, color = Color(0xFF1E3A8A))
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { onIrParaDetalhes(101) }, modifier =
            Modifier.fillMaxWidth(0.8f)) {
            Text("Ver Notebook Gamer (ID: 101)")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { onIrParaDetalhes(202) }, modifier =
            Modifier.fillMaxWidth(0.8f), colors = ButtonDefaults.buttonColors(containerColor =
            Color(0xFF0284C7))) {
            Text("Ver Smartphone Premium (ID: 202)")
        }
    }
}

@Composable
fun ConfiguracaoNavegacao() {
    val controlador = rememberNavController()
    NavHost(navController = controlador, startDestination = RotaListagem) {
        composable<RotaListagem> {
            TelaDeListagem(
                onIrParaDetalhes = { idSelecionado ->
// TODO 2: Escreva a instrução para o controlador navegar para a
                    controlador.navigate(RotaDetalhes(idSelecionado))
// passando de forma Type-Safe o 'idSelecionado' capturado.

                }
            )
        }
        composable<RotaDetalhes> { entradaPilha ->
            val argumentos = entradaPilha.toRoute<RotaDetalhes>()
            TelaDeDetalhes(
                idDoProduto = argumentos.produtoId,
                onVoltarTela = {
// TODO 4: Escreva o comando do controlador para remover a tela atual do topo da pilha
// e retornar com sucesso à interface anterior.
                    controlador.popBackStack()

                }
            )
        }
    }
}

// Etapa 4: Configurando a MainActivity de Entrada
// Insira a chamada do grafo centralizado no escopo de execução da Activity:

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ConfiguracaoNavegacao()
                }
            }
        }
    }
}

@Composable
fun TelaDeDetalhes(idDoProduto: Int, onVoltarTela: () -> Unit) {
    var textoQuantidade by remember { mutableStateOf("") }
    var mensagemConfirmacao by remember { mutableStateOf("") }
    val nomeProduto = if (idDoProduto == 101) "Notebook Gamer" else "Smartphone Premium"
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Button(onClick = onVoltarTela) { Text("← Voltar") }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Nome: $nomeProduto", fontSize = 20.sp, fontWeight =
            FontWeight.Bold)
        Text(text = "Código (ID): $idDoProduto", color = Color(0xFF64748B))
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = textoQuantidade,
            onValueChange = { textoQuantidade = it },
            label = { Text("Digite a quantidade desejada") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
// TODO 5: Refatore as duas linhas abaixo aplicando toIntOrNull() e if/else

                val qtdConvertida = textoQuantidade.toIntOrNull()
                if (qtdConvertida != null) {
                    mensagemConfirmacao = "Pedido de $qtdConvertida unidade(s) enviado com sucesso!"
                }
                else {
                    mensagemConfirmacao = "Quantidade invalida!"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Confirmar Compra") }
        Spacer(modifier = Modifier.height(20.dp))
        if (mensagemConfirmacao.isNotEmpty()) { Text(mensagemConfirmacao, color =
            Color(0xFF059669)) }
    }
}