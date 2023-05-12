package com.example.jobwave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import com.example.jobwave.models.StaffMember
import com.example.jobwave.services.StaffService
import com.google.firebase.FirebaseApp

class EditStaff : AppCompatActivity() {

    private lateinit var fullNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var contactNumberEditText: EditText
    private lateinit var nicEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var staffMember: StaffMember
    private lateinit var staffService: StaffService



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_staff)
        // Initialize staff member adapter


        val firebaseApp = FirebaseApp.initializeApp(this)
        if (firebaseApp != null) {
            staffService = StaffService(firebaseApp)
        } else {
            Toast.makeText(this, "Failed to initialize Firebase", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        fullNameEditText = findViewById(R.id.editname)
        emailEditText = findViewById(R.id.editemail)
        contactNumberEditText = findViewById(R.id.editphone)
        nicEditText = findViewById(R.id.editnic)
        addressEditText = findViewById(R.id.editaddress)
        passwordEditText = findViewById(R.id.editpassword)
        updateButton = findViewById(R.id.Edit)

        // Retrieve the StaffMember object from the intent extras
        staffMember = intent.getParcelableExtra<StaffMember>("staffMember") ?: StaffMember()

        // Set the values of the corresponding text input fields
        fullNameEditText.setText(staffMember.fullName)
        emailEditText.setText(staffMember.email)
        contactNumberEditText.setText(staffMember.contactNumber)
        nicEditText.setText(staffMember.nic)
        addressEditText.setText(staffMember.address)
        passwordEditText.setText(staffMember.password)

        // Show the update button and hide the submit button
        updateButton.visibility = Button.VISIBLE
        findViewById<Button>(R.id.submit).visibility = Button.GONE

        // Enable the update button only if all the text fields have some text
        updateButton.isEnabled = (fullNameEditText.text.isNotBlank() && emailEditText.text.isNotBlank()
                && contactNumberEditText.text.isNotBlank() && nicEditText.text.isNotBlank()
                && addressEditText.text.isNotBlank())

        // Enable the update button only if all the text fields have some text
        fullNameEditText.doOnTextChanged { _, _, _, _ -> updateButton.isEnabled = isAllFieldsFilled() }
        emailEditText.doOnTextChanged { _, _, _, _ -> updateButton.isEnabled = isAllFieldsFilled() }
        contactNumberEditText.doOnTextChanged { _, _, _, _ -> updateButton.isEnabled = isAllFieldsFilled() }
        nicEditText.doOnTextChanged { _, _, _, _ -> updateButton.isEnabled = isAllFieldsFilled() }
        addressEditText.doOnTextChanged { _, _, _, _ -> updateButton.isEnabled = isAllFieldsFilled() }
        passwordEditText.doOnTextChanged { _, _, _, _ -> updateButton.isEnabled = isAllFieldsFilled() }




    }

    // Check whether all the text fields have some text
    private fun isAllFieldsFilled(): Boolean {
        return fullNameEditText.text.isNotBlank() && emailEditText.text.isNotBlank()
                && contactNumberEditText.text.isNotBlank() && nicEditText.text.isNotBlank()
                && addressEditText.text.isNotBlank()&& passwordEditText.text.isNotBlank()
    }

    fun updateStaff(view:View){
        // Show a confirmation dialog box
        AlertDialog.Builder(this)
            .setTitle("Update staff member")
            .setMessage("Are you sure you want to update this staff member?")
            .setPositiveButton(android.R.string.yes) { _, _ ->
                // Update the staff member
                val updatedStaffMember = StaffMember(
                    fullNameEditText.text.toString(),
                    emailEditText.text.toString(),
                    contactNumberEditText.text.toString(),
                    nicEditText.text.toString(),
                    addressEditText.text.toString(),
                    passwordEditText.text.toString()
                )
                staffService.updateStaffMember(staffMember.key ?: "", updatedStaffMember) { success ->
                    if (success) {
                        val viewStaffIntent = Intent(this, StaffListActivity::class.java)
                        viewStaffIntent.putExtra("staffMember", staffMember)
                        startActivity(viewStaffIntent)
                        finish()
                    }
                }
            }
            .setNegativeButton(android.R.string.no, null)
            .show()
    }

    fun viewStaff(view : View){
        val viewStaffIntent = Intent(this@EditStaff, StaffListActivity::class.java)
        startActivity(viewStaffIntent)
        finish()
    }


}


