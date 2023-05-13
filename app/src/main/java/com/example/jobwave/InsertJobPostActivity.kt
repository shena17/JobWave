package com.example.jobwave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar


class InsertJobPostActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_job_post)

        toolbar=findViewById(R.id.insert_job_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle("Post Job")



    }
}