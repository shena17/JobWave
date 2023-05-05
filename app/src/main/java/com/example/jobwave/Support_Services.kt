package com.example.jobwave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SupportServices : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var concern: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support_services)

        name = findViewById(R.id.userName)
        email = findViewById(R.id.user_email)
        concern = findViewById(R.id.user_concerns)
        btnSaveData = findViewById(R.id.dashboardNavBtn)

        dbRef = FirebaseDatabase.getInstance().getReference("Requests")

        btnSaveData.setOnClickListener {
            saveSupportServiceData()
        }
    }

    private fun saveSupportServiceData() {

        //Getting form values
        val name_f = name.text.toString()
        val email_f = email.text.toString()
        val concern_f = concern.text.toString()

        if (name_f.isEmpty()){
            name.error = "Please enter name"
        }
        if (email_f.isEmpty()){
            email.error = "Please enter email"
        }
        if (concern_f.isEmpty()){
            concern.error = "Please enter concern"
        }

        //Getting unique ID for each request
        val cusId = dbRef.push().key!!

        val request = SupportServicesModel(cusId, name_f, email_f, concern_f)

        dbRef.child(cusId).setValue(request)
            .addOnCompleteListener{
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                name.text.clear()
                email.text.clear()
                concern.text.clear()

            }.addOnFailureListener{ err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }

    override fun onBackPressed() {
        val setIntent = Intent(this, Dashboard::class.java)
        startActivity(setIntent)
    }
}
