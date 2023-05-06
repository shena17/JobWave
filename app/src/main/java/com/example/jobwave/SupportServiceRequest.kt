package com.example.jobwave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SupportServiceRequest : AppCompatActivity() {

    private lateinit var requestRecyclerView: RecyclerView
    private lateinit var requestList: ArrayList<SupportServicesModel>
    private lateinit var dbRef: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support_service_request)

        requestRecyclerView = findViewById(R.id.requestList)
        requestRecyclerView.layoutManager = LinearLayoutManager(this)
        requestRecyclerView.setHasFixedSize(true)

        requestList = arrayListOf<SupportServicesModel>()

        getRequestData()

        val signOutBtn = findViewById<AppCompatButton>(R.id.signOut)

        signOutBtn.setOnClickListener{
            //Init and attach
            auth = FirebaseAuth.getInstance();
            //Call signOut()
            auth.signOut();
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }

    private fun getRequestData() {
        requestRecyclerView.visibility = View.GONE

        dbRef = FirebaseDatabase.getInstance().getReference("Requests")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                requestList.clear()
                if (snapshot.exists()){
                    for (requestSnap in snapshot.children){
                        val requestData = requestSnap.getValue(SupportServicesModel::class.java)
                        requestList.add(requestData!!)
                    }
                    val mAdapter = supportServiceAdapter(requestList)
                    requestRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : supportServiceAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@SupportServiceRequest, ReviewSupportServiceRequest::class.java)

                            //Put extras
                            intent.putExtra("cusId", requestList[position].cusId)
                            intent.putExtra("name_f", requestList[position].name_f)
                            intent.putExtra("email_f", requestList[position].email_f)
                            intent.putExtra("concern_f", requestList[position].concern_f)
                            startActivity(intent)
                        }

                    })

                    requestRecyclerView.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}