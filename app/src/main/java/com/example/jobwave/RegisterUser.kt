package com.example.jobwave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RegisterUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        val loginNav = findViewById<Button>(R.id.loginNavBtn)
        loginNav.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val regCompany = findViewById<Button>(R.id.regCompNavBtn)

        regCompany.setOnClickListener{
            val intent = Intent(this, RegisterCompany::class.java)
            startActivity(intent)
        }
    }
}