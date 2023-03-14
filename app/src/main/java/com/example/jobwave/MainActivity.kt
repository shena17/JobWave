package com.example.jobwave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView

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
        val splashScreenTimeout = 4000
        val homeIntent = Intent(this@MainActivity, Login::class.java)

        Handler().postDelayed({
            startActivity(homeIntent)
            finish()
        }, splashScreenTimeout.toLong())
    }
}