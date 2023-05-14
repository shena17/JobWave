package com.example.jobwave.services

import com.example.jobwave.models.Employer
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

class EmployerServices(firebaseApp: FirebaseApp) {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance(firebaseApp)

    private val jobsRef: DatabaseReference = database.getReference("Job Post")

    // Create a new job in the Firebase Database
    fun createJob(job: Employer, callback: (Boolean) -> Unit) {
        // Use the push method to generate a new key for the job
        jobsRef.push().setValue(job)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    // Read a single job from the Firebase Database based on its key
    fun readJob(id: String, callback: (Employer?) -> Unit) {
        jobsRef.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Convert the database snapshot to an job object
                val job = snapshot.getValue(Employer::class.java)
                callback(job)
            }

            override fun onCancelled(error: DatabaseError) {
                // If the operation was cancelled, call the callback with a null result
                callback(null)
            }
        })
    }

    // Read all jobs from the Firebase Database
    fun readAllJobs(callback: (List<Employer>) -> Unit) {
        jobsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Convert each child snapshot to an job object and add it to the list
                val jobs = mutableListOf<Employer>()
                for (childSnapshot in snapshot.children) {
                    // Retrieve the key from the child snapshot and set it to the job object
                    val key = childSnapshot.key
                    val job = childSnapshot.getValue(Employer::class.java)
                    if (job != null) {
                        if (key != null) {
                            job.id = key
                        }
                        jobs.add(job)
                    }
                }
                // Call the callback with the list of jobs
                callback(jobs)
            }

            override fun onCancelled(error: DatabaseError) {
                // If the operation was cancelled, call the callback with an empty list result
                callback(emptyList())
            }
        })
    }

    // Update an existing job in the Firebase Database based on its key
    fun updateJob(id: String, job:Employer, callback: (Boolean) -> Unit) {
        // Extract the relevant fields from the job object to update the database
        val updates = mapOf(
            "title" to job.title,
            "description" to job.description,
            "skills" to job.skills,
            "salary" to job.salary
        )

        // Update the job at the specified key location in the database
        jobsRef.child(id).updateChildren(updates)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    // Delete job from the Firebase Database based on its key
    fun deleteJob(id: Employer, callback: (Boolean) -> Unit) {
        // Remove the job at the specified key location from the database
        jobsRef.child(id.toString()).removeValue()
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
}
