package com.example.jobwave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.helper.widget.MotionEffect.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val dashboardBtn = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.dashboardNavBtn)
        dashboardBtn.setOnClickListener{
           signIn()
        }

        val signUpNav = findViewById<Button>(R.id.signUpNavBtn)
        signUpNav.setOnClickListener{
            val intent = Intent(this, RegisterUser::class.java)
            startActivity(intent)
        }
    }

    private fun signIn(){
        val emailInput = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.emailLogin)
        val passwordInput = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.passwordLogin)

        if(emailInput.editText?.text?.isEmpty() == true || passwordInput.editText?.text?.isEmpty() == true){
            Toast.makeText(this, "Fill all the fields!", Toast.LENGTH_SHORT).show()
            return
        }

        val email = emailInput.editText?.text.toString()
        val password = passwordInput.editText?.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this, Dashboard::class.java)
                    startActivity(intent)

                    Toast.makeText(baseContext, "Logged In", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
//                    Toast.makeText(
//                        baseContext,
//                        "Authentication failed.",
//                        Toast.LENGTH_SHORT,
//                    ).show()
                }
            }.addOnFailureListener{
                Toast.makeText(this, "${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }
}