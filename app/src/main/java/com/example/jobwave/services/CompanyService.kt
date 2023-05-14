package com.example.jobwave.services

import com.example.jobwave.models.Company
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

class CompanyService(firebaseApp: FirebaseApp) {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance(firebaseApp)

    private val companiesRef: DatabaseReference = database.getReference("Company")

    // Create a new company in the Firebase Database
    fun createCompany(company: Company, callback: (Boolean) -> Unit) {
        // Use the push method to generate a new key for the company
        companiesRef.push().setValue(company)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    // Read a single company from the Firebase Database based on its key
    fun readCompany(key: String, callback: (Company?) -> Unit) {
        companiesRef.child(key).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Convert the database snapshot to a Company object
                val company = snapshot.getValue(Company::class.java)
                callback(company)
            }

            override fun onCancelled(error: DatabaseError) {
                // If the operation was cancelled, call the callback with a null result
                callback(null)
            }
        })
    }

    // Read all companies from the Firebase Database
    fun readAllCompanies(callback: (List<Company>) -> Unit) {
        companiesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Convert each child snapshot to a Company object and add it to the list
                val companies = mutableListOf<Company>()
                for (childSnapshot in snapshot.children) {
                    // Retrieve the key from the child snapshot and set it to the company object
                    val key = childSnapshot.key
                    val company = childSnapshot.getValue(Company::class.java)
                    if (company != null) {
                        company.key = key
                        companies.add(company)
                    }
                }
                // Call the callback with the list of companies
                callback(companies)
            }

            override fun onCancelled(error: DatabaseError) {
                // If the operation was cancelled, call the callback with an empty list result
                callback(emptyList())
            }
        })
    }

    // Update an existing company in the Firebase Database based on its key
    fun updateCompany(key: String, company: Company, callback: (Boolean) -> Unit) {
        // Extract the relevant fields from the Company object to update the database
        val updates = mapOf(
            "companyName" to company.companyName,
            "Owner" to company.Owner,
            "email" to company.email,
            "contactNumber" to company.contactNumber,
            "address" to company.address,
            "industry" to company.industry,
            "description" to company.description,
            "status" to company.status
        )

        // Update the company at the specified key location in the database
        companiesRef.child(key).updateChildren(updates)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    // Delete a company from the Firebase Database based on its key
    fun deleteCompany(key: String, callback: (Boolean) -> Unit) {
        // Remove the company at the specified key location from the database
        companiesRef.child(key).removeValue()
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
}