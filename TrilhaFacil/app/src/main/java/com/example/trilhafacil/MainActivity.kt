package com.example.trilhafacil

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Conectando os componentes usando os IDs EXATOS do seu XML
        val txtTitulo = findViewById<TextView>(R.id.textView)
        val editNomeTrilha = findViewById<EditText>(R.id.editTextText)
        val btnPlanejar = findViewById<Button>(R.id.button)

        // Lógica do clique do botão
        btnPlanejar.setOnClickListener {
            val nomeTrilha = editNomeTrilha.text.toString().trim()

            if (nomeTrilha.isEmpty()) {
                Toast.makeText(this, "Por favor, digite o nome de uma trilha!", Toast.LENGTH_SHORT).show()
            } else {
                // Altera o texto do seu TextView
                txtTitulo.text = "Próxima aventura: $nomeTrilha!"

                // Mostra o Toast de sucesso
                Toast.makeText(this, "Trilha '$nomeTrilha' planejada com sucesso! 🥾", Toast.LENGTH_LONG).show()

                // Limpa o campo de texto
                editNomeTrilha.text.clear()
            }
        }
    }
}