package com.example.jobwave

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Profile : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val editProfileBtn = view.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.editProfileBtn)

        editProfileBtn.setOnClickListener{
            requireActivity().run {
                startActivity(Intent(this, EditProfile::class.java))
                finish()
            }
        }
    }


}