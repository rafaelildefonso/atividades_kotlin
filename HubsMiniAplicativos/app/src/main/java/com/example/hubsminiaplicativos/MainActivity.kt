package com.example.hubsminiaplicativos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hubsminiaplicativos.ui.theme.HubsMiniAplicativosTheme

enum class Tela {
    MENU, CONTADOR, CONVERSOR, DADO
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
// Inicializa a variável controladora de tela
            var telaAtual by remember { mutableStateOf(Tela.MENU) }
// Analisa o estado atual e desenha a tela correspondente
            when (telaAtual) {
                Tela.MENU -> TelaMenu(onNavegar = { destino -> telaAtual = destino })
                Tela.CONTADOR -> TelaContador(onVoltar = { telaAtual = Tela.MENU })
                Tela.CONVERSOR -> TelaConversor(onVoltar = { telaAtual = Tela.MENU })
                Tela.DADO -> TelaDado(onVoltar = { telaAtual = Tela.MENU })
            }
        }
    }
}

@Composable
fun TelaMenu(onNavegar: (Tela) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC)) // Fundo cinza azulado claro
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hub de Exercícios Compose",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E3A8A)
        )
        Spacer(modifier = Modifier.height(32.dp))
// Botão para o App 1
        Button(
            onClick = { onNavegar(Tela.CONTADOR) },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("1. Contador de Cliques")
        }
        Spacer(modifier = Modifier.height(12.dp))
// Botão para o App 2
        Button(
            onClick = { onNavegar(Tela.CONVERSOR) },
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
        ) {
            Text("2. Conversor de Temperatura")
        }
        Spacer(modifier = Modifier.height(12.dp))
// Botão para o App 3
        Button(
            onClick = { onNavegar(Tela.DADO) },
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669))
        ) {
            Text("3. Dado Virtual")
        }
    }
}

@Composable
fun TelaContador(onVoltar: () -> Unit) {
    var cliques by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Contador de Cliques", fontSize = 20.sp, fontWeight =
                FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Cliques: $cliques", fontSize = 36.sp, color = Color(0xFF3B82F6))
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { cliques++ }) {
            Text("Incrementar (+1)")
        }
        Spacer(modifier = Modifier.height(48.dp))
// Botão de navegação reversa
        OutlinedButton(onClick = onVoltar) {
            Text("Voltar ao Menu")
        }
    }
}

@Composable fun TelaConversor(onVoltar: () -> Unit) {
    var temperatura by remember { mutableStateOf(0.0) }
    var textoInput by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = textoInput,
            onValueChange = { texto -> textoInput = texto }
        )
        Button(onClick = {
            temperatura = textoInput.toDoubleOrNull() ?: 0.0
            temperatura = (temperatura*1.8)+32
        }) {
            Text("Calcular")
        }
        Text(text = "Temperatura em Fahrenheit: $temperatura", fontSize = 36.sp, color = Color(0xFF3B82F6))
        Spacer(modifier = Modifier.height(48.dp))
// Botão de navegação reversa
        OutlinedButton(onClick = onVoltar) {
            Text("Voltar ao Menu")
        }
    }
}
@Composable fun TelaDado(onVoltar: () -> Unit) {
    var numero by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Rolagem de dado 🎲", fontSize = 36.sp, color = Color(0xFF3B82F6))
        Text(text = "$numero", fontSize = 72.sp, color = Color(0xFF3B82F6))
        Text(text = if (numero == 6) "Parabens!! Você tirou um 6" else "", fontSize = 32.sp, color = Color(0xFF3B82F6))
        Button(onClick = {
            numero = (1..6).random()
        }) {
            Text("Rolar dado")
        }

        Spacer(modifier = Modifier.height(48.dp))
// Botão de navegação reversa
        OutlinedButton(onClick = onVoltar) {
            Text("Voltar ao Menu")
        }
    }
}