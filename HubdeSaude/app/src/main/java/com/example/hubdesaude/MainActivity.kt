package com.example.hubdesaude

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
import com.example.hubdesaude.ui.theme.HubDeSaudeTheme

enum class TelaSaude {
    MENU, CALCULAR_IMC, META_AGUA
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var telaAtual by remember { mutableStateOf(TelaSaude.MENU) }
            when (telaAtual) {
                TelaSaude.MENU ->
                    TelaMenu(onNavegar = { destino -> telaAtual = destino })
                TelaSaude.CALCULAR_IMC ->
                    TelaIMC(onVoltar = { telaAtual = TelaSaude.MENU })
                TelaSaude.META_AGUA ->
                    TelaAgua(onVoltar = { telaAtual = TelaSaude.MENU })
            }
        }
    }
}

@Composable
fun TelaMenu(onNavegar: (TelaSaude) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC)) // Fundo cinza azulado claro
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hub de Saúde",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E3A8A)
        )
        Spacer(modifier = Modifier.height(32.dp))
// Botão para o App 1
        Button(
            onClick = { onNavegar(TelaSaude.CALCULAR_IMC) },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("1. Calcular IMC")
        }
        Spacer(modifier = Modifier.height(12.dp))
// Botão para o App 2
        Button(
            onClick = { onNavegar(TelaSaude.META_AGUA) },
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
        ) {
            Text("2. Meta Água")
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}


@Composable fun TelaIMC(onVoltar: () -> Unit) {
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var IMC by remember { mutableStateOf(0.0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = peso,
            onValueChange = { texto -> peso = texto },
            placeholder = { Text("Peso") }
        )
        OutlinedTextField(
            value = altura,
            onValueChange = { texto -> altura = texto },
            placeholder = { Text("Altura") }
        )
        Button(onClick = {
            IMC = (peso.toDoubleOrNull()?:0.0) / ((altura.toDoubleOrNull()?:0.0) * (altura.toDoubleOrNull()?:0.0))
        }) {
            Text("Calcular")
        }
        Text(text = "IMC: $IMC", fontSize = 36.sp, color = Color(0xFF3B82F6))
        Spacer(modifier = Modifier.height(48.dp))
// Botão de navegação reversa
        OutlinedButton(onClick = onVoltar) {
            Text("Voltar ao Menu")
        }
    }
}

@Composable
fun TelaAgua(onVoltar: () -> Unit) {
    var coposDagua by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Contador de copos d'água", fontSize = 20.sp, fontWeight =
                FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Copos d'água consumidos: ${coposDagua * 250} ml", fontSize = 36.sp, color = Color(0xFF3B82F6))
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { coposDagua++ }) {
            Text("Beber 1 Copo",)
        }
        Spacer(modifier = Modifier.height(48.dp))
// Botão de navegação reversa
        OutlinedButton(onClick = onVoltar) {
            Text("Voltar ao Menu")
        }
    }
}