package com.example.meuappandroid

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

        val txtMensagem = findViewById<TextView>(R.id.txtMensagem);
        val editQuantidade = findViewById<EditText>(R.id.editQuantidade);
        val btnSacar  = findViewById<Button>(R.id.btnSacar);
        val btnPix = findViewById<Button>(R.id.btnPix);

        btnPix.setOnClickListener {
            val quantidade = editQuantidade.text.toString().trim().toDouble()

            if (quantidade <= 0) {
                Toast.makeText(this, "Por favor, digite um valor maior que zero!", Toast.LENGTH_SHORT).show()
            } else {
                txtMensagem.text = "Pix enviado no valor de: R\$$quantidade!"

                Toast.makeText(this, "Pix enviado!", Toast.LENGTH_LONG).show()

                editQuantidade.text.clear()
            }
        }

        btnSacar.setOnClickListener {
            val quantidade = editQuantidade.text.toString().trim().toDouble()

            if (editQuantidade.text.toString().isEmpty() || quantidade <= 0) {
                Toast.makeText(this, "Por favor, digite um valor maior que zero!", Toast.LENGTH_SHORT).show()
            } else {
                txtMensagem.text = "Saque feito no valor de: R\$$quantidade!"

                Toast.makeText(this, "Saque realizado!", Toast.LENGTH_LONG).show()

                editQuantidade.text.clear()
            }
        }
    }
}