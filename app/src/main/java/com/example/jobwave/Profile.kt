package com.example.jobwave

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputBinding
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import com.example.jobwave.databinding.FragmentProfileBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Profile : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var user : Users
    private lateinit var uid : String

    private var _binding: FragmentProfileBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize Firebase Auth
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        database = FirebaseDatabase.getInstance().getReference("Users")


        if(uid.isEmpty()){
            Toast.makeText(activity,"Sign in again!",Toast.LENGTH_SHORT).show();
            requireActivity().run {
                startActivity(Intent(this, Login::class.java))
                finish()
            }
        }else{
            database.child(uid).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    user = snapshot.getValue(Users::class.java)!!
                    binding.fullNameProfile.setText(user.fullName)
                    binding.emailProfile.setText(user.email)
                    binding.jobProfile.setText(user.job)
                    binding.intro.setText(user.intro)
                    binding.phoneProfile.setText(user.phone)
                    binding.descriptionProfile.setText(user.description)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(activity, "Failed to get User data!", Toast.LENGTH_SHORT).show()
                }
            })
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val editProfileBtn = view.findViewById<AppCompatButton>(R.id.editProfileBtn)
        editProfileBtn.setOnClickListener{
            requireActivity().run {
                startActivity(Intent(this, EditProfile::class.java))
                finish()
            }
        }

        val signOutBtn = view.findViewById<AppCompatButton>(R.id.signOut)

        signOutBtn.setOnClickListener{
            requireActivity().run {
                //Init and attach
                auth = FirebaseAuth.getInstance();

                //Call signOut()
                auth.signOut();
                startActivity(Intent(this, Login::class.java))
                finish()
            }
        }
    }



}