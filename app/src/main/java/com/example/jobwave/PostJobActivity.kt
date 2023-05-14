package com.example.jobwave

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.CaseMap.Title
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobwave.adapter.EmployerAdapter
import com.example.jobwave.models.Employer
import com.example.jobwave.services.EmployerServices
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class PostJobActivity : AppCompatActivity() {

    private lateinit var fabBtn: FloatingActionButton

    //Recycler View
    private lateinit var recyclerView: RecyclerView

    //Firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var JobPostDatabase: DatabaseReference

    private lateinit var emp:Employer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_job)


        fabBtn = findViewById(R.id.fab_add)

        mAuth = FirebaseAuth.getInstance()

        val mUser = mAuth.currentUser
        val uId = mUser?.uid

        JobPostDatabase =
            uId?.let { FirebaseDatabase.getInstance().getReference().child("Job Post").child(it) }!!

        recyclerView = findViewById(R.id.recycler_job_post_id)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd
        linearLayoutManager.reverseLayout

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager

        fabBtn.setOnClickListener() {
            startActivity(Intent(applicationContext, InsertJobPostActivity::class.java))
        }
    }

    protected override fun onStart() {
        super.onStart()

        //27

        }

        fun populateViewHolder(viewHolder: MyViewHolder){
            emp.getTitle()?.let { viewHolder.setJobTitle(it) }
            emp.getDate()?.let { viewHolder.setJobDate(it) }
            emp.getDescription()?.let { viewHolder.setJobDEscription(it) }
            emp.getSkills()?.let { viewHolder.setJobSkills(it) }
            emp.getSalary()?.let { viewHolder.setJobSalary(it) }

            recyclerView.adapter
        }
    }



    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var myView: View

        public fun MyViewHolder(itemView: View) {
            super.itemView
            myView = itemView
        }


        public fun setJobTitle(title: String) {
            val mTitle: TextView = myView.findViewById(R.id.job_title)
            mTitle.text = title
        }

        public fun setJobDate(date: String) {
            val mDate: TextView = myView.findViewById(R.id.job_date)
            mDate.text = date
        }

        public fun setJobDEscription(description: String) {
            val mDescription: TextView = myView.findViewById(R.id.job_description)
            mDescription.text = description
        }

        public fun setJobSkills(skills: String) {
            val mSkills: TextView = myView.findViewById(R.id.job_skill)
            mSkills.text = skills
        }

        public fun setJobSalary(salary: String) {
            val mSalary: TextView = myView.findViewById(R.id.job_salary)
            mSalary.text = salary
        }
    }

