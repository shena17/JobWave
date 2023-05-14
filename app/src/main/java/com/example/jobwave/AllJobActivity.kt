package com.example.jobwave

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobwave.adapter.EmployerAdapter
import com.example.jobwave.adapter.ViewAdsAdapter
import com.example.jobwave.databinding.ActivityAllJobBinding
import com.example.jobwave.models.Employer
import com.example.jobwave.services.EmployerServices
import com.google.firebase.FirebaseApp

class AllJobActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllJobBinding
    private var madapter: EmployerAdapter? = null
    private lateinit var jobsAdapter: EmployerAdapter
    private lateinit var jobsServices: EmployerServices
    private lateinit var noRecordsTextView: TextView
    private lateinit var recyclerViewjobs: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllJobBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_main)

        setContentView(binding.root)


        recyclerViewjobs = findViewById(R.id.recyclerViewJobs)
        noRecordsTextView = findViewById(R.id.noRecordsTextView)
        recyclerViewjobs.layoutManager = LinearLayoutManager(this)

        val firebaseApp = FirebaseApp.initializeApp(this)
        if (firebaseApp != null) {
            jobsServices = EmployerServices(firebaseApp)
        } else {
            Toast.makeText(this, "Failed to initialize Firebase", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        jobsServices.readAllJobs { jobs ->
            if (jobs.isNotEmpty()) {
                noRecordsTextView.visibility = View.GONE
                jobsAdapter = EmployerAdapter(jobs) { job ->
                    showDeleteConfirmationDialog(job as Employer)
                }
                recyclerViewjobs.adapter = jobsAdapter
            } else {
                noRecordsTextView.visibility = View.VISIBLE
                recyclerViewjobs.visibility = View.GONE
            }
        }


    }

    private fun setAdapter(list: Unit) {
        madapter?.setData(list)
    }

    private fun showDeleteConfirmationDialog(job: Employer) {

    }
}

