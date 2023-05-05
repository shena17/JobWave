package com.example.jobwave

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterCompany : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_company)

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance()

        auth = Firebase.auth



        val loginNav = findViewById<Button>(R.id.loginNavBtn)
        loginNav.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val regCompany = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.regUserNavBtn)
        regCompany.setOnClickListener{
            val intent = Intent(this, RegisterUser::class.java)
            startActivity(intent)
        }

        val submit = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.regCompany)
        submit.setOnClickListener{
            registerCompany()
        }

    }

    private fun registerCompany() {

        val inCompany = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.companyName)
        val inOwner = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.owner)
        val inEmail = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.companyEmail)
        val inPhone = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.companyPhone)
        val inAddress = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.address)
        val inIndustry = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.industry)
        val inDescription = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.description)
        val inPassword = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.companyPwd)

        if(inEmail.editText?.text?.isEmpty() == true || inPassword.editText?.text?.isEmpty() == true){
            Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show()
            return
        }

        var companyName = inCompany.editText?.text.toString()
        var owner = inOwner.editText?.text.toString()
        var email = inEmail.editText?.text.toString()
        var phone = inPhone.editText?.text.toString()
        var address = inAddress.editText?.text.toString()
        var industry = inIndustry.editText?.text.toString()
        var description = inDescription.editText?.text.toString()
        var password = inPassword.editText?.text.toString()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){task ->
            if(task.isSuccessful){
                val databaseReference = databaseReference.reference.child("Company").child(auth.currentUser!!.uid)

                val company : Company = Company(companyName, owner, email, phone, address, industry, description, auth.currentUser!!.uid)

                databaseReference.setValue(company).addOnCompleteListener{
                    if(it.isSuccessful){
                        Log.d(ContentValues.TAG, "createCompanyWithEmail:success")
                        //Sign in success, update UI with the signed-in user's information
                        val intent = Intent(this, Login::class.java)
                        startActivity(intent)

                        Toast.makeText(baseContext, "Company Registered Successfully", Toast.LENGTH_SHORT).show()
                    }
                }

            }else {
                Log.w(ContentValues.TAG, "createCompanyWithEmail:failure", task.exception)

            }
        }.addOnFailureListener{
            Toast.makeText(this, "Error Occured ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
        }

    }
}