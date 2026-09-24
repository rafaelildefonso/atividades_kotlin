package com.rafaelildefonso.listatarefas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.rafaelildefonso.listatarefas.ui.ListaTarefasScreen
import com.rafaelildefonso.listatarefas.ui.theme.ListaTarefasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListaTarefasTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ListaTarefasScreen()
                }
            }
        }
    }
}
