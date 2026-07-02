package com.example.trilhafacilcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.trilhafacilcompose.ui.theme.TrilhaFacilComposeTheme
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Aqui chamamos a nossa função visual principal
            TrilhaFacilApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrilhaFacilApp() {
// VARIÁVEIS DE ESTADO (Reativas)
    var nomeTrilha by remember { mutableStateOf("") }
    var tituloApp by remember { mutableStateOf("Bem-vindo ao Trilha Fácil") }
// Captura do contexto (necessário para mostrar o Toast)
    val context = LocalContext.current;


// Continuando dentro da função TrilhaFacilApp()...
    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa a tela inteira
            .background(Color(0xFFF0FDF4)) // Fundo verde claro
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
// 1. ÍCONE
        Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_compass),
            contentDescription = "Ícone de Bússola",
            tint = Color(0xFF166534),
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(16.dp)) // Substitui a margem!
// 2. TEXTO DO TÍTULO
        Text(
            text = tituloApp, // Lê a variável reativa
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF166534)
        )
        Spacer(modifier = Modifier.height(32.dp))
// 3. CAMPO DE DIGITAÇÃO
        OutlinedTextField(
            value = nomeTrilha,
            onValueChange = { novoTexto -> nomeTrilha = novoTexto }, // Atualiza enquanto digita
                    label = { Text("Nome da sua próxima trilha...") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        Spacer(modifier = Modifier.height(24.dp))
// 4. BOTÃO COM A LÓGICA
        Button(
            onClick = {
                if (nomeTrilha.isBlank()) {
                    Toast.makeText(context, "Por favor, digite uma trilha!",
                        Toast.LENGTH_SHORT).show()
                } else {
// Ao mudar o tituloApp, a tela se atualiza sozinha!
                    tituloApp = "Próxima aventura: $nomeTrilha!"
                    Toast.makeText(context, "Trilha '$nomeTrilha' planejada!",
                        Toast.LENGTH_LONG).show()
                    nomeTrilha = "" // Limpa o campo
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF166534))
        ) {
            Text("Planejar Trilha", color = Color.White)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TrilhaFacilComposeTheme {
        Greeting("Android")
    }
}