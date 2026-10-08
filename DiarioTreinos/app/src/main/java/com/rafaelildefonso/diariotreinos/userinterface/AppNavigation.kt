package com.rafaelildefonso.diariotreinos.userinterface

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.rafaelildefonso.diariotreinos.model.Rota
import com.rafaelildefonso.diariotreinos.viewmodel.DiarioViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: DiarioViewModel = viewModel()

    NavHost(navController = navController, startDestination = Rota.Splash) {

        composable<Rota.Splash> {
            SplashScreen(
                estaLogado = viewModel.estaLogado,
                aoTerminarVerificacao = { logado ->
                    val destino = if (logado) Rota.ListaDiarios else Rota.Login
                    navController.navigate(destino) {
                        popUpTo(Rota.Splash) { inclusive = true }
                    }
                }
            )
        }

        composable<Rota.Login> {
            LoginScreen(
                aoLogar = {
                    viewModel.fazerLogin()
                    navController.navigate(Rota.ListaDiarios) {
                        popUpTo(Rota.Login) { inclusive = true }
                    }
                }
            )
        }

        composable<Rota.ListaDiarios> {
            PainelPrincipal(navController, viewModel, AbaPrincipal.DIARIOS)
        }

        composable<Rota.Estatisticas> {
            PainelPrincipal(navController, viewModel, AbaPrincipal.ESTATISTICAS)
        }

        composable<Rota.Perfil> {
            PainelPrincipal(navController, viewModel, AbaPrincipal.PERFIL)
        }

        composable<Rota.DetalheDiario> { backStackEntry ->
            val rota: Rota.DetalheDiario = backStackEntry.toRoute()
            DetalheDiarioScreen(
                diario = viewModel.buscarDiario(rota.diarioId),
                aoVoltar = { navController.popBackStack() },
                aoAlternarConclusao = { viewModel.alternarConclusao(rota.diarioId) }
            )
        }
    }
}