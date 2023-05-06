package com.example.jobwave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class NewJobPost : AppCompatActivity() {

    private lateinit var edTitle: EditText
    private lateinit var edDes: EditText
    private lateinit var edSkills: EditText
    private lateinit var edSalary: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_job_post)
    }
}