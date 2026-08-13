package com.example.simulado3a43_biblioteca

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
import com.example.simulado3a43_biblioteca.ui.theme.Simulado3A43_BibliotecaTheme

enum class TelaBiblioteca {
    MENU, CALCULO_MULTA, REGISTRO_LIVROS
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var telaAtual by remember { mutableStateOf(TelaBiblioteca.MENU) }

            when (telaAtual) {
                TelaBiblioteca.MENU -> TelaMenu(onNavegar = { destino -> telaAtual = destino })
                TelaBiblioteca.CALCULO_MULTA -> TelaMulta(onVoltar = { telaAtual = TelaBiblioteca.MENU })
                TelaBiblioteca.REGISTRO_LIVROS -> TelaLivros(onVoltar = { telaAtual = TelaBiblioteca.MENU })
            }
        }
    }
}


@Composable
fun TelaMenu(onNavegar: (TelaBiblioteca) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC)) // Fundo cinza azulado claro
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Simulado Android",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E3A8A)
        )
        Spacer(modifier = Modifier.height(32.dp))
// Botão para o App 1
        Button(
            onClick = { onNavegar(TelaBiblioteca.CALCULO_MULTA) },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("1. Calculo multa")
        }
        Spacer(modifier = Modifier.height(12.dp))
// Botão para o App 2
        Button(
            onClick = { onNavegar(TelaBiblioteca.REGISTRO_LIVROS) },
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
        ) {
            Text("2. Registro livros")
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}


@Composable
fun TelaMulta(onVoltar: () -> Unit) {
    var diasAtraso by remember { mutableStateOf("") }
    var taxaDia by remember { mutableStateOf("") }
    var multaTotal by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = diasAtraso,
            onValueChange = { texto -> diasAtraso = texto },
            placeholder = { Text("Dias de Atraso") }
        )
        OutlinedTextField(
            value = taxaDia,
            onValueChange = { texto -> taxaDia = texto },
            placeholder = { Text("Taxa por Dia") }
        )
        Button(onClick = {
            multaTotal = (diasAtraso.toIntOrNull()?:0) * (taxaDia.toIntOrNull()?:0)
        }
            ) {
            Text("Calcular")
        }
        Text(text = "Multa total: $multaTotal", fontSize = 36.sp, color = Color(0xFF3B82F6))
        Text(text = if (multaTotal >= 15) "Status: Usuário Bloqueado" else "Status: Regular", fontSize = 36.sp, color = Color(0xFF3B82F6))
        Spacer(modifier = Modifier.height(48.dp))
// Botão de navegação reversa
        OutlinedButton(onClick = onVoltar) {
            Text("Voltar ao Menu")
        }
    }
}

@Composable
fun TelaLivros(onVoltar: () -> Unit) {
    var quantidadeLivros by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Total de livros", fontSize = 20.sp, fontWeight =
                FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Pontos de Leitura: ${quantidadeLivros * 10}", fontSize = 36.sp, color = Color(0xFF3B82F6))
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { quantidadeLivros++ }) {
            Text("+1 Livro")
        }
        Spacer(modifier = Modifier.height(48.dp))
// Botão de navegação reversa
        OutlinedButton(onClick = onVoltar) {
            Text("Voltar ao Menu")
        }
    }
}