package com.example.crud_firebase.ui.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crud_firebase.R
import com.example.crud_firebase.model.Appointment

class AppointmentsAdapter(private val appointmentsList: List<Appointment>) :
    RecyclerView.Adapter<AppointmentsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appointment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointment = appointmentsList[position]

        // Atualizar os campos do item com os dados do agendamento
        holder.dateTextView.text = "Data: ${appointment.date}"
        holder.timeTextView.text = "Hora: ${appointment.time}"
        holder.hairstylistTextView.text = "Cabeleireiro: ${appointment.hairstylist}"
    }

    override fun getItemCount(): Int {
        return appointmentsList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        val hairstylistTextView: TextView = itemView.findViewById(R.id.hairstylistTextView)
    }

}
