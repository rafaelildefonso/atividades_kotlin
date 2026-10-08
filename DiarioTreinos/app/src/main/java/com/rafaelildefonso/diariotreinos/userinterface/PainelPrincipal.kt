package com.rafaelildefonso.diariotreinos.userinterface

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.rafaelildefonso.diariotreinos.model.Rota
import com.rafaelildefonso.diariotreinos.viewmodel.DiarioViewModel

enum class AbaPrincipal { DIARIOS, ESTATISTICAS, PERFIL }

@Composable
fun PainelPrincipal(
    navController: NavHostController,
    viewModel: DiarioViewModel,
    abaAtual: AbaPrincipal
) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = abaAtual == AbaPrincipal.DIARIOS,
                    onClick = { navegarParaAba(navController, Rota.ListaDiarios) },
                    icon = { Icon(Icons.Filled.CheckCircle, contentDescription = "Diarios") },
                    label = { Text("Diarios") }
                )
                NavigationBarItem(
                    selected = abaAtual == AbaPrincipal.ESTATISTICAS,
                    onClick = { navegarParaAba(navController, Rota.Estatisticas) },
                    icon = { Icon(Icons.Filled.BarChart, contentDescription = "Estatísticas") },
                    label = { Text("Estatísticas") }
                )
                NavigationBarItem(
                    selected = abaAtual == AbaPrincipal.PERFIL,
                    onClick = { navegarParaAba(navController, Rota.Perfil) },
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
                    label = { Text("Perfil") }
                )
            }
        }
    ) { paddingValues ->
        when (abaAtual) {
            AbaPrincipal.DIARIOS -> ListaDiariosScreen(
                modifier = Modifier.padding(paddingValues),
                viewModel = viewModel,
                aoAbrirDetalhe = { id -> navController.navigate(Rota.DetalheDiario(id)) }
            )
            AbaPrincipal.ESTATISTICAS -> EstatisticasScreen(
                modifier = Modifier.padding(paddingValues),
                viewModel = viewModel
            )
            AbaPrincipal.PERFIL -> PerfilScreen(
                modifier = Modifier.padding(paddingValues),
                viewModel = viewModel,
                aoSair = {
                    viewModel.fazerLogout()
                    navController.navigate(Rota.Login) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}

private fun navegarParaAba(navController: NavHostController, destino: Rota) {
    navController.navigate(destino) {
        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}