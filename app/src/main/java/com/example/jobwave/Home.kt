package com.example.jobwave

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Home : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

     return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val request = view.findViewById<androidx.cardview.widget.CardView>(R.id.requestBtn)

        request.setOnClickListener{
            requireActivity().run {
                startActivity(Intent(this, SupportServices::class.java))
                finish()
            }
        }
    }


}