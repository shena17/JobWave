package com.example.jobwave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth

class AdminMenu : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

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

        val signOutBtn = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.signOut)
        signOutBtn.setOnClickListener{
            //Init and attach
            auth = FirebaseAuth.getInstance();
            //Call signOut()
            auth.signOut();
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        val setIntent = Intent(Intent.ACTION_MAIN)
        setIntent.addCategory(Intent.CATEGORY_HOME)
        setIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(setIntent)
    }

}