package com.example.dashboardfinanceiro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dashboardfinanceiro.ui.theme.DashboardFinanceiroTheme
import kotlin.math.pow

enum class TelaFinanceira {
    RESUMO, JUROS_COMPOSTOS, CONVERSOR_MOEDA
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

// Gerenciamento de Memória Compose:
// remember retém o estado através de recomposições.
            var telaAtual by remember { mutableStateOf(TelaFinanceira.RESUMO) }
            when (telaAtual) {

                TelaFinanceira.RESUMO ->
                    TelaResumo(onNavegar = { destino -> telaAtual = destino })
                TelaFinanceira.JUROS_COMPOSTOS ->
                    TelaJuros(onVoltar = { telaAtual = TelaFinanceira.RESUMO })
                TelaFinanceira.CONVERSOR_MOEDA ->
                    TelaConversor(onVoltar = { telaAtual = TelaFinanceira.RESUMO })
            }
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


@Composable
fun TelaResumo(onNavegar: (TelaFinanceira) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Dashboard Financeiro", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { onNavegar(TelaFinanceira.JUROS_COMPOSTOS) }) {
            Text("Calculadora de Juros")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onNavegar(TelaFinanceira.CONVERSOR_MOEDA) }) {
            Text("Conversor de Moedas")
        }
    }
}

@Composable
fun TelaJuros(onVoltar: (TelaFinanceira) -> Unit){
    var capital by remember { mutableStateOf("") }
    var taxa by remember { mutableStateOf("") }
    var tempo by remember { mutableStateOf("") }
    var montante by remember { mutableStateOf(0.0) }

    Column {

        OutlinedTextField(
            value = capital,
            onValueChange = { novoTexto ->
                // Permite apenas números e um ponto decimal
                capital = novoTexto
            },
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = taxa,
            onValueChange = { novoTexto ->
                // Permite apenas números e um ponto decimal
                taxa = novoTexto
            },
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = tempo,
            onValueChange = { novoTexto ->
                // Permite apenas números e um ponto decimal
                tempo = novoTexto
            },
        )
        Button(onClick = {
            var capital1 = capital.toDoubleOrNull() ?: 0.0
            var taxa1 = taxa.toDoubleOrNull() ?: 0.0
            var Tempo1 = capital.toDoubleOrNull() ?: 0.0
            montante = capital1 *  (1+taxa1).pow(Tempo1)}) {
            Text("Converter")

        }
        Text("Valor: $montante")


    }

}
@Composable
fun TelaConversor(onVoltar: (TelaFinanceira) -> Unit){
    var reais by remember { mutableStateOf("") }
    var dolar by remember { mutableStateOf(0.0) }
    var Euro by remember { mutableStateOf(0.0) }

    Column {

        OutlinedTextField(
            value = reais,
            onValueChange = { novoTexto ->
                // Permite apenas números e um ponto decimal
                reais = novoTexto
            },
        )
        Spacer(modifier = Modifier.height(16.dp))


        Button(onClick = {
            var reaisD = reais.toDoubleOrNull() ?: 0.0

            dolar = reaisD * 5
            Euro = reaisD * 5.5}) {
            Text("Converter")

        }
        Text("Dolar: $dolar Euro: $Euro")


    }
}