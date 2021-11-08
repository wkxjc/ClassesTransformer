package com.classes.transformer

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val btnThis by lazy { findViewById<Button>(R.id.btnThis) }
    private val btnObject by lazy { findViewById<Button>(R.id.btnObject) }
    private val btnLambda by lazy { findViewById<Button>(R.id.btnLambda) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnThis.setOnClickListener(this)
        btnObject.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Log.d("~~~", "Click btnObject: ${Date()}")
            }
        })
        btnLambda.setOnClickListener {
            Log.d("~~~", "Click btnLambda: ${Date()}")
        }
    }

    override fun onClick(v: View?) {
        Log.d("~~~", "Click: ${Date()}")
    }

    /**
     * Unsupported yet
     * Because we can only filter such methods with only one view parameter
     * this will lead to many methods that do not need to prevent repeated clicks being added.
     */
    fun clickFromDataBinding(v: View?) {
        Log.d("~~~", "Click btnDataBinding: ${Date()}")
    }

}

