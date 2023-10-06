package com.example.crud_firebase.ui.adpter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crud_firebase.R
import com.example.crud_firebase.model.Appointment
import com.google.firebase.firestore.FirebaseFirestore

class AppointmentsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val appointmentsList = mutableListOf<Appointment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_appointments, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewAppointments)


        // Configurar o RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = AppointmentsAdapter(appointmentsList)
        recyclerView.adapter = adapter

        // Carregar agendamentos do Firebase
        loadAppointments()

        return view
    }

    private fun loadAppointments() {

        val db = FirebaseFirestore.getInstance()
        val appointmentsCollection = db.collection("appointments")

        appointmentsCollection
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val appointmentData = document.toObject(Appointment::class.java)
                    appointmentsList.add(appointmentData)
                }
                recyclerView.adapter?.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Lidar com erros ao buscar agendamentos do Firebase
                Log.e("Firebase", "Erro ao buscar agendamentos: ${exception.message}", exception)
            }
    }


}