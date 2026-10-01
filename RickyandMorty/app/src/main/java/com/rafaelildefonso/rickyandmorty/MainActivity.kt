package com.rafaelildefonso.rickyandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafaelildefonso.rickyandmorty.presentation.CharacterScreen
import com.rafaelildefonso.rickyandmorty.presentation.CharacterViewModel
import com.rafaelildefonso.rickyandmorty.ui.theme.RickyandMortyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickyandMortyTheme {
                val application = application as MultiverseRadarApp
                val viewModel: CharacterViewModel = viewModel(
                    factory = CharacterViewModel.provideFactory(
                        application.container.characterRepository
                    )
                )
                CharacterScreen(
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}