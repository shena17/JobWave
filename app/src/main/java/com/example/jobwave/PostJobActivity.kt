package com.example.jobwave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PostJobActivity : AppCompatActivity() {

    private lateinit var fabBtn: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_job)


        fabBtn=findViewById(R.id.fab_add);

        fabBtn.setOnClickListener(){
            startActivity(Intent(applicationContext,InsertJobPostActivity::class.java))
        }
    }
}
