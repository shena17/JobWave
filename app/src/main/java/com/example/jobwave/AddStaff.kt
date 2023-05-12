package com.example.jobwave
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jobwave.models.StaffMember

import com.example.jobwave.services.StaffService
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseApp

class AddStaff : AppCompatActivity() {

    private lateinit var staffService: StaffService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_staff)
        val firebaseApp = FirebaseApp.initializeApp(this)
        if (firebaseApp != null) {
            staffService = StaffService(firebaseApp)
        } else {
            Toast.makeText(this, "Failed to initialize Firebase", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val fullNameEditText = findViewById<TextInputEditText>(R.id.editname)
        val emailEditText = findViewById<TextInputEditText>(R.id.editemail)
        val contactNumberEditText = findViewById<TextInputEditText>(R.id.editphone)
        val nicEditText = findViewById<TextInputEditText>(R.id.editnic)
        val addressEditText = findViewById<TextInputEditText>(R.id.editaddress)
        val passwordEditText = findViewById<TextInputEditText>(R.id.editpassword)
        val submitButton = findViewById<Button>(R.id.submit)

        submitButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val contactNumber = contactNumberEditText.text.toString().trim()
            val nic = nicEditText.text.toString().trim()
            val address = addressEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Validate full name field
            if (fullName.isEmpty()) {
                fullNameEditText.error = "Full name is required"
                fullNameEditText.requestFocus()
                return@setOnClickListener
            }

            // Validate email field
            if (email.isEmpty()) {
                emailEditText.error = "Email is required"
                emailEditText.requestFocus()
                return@setOnClickListener
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.error = "Please enter a valid email"
                emailEditText.requestFocus()
                return@setOnClickListener
            }

            // Validate contact number field
            if (contactNumber.isEmpty()) {
                contactNumberEditText.error = "Contact number is required"
                contactNumberEditText.requestFocus()
                return@setOnClickListener
            } else if (!Patterns.PHONE.matcher(contactNumber).matches()) {
                contactNumberEditText.error = "Please enter a valid contact number"
                contactNumberEditText.requestFocus()
                return@setOnClickListener
            }

            // Validate NIC field
            if (nic.isEmpty()) {
                nicEditText.error = "NIC is required"
                nicEditText.requestFocus()
                return@setOnClickListener
            }

            // Validate password field
            val passwordLayout = findViewById<TextInputLayout>(R.id.password)
            if (password.isEmpty()) {
                passwordLayout.error = "password is required"
                passwordLayout.requestFocus()
                return@setOnClickListener
            } else if (password.length < 8 || password.length>16){
                passwordLayout.error = "Password must be between 8 and 16 characters"
                passwordLayout.requestFocus()
                return@setOnClickListener
            }

            // All fields are valid, continue with adding staff logic
            val staffMember = StaffMember(fullName, email, contactNumber, nic, address,password)

            staffService.addStaffMember(staffMember) { success ->
                if (success) {
                    Toast.makeText(this@AddStaff, "Staff member added successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@AddStaff, "Failed to add staff member", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun viewStaff(view : View){
        val viewStaffIntent = Intent(this@AddStaff, StaffListActivity::class.java)
        startActivity(viewStaffIntent)
    }
}
