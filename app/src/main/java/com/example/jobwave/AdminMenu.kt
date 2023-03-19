package com.example.jobwave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class AdminMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_menu)

        val approveCompany = findViewById<androidx.cardview.widget.CardView>(R.id.approveCompany)

        approveCompany.setOnClickListener{
            val intent = Intent(this, ApproveCompany::class.java)
            startActivity(intent)
        }

        val manageAds = findViewById<androidx.cardview.widget.CardView>(R.id.manageAds)

        manageAds.setOnClickListener{
            val intent = Intent(this, ManageAds::class.java)
            startActivity(intent)
        }

        val manageStaff = findViewById<androidx.cardview.widget.CardView>(R.id.manageStaff)

        manageStaff.setOnClickListener{
            val intent = Intent(this, ViewStaff::class.java)
            startActivity(intent)
        }
    }
}