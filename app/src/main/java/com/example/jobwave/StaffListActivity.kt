package com.example.jobwave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobwave.adapter.StaffMemberAdapter
import com.example.jobwave.models.StaffMember
import com.example.jobwave.services.StaffService
import com.google.firebase.FirebaseApp

class StaffListActivity : AppCompatActivity() {

    private lateinit var staffService: StaffService
    private lateinit var staffMemberRecyclerView: RecyclerView
    private lateinit var staffMemberAdapter: StaffMemberAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_list)

        val firebaseApp = FirebaseApp.getInstance()
        staffService = StaffService(firebaseApp)

        staffMemberRecyclerView = findViewById(R.id.staff_member_recycler_view)
        staffMemberAdapter = StaffMemberAdapter(listOf(), this::onEditButtonClicked, this::onDeleteButtonClicked)

        staffMemberRecyclerView.layoutManager = LinearLayoutManager(this)
        staffMemberRecyclerView.adapter = staffMemberAdapter

        loadStaffMembers()
    }

    private fun loadStaffMembers() {
        staffService.getAllStaffMembers { staffMembers ->
            staffMemberAdapter.updateStaffMembers(staffMembers)
        }
    }

    private fun onEditButtonClicked(staffMember: StaffMember) {
        // Send the staff member object to the EditStaff activity
        val editStaffIntent = Intent(this, EditStaff::class.java)
        editStaffIntent.putExtra("staffMember", staffMember)
        startActivity(editStaffIntent)
        finish()
    }

    private fun onDeleteButtonClicked(staffMember: StaffMember) {
        // Show a confirmation dialog before deleting the staff member
        AlertDialog.Builder(this)
            .setTitle("Delete Staff Member")
            .setMessage("Are you sure you want to delete ${staffMember.fullName}?")
            .setPositiveButton("Yes") { _, _ ->
                try {
                    // Delete the staff member from the database
                    staffService.deleteStaffMember(staffMember.key!!) { isSuccess ->
                        if (isSuccess) {
                            Toast.makeText(
                                this,
                                "${staffMember.fullName} deleted successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            loadStaffMembers()
                        } else {
                            Toast.makeText(
                                this,
                                "Failed to delete ${staffMember.fullName}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        this,
                        "An error occurred while deleting ${staffMember.key}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton("No", null)
            .show()
    }

}