package com.example.jobwave.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.jobwave.R
import com.example.jobwave.models.Advertisement

class ViewAdsAdapter(val ads: List<Advertisement>, private val onDeleteClick: (Advertisement) -> Unit) :
    RecyclerView.Adapter<ViewAdsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewFullName: TextView = view.findViewById(R.id.textViewFullName)
        val textViewEmail: TextView = view.findViewById(R.id.textViewEmail)
        val textViewContactNumber: TextView = view.findViewById(R.id.textViewContactNumber)
        val textViewDescription: TextView = view.findViewById(R.id.textViewDescription)
        val buttonDelete: Button = view.findViewById(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_advertisement, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ad = ads[position]
        holder.textViewFullName.text = ad.fullName
        holder.textViewEmail.text = ad.email
        holder.textViewContactNumber.text = ad.contactNumber
        holder.textViewDescription.text = ad.description

        holder.buttonDelete.setOnClickListener {
            onDeleteClick(ad)
        }
    }

    override fun getItemCount() = ads.size
}
