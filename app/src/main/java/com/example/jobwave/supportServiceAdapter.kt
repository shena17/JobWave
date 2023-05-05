package com.example.jobwave

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class supportServiceAdapter (private val requestList: ArrayList<SupportServicesModel>) : RecyclerView.Adapter<supportServiceAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.request_list_item, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRequest = requestList[position]
        holder.cusName.text = currentRequest.name_f
    }

    override fun getItemCount(): Int {
        return requestList.size
    }

    class ViewHolder (itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val cusName : TextView = itemView.findViewById(R.id.cusName)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}