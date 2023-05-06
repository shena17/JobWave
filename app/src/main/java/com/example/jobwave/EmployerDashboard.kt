package com.example.jobwave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toolbar
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth


class EmployerDashboard : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var btnPostJob:Button

    private lateinit var toolbar:Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employer_dashboard)

        btnPostJob = findViewById(R.id.btn_PostJob)

        btnPostJob.setOnClickListener(){
            startActivity(Intent(applicationContext, PostJobActivity::class.java))
        }

        val signOutBtn = findViewById<AppCompatButton>(R.id.signOut)

        signOutBtn.setOnClickListener{
                //Init and attach
                auth = FirebaseAuth.getInstance();
                //Call signOut()
                auth.signOut();
                startActivity(Intent(this, Login::class.java))
                finish()
        }

    }

    override fun onBackPressed() {
        val setIntent = Intent(Intent.ACTION_MAIN)
        setIntent.addCategory(Intent.CATEGORY_HOME)
        setIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(setIntent)
    }


}