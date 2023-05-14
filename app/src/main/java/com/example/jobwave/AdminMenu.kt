package com.example.jobwave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.example.jobwave.Login.Companion.auth
import com.google.firebase.auth.FirebaseAuth

class AdminMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_menu)

        val approveCompany = findViewById<androidx.cardview.widget.CardView>(R.id.approveCompany)

        approveCompany.setOnClickListener{
            val intent = Intent(this, CompanyListActivity::class.java)
            startActivity(intent)
        }

        val manageAds = findViewById<androidx.cardview.widget.CardView>(R.id.manageAds)

        manageAds.setOnClickListener{
            val intent = Intent(this, ViewAdsActivity::class.java)
            startActivity(intent)
        }

        val manageStaff = findViewById<androidx.cardview.widget.CardView>(R.id.manageStaff)

        manageStaff.setOnClickListener{
            val intent = Intent(this, AddStaff::class.java)
            startActivity(intent)
        }
    }

    fun signOut(view: View) {
        // Sign out the user from Firebase Auth
        auth.signOut()
        // Navigate back to the Login screen
        val intent = Intent(this, Login::class.java)
        // Set the `FLAG_ACTIVITY_NEW_TASK` and `FLAG_ACTIVITY_CLEAR_TASK` flags to clear the back stack
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

}