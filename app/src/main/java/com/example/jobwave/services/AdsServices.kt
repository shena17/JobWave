package com.example.jobwave.services

import com.example.jobwave.models.Advertisement
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

class AdsServices(firebaseApp: FirebaseApp) {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance(firebaseApp)

    private val adsRef: DatabaseReference = database.getReference("Advertisement")

    // Create a new advertisement in the Firebase Database
    fun createAdvertisement(advertisement: Advertisement, callback: (Boolean) -> Unit) {
        // Use the push method to generate a new key for the advertisement
        adsRef.push().setValue(advertisement)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    // Read a single advertisement from the Firebase Database based on its key
    fun readAdvertisement(key: String, callback: (Advertisement?) -> Unit) {
        adsRef.child(key).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Convert the database snapshot to an Advertisement object
                val advertisement = snapshot.getValue(Advertisement::class.java)
                callback(advertisement)
            }

            override fun onCancelled(error: DatabaseError) {
                // If the operation was cancelled, call the callback with a null result
                callback(null)
            }
        })
    }

    // Read all advertisements from the Firebase Database
    fun readAllAdvertisements(callback: (List<Advertisement>) -> Unit) {
        adsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Convert each child snapshot to an Advertisement object and add it to the list
                val advertisements = mutableListOf<Advertisement>()
                for (childSnapshot in snapshot.children) {
                    // Retrieve the key from the child snapshot and set it to the advertisement object
                    val key = childSnapshot.key
                    val advertisement = childSnapshot.getValue(Advertisement::class.java)
                    if (advertisement != null) {
                        advertisement.key = key
                        advertisements.add(advertisement)
                    }
                }
                // Call the callback with the list of advertisements
                callback(advertisements)
            }

            override fun onCancelled(error: DatabaseError) {
                // If the operation was cancelled, call the callback with an empty list result
                callback(emptyList())
            }
        })
    }

    // Update an existing advertisement in the Firebase Database based on its key
    fun updateAdvertisement(key: String, advertisement: Advertisement, callback: (Boolean) -> Unit) {
        // Extract the relevant fields from the Advertisement object to update the database
        val updates = mapOf(
            "fullName" to advertisement.fullName,
            "email" to advertisement.email,
            "contactNumber" to advertisement.contactNumber,
            "description" to advertisement.description
        )

        // Update the advertisement at the specified key location in the database
        adsRef.child(key).updateChildren(updates)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    // Delete an advertisement from the Firebase Database based on its key
    fun deleteAdvertisement(key: String, callback: (Boolean) -> Unit) {
        // Remove the advertisement at the specified key location from the database
        adsRef.child(key).removeValue()
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
}
