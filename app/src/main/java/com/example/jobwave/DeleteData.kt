package com.example.jobwave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jobwave.databinding.ActivityDeleteDataBinding
import com.example.jobwave.databinding.ActivityUpdateDataBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeleteData : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteDataBinding
    private lateinit var database:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_delete_data)
        binding = ActivityDeleteDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.deletedataBtn.setOnClickListener{

            var id = binding.etuserName.text.toString()
            if(id.isNotEmpty()){
                deleteData(id)
            }else{
                Toast.makeText(this,"Please enter a valid job ID",Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun deleteData(id:String){
        database = FirebaseDatabase.getInstance().getReference("Job Post")
        database.child(id).removeValue().addOnSuccessListener {

            binding.etuserName.text.clear()
            Toast.makeText(this,"Successfully Deleted",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
        }
    }
}