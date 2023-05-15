package com.example.jobwave

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobwave.models.Employer
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AllJobActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView

    private lateinit var mAllJobPost: DatabaseReference
    private lateinit var emp:Employer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_job)


        toolbar = findViewById(R.id.all_job_post)
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle("All Job Post")


       // recyclerView  = findViewById(R.id.recycler_all_job)

        val linearLayoutManager = LinearLayoutManager(this)

        linearLayoutManager.stackFromEnd
        linearLayoutManager.reverseLayout

        //Database
        mAllJobPost = FirebaseDatabase.getInstance().getReference().child("Public Database")
        mAllJobPost.keepSynced(true)

    }

    protected override fun onStart(){
        super.onStart()

    fun populateViewHolder(viewHolder: AllJobPostViewHolder) {
        emp.getTitle()?.let { viewHolder.setJobTitle(it) }
        emp.getDate()?.let { viewHolder.setJobDate(it) }
        viewHolder.setJobDescription((emp.getDescription()))
        emp.getSalary()?.let { viewHolder.setJobSalary(it) }

        viewHolder.myView.setOnClickListener(View.OnClickListener() {
            fun onClick(view: View){
                val intent = Intent(applicationContext,JobDetailsActivity::class.java)

                intent.putExtra("title",emp.getTitle())
                intent.putExtra("date",emp.getDate())
                intent.putExtra("description",emp.getDescription())
                intent.putExtra("skills",emp.getSkills())
                intent.putExtra("salary",emp.getSkills())

                startActivity(intent)
            }
        })

    }
        recyclerView.adapter

    }

     class AllJobPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         val myView:View
             get() {
                 TODO()
             }

         public fun AllJobPostViewHolder(itemView: View){
             super.itemView
         }

         public fun setJobTitle(title: String) {
             val mTitle: TextView = myView.findViewById(R.id.all_job_post_title)
             mTitle.text = title
         }

         public fun setJobDate(date: String) {
             val mDate: TextView = myView.findViewById(R.id.all_job_post_date)
             mDate.text = date
         }

         public fun setJobDescription(description: String?) {
             val mDescription: TextView = myView.findViewById(R.id.all_job_post_description)
             mDescription.text = description
         }

         public fun setJobSkills(skills: String) {
             val mSkills: TextView = myView.findViewById(R.id.all_job_post_skills)
             mSkills.text = skills
         }

         public fun setJobSalary(salary: String) {
             val mSalary: TextView = myView.findViewById(R.id.all_job_post_salary)
             mSalary.text = salary
         }
    }
}

