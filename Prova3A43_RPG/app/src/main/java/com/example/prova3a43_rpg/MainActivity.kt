package com.example.prova3a43_rpg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prova3a43_rpg.ui.theme.Prova3A43_RPGTheme

enum class TelaRPG {
    MENU, DANO_POR_SEGUNDO, EXPERIENCIA
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var telaAtual by remember { mutableStateOf(TelaRPG.MENU) }
            when (telaAtual) {
                TelaRPG.MENU -> TelaMenu(onNavegar = { destino -> telaAtual = destino })
                TelaRPG.DANO_POR_SEGUNDO -> TelaDano(onVoltar = { telaAtual = TelaRPG.MENU })
                TelaRPG.EXPERIENCIA -> TelaExperiencia(onVoltar = { telaAtual = TelaRPG.MENU })
            }
        }
    }
}

@Composable
fun TelaMenu(onNavegar: (TelaRPG) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .padding(24.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hub RPG",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E3A8A),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
// Botão para o App 1
        Button(
            onClick = { onNavegar(TelaRPG.DANO_POR_SEGUNDO) },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Calculadora de Dano (DPS)")
        }
        Spacer(modifier = Modifier.height(12.dp))
// Botão para o App 2
        Button(
            onClick = { onNavegar(TelaRPG.EXPERIENCIA) },
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
        ) {
            Text("Calculadora de Experiência")
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun TelaDano(onVoltar: () -> Unit) {
    var danoPorSegundo by remember { mutableStateOf(0.0) }
    var danoAtaque by remember { mutableStateOf("") }
    var quantidadePorSegundo by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = danoAtaque,
            onValueChange = { texto -> danoAtaque = texto },
            placeholder = { Text("Dano por Ataque") }
        )
        Spacer(modifier = Modifier.height(height = 20.dp))
        OutlinedTextField(
            value = quantidadePorSegundo,
            onValueChange = { texto -> quantidadePorSegundo = texto },
            placeholder = { Text("Quantidade de Ataques por Segundo") }
        )
        Button(onClick = {
            danoPorSegundo = (danoAtaque.toDoubleOrNull() ?: 0.0) * (quantidadePorSegundo.toDoubleOrNull() ?: 0.0)
        }) {
            Text("Calcular")
        }
        Text(text = "Dano por segundo: $danoPorSegundo", fontSize = 36.sp, color = Color(0xFF3B82F6),textAlign = TextAlign.Center)
        Text(text = if (danoPorSegundo >= 100.0) "Status: Build Forte" else "Status: Build Fraca", fontSize = 36.sp, color = Color(0xFF3B82F6),textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(48.dp))
// Botão de navegação reversa
        OutlinedButton(onClick = onVoltar) {
            Text("Voltar ao Menu")
        }
    }
}

@Composable
fun TelaExperiencia(onVoltar: () -> Unit) {
    var totalAcumuladoXP by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Total de livros", fontSize = 20.sp, fontWeight =
                FontWeight.Bold,textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Total de XP: ${totalAcumuladoXP}", fontSize = 36.sp, color = Color(0xFF3B82F6),textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(height = 20.dp))
        Text(text = "Nível atual do personagem: ${totalAcumuladoXP/100}", fontSize = 36.sp, color = Color(0xFF3B82F6),textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { totalAcumuladoXP+=50 }) {
            Text("+50 XP por Missão")
        }
        Spacer(modifier = Modifier.height(48.dp))
// Botão de navegação reversa
        OutlinedButton(onClick = onVoltar) {
            Text("Voltar ao Menu")
        }
    }
}