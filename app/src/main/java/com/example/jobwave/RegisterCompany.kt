package com.example.jobwave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RegisterCompany : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_company)

        val regCompany = findViewById<Button>(R.id.regUserNavBtn)

        val loginNav = findViewById<Button>(R.id.loginNavBtn)
        loginNav.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        regCompany.setOnClickListener{
            val intent = Intent(this, RegisterUser::class.java)
            startActivity(intent)
        }
    }
}