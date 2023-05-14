package com.example.jobwave.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobwave.R
import com.example.jobwave.models.Employer

class EmployerAdapter :RecyclerView.Adapter<EmployerAdapter.EmployerViewHolder>(){

    private var list = mutableListOf<Employer>()
    private  var actionEdit :((Employer)->Unit)? = null
    private var actionDelete:((Employer)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item_employer_view_holder,parent,false)

        return EmployerViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployerViewHolder, position: Int) {
        val user = list[position]
        holder.tvTitle.text = user.title
        holder.tvDescription.text = user.description
        holder.tvSkills.text = user.skills
        holder.tvSalary.text = user.salary

        holder.actionEdit.setOnClickListener {actionEdit?.invoke(user)}
        holder.actionDelete.setOnClickListener{actionDelete?.invoke(user)}
    }

    override fun getItemCount() = list.size

    fun setData(data: Unit){
        list.apply {
            clear()
            //addAll(data)
        }
    }

    fun setOnActionEditListener(callback:(Employer)->Unit){
        this.actionEdit = callback
    }

    fun setOnActionDeleteListener(callback: (Employer) -> Unit){
        this.actionDelete = callback
    }


    class EmployerViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvDescription:TextView = itemView.findViewById(R.id.tv_description)
        val tvSkills:TextView = itemView.findViewById(R.id.tv_skills)
        val tvSalary:TextView = itemView.findViewById(R.id.tv_salary)
        val actionEdit:ImageView = itemView.findViewById(R.id.action_edit)
        val actionDelete:ImageView = itemView.findViewById(R.id.action_delete)
    }

}


