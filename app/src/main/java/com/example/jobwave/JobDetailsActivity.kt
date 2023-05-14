package com.example.jobwave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.DialogTitle
import androidx.appcompat.widget.Toolbar


class JobDetailsActivity : AppCompatActivity() {

    private lateinit var mTitle: TextView
    private lateinit var mDate: TextView
    private lateinit var mDescription: TextView
    private lateinit var mSkills: TextView
    private lateinit var mSalary: TextView

    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_details)
        toolbar = findViewById(R.id.toolbar_job_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle("Job Details")

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mTitle = findViewById(R.id.job_details_title)
        mDate = findViewById(R.id.job_details_date)
        mDescription = findViewById(R.id.job_details_description)
        mSalary = findViewById(R.id.job_details_salary)
        mSkills = findViewById(R.id.job_details_skills)

        //Recieve Data from all job activity

        val intent:Intent = getIntent()

        val title = intent.getStringExtra("title")
        val date = intent.getStringExtra("date")
        val description = intent.getStringExtra("description")
        val skills = intent.getStringExtra("skills")
        val salary = intent.getStringExtra("salary")

        mTitle.setText(title)
        mDate.setText(date)
        mDescription.setText(description)
        mSkills.setText(skills)
        mSalary.setText(salary)

    }

}