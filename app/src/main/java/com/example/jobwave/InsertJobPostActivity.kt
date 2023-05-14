package com.example.jobwave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.jobwave.models.Employer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.DateFormat
import java.util.Date


class InsertJobPostActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var job_title:EditText
    private lateinit var job_description:EditText
    private lateinit var job_skills:EditText
    private lateinit var job_salary:EditText

    private lateinit var btn_post_job:Button

    private  lateinit var mAuth: FirebaseAuth
    private lateinit var mJobPost:DatabaseReference

    private lateinit var mPublicDatabase:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_job_post)

        toolbar=findViewById(R.id.insert_job_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle("Post Job")

        mAuth = FirebaseAuth.getInstance()

        val mUser = mAuth.currentUser
        val uid = mUser?.uid

        mJobPost = uid?.let { FirebaseDatabase.getInstance().getReference().child(it) }!!

        mPublicDatabase=FirebaseDatabase.getInstance().getReference().child("Job Post")

        InsertJob()
    }

    private fun InsertJob(){
        job_title = findViewById(R.id.job_title);
        job_description=findViewById(R.id.job_description)
        job_skills = findViewById(R.id.job_skill)
        job_salary  = findViewById(R.id.job_salary)

        btn_post_job=findViewById(R.id.btn_job_post)

        btn_post_job.setOnClickListener(){
            val title = job_title.text.toString().trim()
            val description = job_salary.text.toString().trim()
            val skills = job_skills.text.toString().trim()
            val salary = job_salary.text.toString().trim()

            if(TextUtils.isEmpty(title)){
                job_title.setError("Required field")
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(description)){
                job_description.setError("Required Field")
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(skills)){
                job_skills.setError("Required Field")
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(salary)){
                job_salary.setError("Required field")
                return@setOnClickListener
            }

            val id = mJobPost.push().key
            val date = DateFormat.getDateInstance().format(Date())

            val emp = id?.let { it1 -> Employer(title,description, skills, salary, it1, date) }

            if (id != null) {
                mJobPost.child(id).setValue((emp))
            }

            if (id != null) {
                mPublicDatabase.child(id).setValue(emp)
            }



            Toast.makeText(applicationContext,"Successfull",Toast.LENGTH_SHORT).show()

            startActivity(Intent(applicationContext,PostJobActivity::class.java))

        }

    }
}