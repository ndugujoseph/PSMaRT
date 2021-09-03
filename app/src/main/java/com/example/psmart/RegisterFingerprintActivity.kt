package com.example.psmart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast


class RegisterFingerprintActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_fingerprint)
    }

    private fun notifyUser(message: String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}