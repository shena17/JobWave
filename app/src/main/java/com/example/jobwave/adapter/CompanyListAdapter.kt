package com.example.jobwave.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.jobwave.R
import com.example.jobwave.models.Company
import com.example.jobwave.services.CompanyService
import com.google.firebase.FirebaseApp



class CompanyListAdapter(private val companies: List<Company>) :
    RecyclerView.Adapter<CompanyListAdapter.CompanyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.company_item, parent, false)
        return CompanyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        val company = companies[position]
        holder.companyName.text = company.companyName
        holder.owner.text = company.Owner
        holder.email.text = company.email
        holder.contactNumber.text = company.contactNumber
        holder.address.text = company.address
        holder.industry.text = company.industry
        holder.description.text = company.description
        holder.status.text = company.status

        // Enable/disable confirm and decline buttons based on company status
        holder.confirmButton.isEnabled = company.status != "confirmed"
        holder.declineButton.isEnabled = company.status != "declined"

        holder.confirmButton.setOnClickListener {
            // Perform the confirm action for the company
            if (company.status != "confirmed") {
                company.status = "confirmed"
                updateCompanyStatus(company, holder)
                holder.confirmButton.isEnabled = false
                holder.confirmButton.text = "Confirmed"
            }
        }
        holder.declineButton.setOnClickListener {
            // Perform the decline action for the company
            if (company.status != "declined") {
                company.status = "declined"
                updateCompanyStatus(company, holder)
                holder.declineButton.isEnabled = false
                holder.declineButton.text = "Declined"
            }
        }
    }

    override fun getItemCount(): Int {
        return companies.size
    }

    private fun updateCompanyStatus(company: Company, holder: CompanyViewHolder) {
        // Update the status of the company in the Firebase Realtime Database
        val companyService = CompanyService(FirebaseApp.getInstance())
        companyService.updateCompany(company.key!!, company) { success ->
            if (success) {
                // If the update was successful, notify the adapter that the data has changed
                notifyDataSetChanged()
            } else {
                // If the update failed, show an error message
                Toast.makeText(
                    holder.itemView.context,
                    "Failed to update company status",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    inner class CompanyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val companyName: TextView = itemView.findViewById(R.id.companyName)
        val owner: TextView = itemView.findViewById(R.id.owner)
        val email: TextView = itemView.findViewById(R.id.email)
        val contactNumber: TextView = itemView.findViewById(R.id.contactNumber)
        val address: TextView = itemView.findViewById(R.id.address)
        val industry: TextView = itemView.findViewById(R.id.industry)
        val description: TextView = itemView.findViewById(R.id.companyDescription)
        val status: TextView = itemView.findViewById(R.id.companyStatus)
        val confirmButton: Button = itemView.findViewById(R.id.Edit)
        val declineButton: Button = itemView.findViewById(R.id.Edit_1)
    }
}


