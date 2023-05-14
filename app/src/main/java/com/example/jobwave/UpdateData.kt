package com.example.jobwave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jobwave.databinding.ActivityUpdateDataBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateData : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateDataBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_update_data)

        binding = ActivityUpdateDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.updateBtn.setOnClickListener {

            val id = binding.userName.text.toString()
            val title = binding.title.text.toString()
            val description = binding.description.text.toString()
            val salary = binding.salary.text.toString()

            updateData(id,title,description,salary)

        }
    }
    private fun updateData(userName: String, title: String, description: String, salary: String){
        database = FirebaseDatabase.getInstance().getReference("Job Post")
        val user = mapOf<String,String>(
            "title" to title,
            "description" to description,
            "salary" to salary
        )

        database.child(userName).updateChildren(user).addOnSuccessListener {

            binding.userName.text.clear()
            binding.title.text.clear()
            binding.description.text.clear()
            binding.salary.text.clear()
            Toast.makeText(this,"Successfully Updated", Toast.LENGTH_SHORT).show()


        }.addOnFailureListener{

            Toast.makeText(this,"Failed to Update", Toast.LENGTH_SHORT).show()

        }
    }


}