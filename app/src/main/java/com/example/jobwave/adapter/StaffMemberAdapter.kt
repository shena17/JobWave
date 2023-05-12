package com.example.jobwave.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobwave.R
import com.example.jobwave.models.StaffMember


class StaffMemberAdapter(
    private var staffMembers: List<StaffMember>,
    private val onEditButtonClickListener: (StaffMember) -> Unit,
    private val onDeleteButtonClickListener: (StaffMember) -> Unit
) : RecyclerView.Adapter<StaffMemberAdapter.StaffMemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffMemberViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_staff_member, parent, false)
        return StaffMemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: StaffMemberViewHolder, position: Int) {
        val staffMember= staffMembers[position]
        holder.bind(staffMember, onEditButtonClickListener, onDeleteButtonClickListener)
    }override fun getItemCount(): Int {
        return staffMembers.size
    }

    fun updateStaffMembers(newStaffMembers: List<StaffMember>) {
        staffMembers = newStaffMembers
        notifyDataSetChanged()
    }

    class StaffMemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fullNameTextView: TextView = itemView.findViewById(R.id.full_name)
        private val emailTextView: TextView = itemView.findViewById(R.id.email)
        private val contactNumberTextView: TextView = itemView.findViewById(R.id.contact_number)
        private val nicTextView: TextView = itemView.findViewById(R.id.nic)
        private val addressTextView: TextView = itemView.findViewById(R.id.address)
        private val editButton: Button = itemView.findViewById(R.id.edit_button)
        private val deleteButton: Button = itemView.findViewById(R.id.delete_button)

        fun bind(
            staffMember: StaffMember,
            onEditButtonClickListener: (StaffMember) -> Unit,
            onDeleteButtonClickListener: (StaffMember) -> Unit
        ) {
            fullNameTextView.text = staffMember.fullName
            emailTextView.text = staffMember.email
            contactNumberTextView.text = staffMember.contactNumber
            nicTextView.text = staffMember.nic
            addressTextView.text = staffMember.address

            editButton.setOnClickListener { onEditButtonClickListener(staffMember) }
            deleteButton.setOnClickListener { onDeleteButtonClickListener(staffMember) }
        }
    }
}