package com.example.jobwave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val dashboardBtn = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.dashboardNavBtn)
        dashboardBtn.setOnClickListener{
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }


        val signUpNav = findViewById<Button>(R.id.signUpNavBtn)
        signUpNav.setOnClickListener{
            val intent = Intent(this, RegisterUser::class.java)
            startActivity(intent)
        }
    }
}