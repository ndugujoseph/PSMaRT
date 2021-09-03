package com.example.psmart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val selectClass = findViewById<Button>(R.id.learner)

        selectClass.setOnClickListener {
            val intent = Intent(this, SelectClassSecondaryActivity::class.java);
            startActivity(intent)
        }

    }
}