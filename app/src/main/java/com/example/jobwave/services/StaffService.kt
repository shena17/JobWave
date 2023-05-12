package com.example.jobwave.services



import com.example.jobwave.models.StaffMember
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener

class StaffService(firebaseApp: FirebaseApp) {

    // Get an instance of the Firebase Database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance(firebaseApp)

    // Get a reference to the "staffMembers" collection in the Firebase Database
    private val staffMembersRef: DatabaseReference = database.getReference("staffMembers")

    // Add a new staff member to the "staffMembers" collection in the Firebase Database
    // and call the provided callback function with a Boolean indicating whether the operation succeeded or failed


    fun addStaffMember(staffMember: StaffMember, callback: (Boolean) -> Unit) {
        // Use the push method to generate a new key for the staff member
        staffMembersRef.push().setValue(staffMember)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    // Retrieve a single staff member from the "staffMembers" collection in the Firebase Database
    // based on its key and call the provided callback function with the resulting staff member object
    // or null if the operation failed

    fun getStaffMember(key: String, callback: (StaffMember?) -> Unit) {
        staffMembersRef.child(key).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Convert the database snapshot to a StaffMember object
                val staffMember = snapshot.getValue(StaffMember::class.java)
                callback(staffMember)
            }

            override fun onCancelled(error: DatabaseError) {
                // If the operation was cancelled, call the callback with a null result
                callback(null)
            }
        })
    }

    // Retrieve all staff members from the "staffMembers" collection in the Firebase Database
    // and call the provided callback function with the resulting list of staff member objects

    fun getAllStaffMembers(callback: (List<StaffMember>) -> Unit) {
        staffMembersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Convert each child snapshot to a StaffMember object and add it to the list
                val staffMembers = mutableListOf<StaffMember>()
                for (childSnapshot in snapshot.children) {
                    // Retrieve the key from the child snapshot and set it to the staff member object
                    val key = childSnapshot.key
                    val staffMember = childSnapshot.getValue(StaffMember::class.java)
                    if (staffMember != null) {
                        staffMember.key = key
                        staffMembers.add(staffMember)
                    }
                }
                // Call the callback with the list of staff members
                callback(staffMembers)
            }

            override fun onCancelled(error: DatabaseError) {
                // If the operation was cancelled, call the callback with an empty list result
                callback(emptyList())
            }
        })
    }


    // Update an existing staff member in the "staffMembers" collection in the Firebase Database
    // based on its key and call the provided callback function with a Boolean indicating whether the operation succeeded or failed
    fun updateStaffMember(key : String,staffMember: StaffMember, callback: (Boolean) -> Unit) {
        // Get the key of the staff member to update


        // Extract the relevant fields from the StaffMember object to update the database
        val updates = mapOf(
            "fullName" to staffMember.fullName,
            "email" to staffMember.email,
            "contactNumber" to staffMember.contactNumber,
            "nic" to staffMember.nic,
            "address" to staffMember.address,
            "password" to staffMember.password
        )

        // Update the staff member at the specified key location in the database
        staffMembersRef.child(key).updateChildren(updates)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }


// Delete a staff member from the "staffMembers" collection in the Firebase Database

    // based on its key and call the provided callback function with a Boolean indicating whether the operation succeeded or failed

    fun deleteStaffMember(key: String, callback: (Boolean) -> Unit) {
        // Remove the staff member at the specified key location from the database
        staffMembersRef.child(key).removeValue()
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
}


