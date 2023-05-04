package com.example.jobwave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Animations
        val topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)

        //Hooks
        val welcome = findViewById<ImageView>(R.id.welcomeLogo)

        welcome.startAnimation(topAnimation)

        //Splash screen
        val splashScreenTimeout = 2000


        Handler().postDelayed({
                val intent = Intent(this, Login::class.java)
                startActivity(intent)

        }, splashScreenTimeout.toLong())
    }

}