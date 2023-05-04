package com.example.jobwave

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jobwave.databinding.ActivityEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class EditProfile : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var user: Users
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

//        Retrive data from db
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        if (uid.isNotEmpty()){

            getUserDetails()

        }else{
            Toast.makeText(baseContext, "Cannot find user!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }


        val updateBtn = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.profileNavBtn)

        updateBtn.setOnClickListener {
            updateDetails()
        }

        val deleteBtn = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.deleteBtn)

        deleteBtn.setOnClickListener {
            deleteUser()
        }
    }

    private fun deleteUser() {

        val user = Firebase.auth.currentUser!!
        user.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "User deleted Successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, Login::class.java))
                    finish()
                }
        }.addOnFailureListener{
            Toast.makeText(baseContext, "Unable to delete user", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateDetails() {

        val fullname = binding.editName.text.toString()
        val intro = binding.editIntro.text.toString()
        val description = binding.editDescription.text.toString()
        val job = binding.editJob.text.toString()
        val phone = binding.editPhone.text.toString()

        val updateUser = mapOf<String, String>(
            "fullName" to fullname,
            "intro" to intro,
            "description" to description,
            "job" to job,
            "phone" to phone
        )

        databaseReference.child(uid).updateChildren(updateUser).addOnSuccessListener {
            Toast.makeText(baseContext, "Update Successfully", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(this, "${it.localizedMessage}", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getUserDetails() {

        databaseReference.child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                user = snapshot.getValue(Users::class.java)!!
                binding.editName.setText(user.fullName)
                binding.editIntro.setText(user.intro)
                binding.editDescription.setText(user.description)
                binding.editJob.setText(user.job)
                binding.editEmail.setText(user.email)
                binding.editPhone.setText(user.phone)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@EditProfile, "Cancelled", Toast.LENGTH_SHORT).show()
            }

        })

    }

    override fun onBackPressed() {
        val setIntent = Intent(this, Dashboard::class.java)
        startActivity(setIntent)
    }

}