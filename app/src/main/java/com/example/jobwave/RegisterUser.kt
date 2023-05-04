package com.example.jobwave

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.isEmpty
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterUser : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Initialize Firebase Auth
        auth = Firebase.auth

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

        val signup = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.signup)
        signup.setOnClickListener{
            signUp()
        }
    }

    private fun signUp(){
        val inputName = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.fullName)
        val inputEmail = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.emailReg)
        val inputJob = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.job)
        val inputPhone = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.phone)
        val inputDescription = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.description)
        val inputPassword = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.passwordReg)

        if(inputEmail.editText?.text?.isEmpty() == true || inputPassword.editText?.text?.isEmpty() == true){
            Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show()
            return
        }

        val fullName = inputName.editText?.text.toString()
        val email = inputEmail.editText?.text.toString()
        val job = inputJob.editText?.text.toString()
        val intro = inputJob.editText?.text.toString()
        val phone = inputPhone.editText?.text.toString()
        val description = inputDescription.editText?.text.toString()
        val role = "User"
        val password = inputPassword.editText?.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val databaseReference = database.reference.child("Users").child(auth.currentUser!!.uid)

                    val users : Users = Users(fullName, email, job, intro, phone, description, role, auth.currentUser!!.uid)

                    databaseReference.setValue(users).addOnCompleteListener {
                        if(it.isSuccessful){
                            Log.d(TAG, "createUserWithEmail:success")
                            //Sign in success, update UI with the signed-in user's information
                            val intent = Intent(this, Login::class.java)
                            startActivity(intent)

                            Toast.makeText(baseContext, "Registered Successfully", Toast.LENGTH_SHORT).show()
                        }

                    }

                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    // If sign in fails, display a message to the user.
//                    Toast.makeText(
//                        baseContext,
//                        "Authentication failed.",
//                        Toast.LENGTH_SHORT,
//                    ).show()
                }
            }.addOnFailureListener{
                Toast.makeText(this, "Error Occured ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }


    }
}