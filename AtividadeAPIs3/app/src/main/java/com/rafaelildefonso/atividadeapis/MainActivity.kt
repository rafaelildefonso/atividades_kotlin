package com.rafaelildefonso.atividadeapis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.rafaelildefonso.atividadeapis.ui.theme.AtividadeAPIsTheme
import com.rafaelildefonso.atividadeapis.ui.theme.CepScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AtividadeAPIsTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CepScreen()
                }
            }
        }
    }
}
