package com.example.jobwave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobwave.adapter.CompanyListAdapter
import com.example.jobwave.services.CompanyService
import com.google.firebase.FirebaseApp

class CompanyListActivity : AppCompatActivity() {

    private lateinit var companyList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_list)

        companyList = findViewById(R.id.companyList)
        companyList.layoutManager = LinearLayoutManager(this)

        // Read all companies from the Firebase Realtime Database
        val companyService = CompanyService(FirebaseApp.getInstance())
        companyService.readAllCompanies { companies ->
            // Create the adapter with the list of companies and set it to the RecyclerView
            val adapter = CompanyListAdapter(companies)
            companyList.adapter = adapter
        }
    }
}
