package com.example.jobwave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jobwave.databinding.ActivityReadDataBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ReadData : AppCompatActivity() {

    private lateinit var binding: ActivityReadDataBinding
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_read_data)
        binding = ActivityReadDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.readdataBtn.setOnClickListener {

            val id: String = binding.etusername.text.toString()

            if (id.isNotEmpty()) {

                readData(id)

            } else {

                Toast.makeText(this, "PLease enter the job Id", Toast.LENGTH_SHORT).show()

            }

        }

    }

    private fun readData(id: String) {

        database = FirebaseDatabase.getInstance().getReference("Job Post")
        database.child(id).get().addOnSuccessListener {
            if (it.exists()){

                val title = it.child("title").value
                val description = it.child("description").value
                val salary = it.child("salary").value
                Toast.makeText(this,"Successfully Read",Toast.LENGTH_SHORT).show()
                binding.etusername.text.clear()
                binding.tvjobtitle.text = title.toString()
                binding.tvjobdescription.text = description.toString()
                binding.tvjobsalary.text = salary.toString()

            }else{

                Toast.makeText(this,"User Doesn't Exist",Toast.LENGTH_SHORT).show()


            }

        }.addOnFailureListener{
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
        }
    }
}