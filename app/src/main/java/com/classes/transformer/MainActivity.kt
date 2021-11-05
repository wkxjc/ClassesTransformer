package com.classes.transformer

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val btn by lazy { findViewById<Button>(R.id.btn) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn) {
            Toast.makeText(this, "Clicked: ${Date()}", Toast.LENGTH_SHORT).show()
            Log.d("~~~", "Click: ${Date()}")
        }
    }
}

