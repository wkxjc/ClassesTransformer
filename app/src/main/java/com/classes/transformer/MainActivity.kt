package com.classes.transformer

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    private val btn by lazy { findViewById<Button>(R.id.btn) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener { v ->
            if (v?.id == R.id.btn) {
                Toast.makeText(v.context, "Clicked: ${Date()}", Toast.LENGTH_SHORT).show()
                Log.d("~~~", "Click: ${Date()}")
            }
        }
    }
}

