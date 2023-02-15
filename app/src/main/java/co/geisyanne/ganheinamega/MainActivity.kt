package co.geisyanne.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        sharedPreferences = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = sharedPreferences.getString("result", null)
        result?.let {
            txtResult.text = "Última aposta: \n $it"
        }

        btnGenerate.setOnClickListener {
            val text = editText.text.toString()
            numberGenerate(text, txtResult)
        }
    }

    private fun numberGenerate(text: String, txtResult: TextView) {

        val qtd = text.toIntOrNull()

        if (qtd == null || (qtd !in 6..15)) {
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        val numbers = mutableSetOf<Int>()
        val random = Random

        while (true) {
            val number = random.nextInt(60)
            numbers.add(number + 1)
            if (numbers.size == qtd) break
        }

        val sortNum = numbers.toList().sorted()
        txtResult.text = sortNum.joinToString(" - ")

        val editor = sharedPreferences.edit()
        editor.putString("result", txtResult.text.toString())
        editor.apply()
    }
}