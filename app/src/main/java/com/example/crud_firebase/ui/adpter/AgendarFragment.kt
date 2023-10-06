package com.example.crud_firebase.ui.adpter

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.crud_firebase.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar


class AgendarFragment : Fragment() {


    private lateinit var dateTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var spinnerProfessional: Spinner

    /*Autenticar usuário*/
    private lateinit var auth: FirebaseAuth

    private val db = FirebaseFirestore.getInstance()
    private val appointmentsCollection = db.collection("appointments")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_agendar, container, false)

        dateTextView = view.findViewById(R.id.textViewDate)
        timeTextView = view.findViewById(R.id.textViewTime)
        spinnerProfessional = view.findViewById(R.id.spinnerProfessional)


        val pickDateButton = view.findViewById<Button>(R.id.buttonPickDate)
        val pickTimeButton = view.findViewById<Button>(R.id.buttonPickTime)
        val saveButton = view.findViewById<Button>(R.id.buttonSave)

        pickDateButton.setOnClickListener { showDatePicker() }
        pickTimeButton.setOnClickListener { showTimePicker() }

        // Defina os valores iniciais para a data e hora (substitua com seus próprios dados)
        dateTextView.text = "Data: Selecione"
        timeTextView.text = "Hora: Selecione"


        // Preencha o Spinner com os nomes dos cabeleireiros
        val hairstylists = arrayOf("Cabeleireiro 1", "Cabeleireiro 2", "Cabeleireiro 3", "Cabeleireiro 4")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, hairstylists)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerProfessional.adapter = adapter

        // Configurar o clique do botão de salvar
        saveButton.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                // O usuário está autenticado, chame a função saveAppointment()
                saveAppointment()

            } else {
                // O usuário não está autenticado, mostre uma mensagem de erro ou solicite o login
                // Você pode redirecionar o usuário para a tela de login ou mostrar uma mensagem de erro aqui
            }
        }


        return view

    }


    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                // Atualize o TextView com a data selecionada
                dateTextView.text = "Data: $selectedYear-${selectedMonth + 1}-$selectedDay"
            },
            year,
            month,
            day
        )


        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
/*            TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                // Atualize o TextView com a hora selecionada
                timeTextView.text = "Hora: $selectedHour:$selectedMinute"
            },*/
            { _, hourOfDay, minute ->
                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                if (isAppointmentTimeValid(selectedTime)) {
                    // O horário é válido, atualize o TextView
                    timeTextView.text = "Hora: $selectedTime"
                } else {
                    // O horário não é válido de acordo com as regras
                    // Mostre uma mensagem de erro ao usuário
                    Toast.makeText(requireContext(), "Horário inválido!", Toast.LENGTH_SHORT).show()
                }
            },
            hour,
            minute,
            true // Formato de 24 horas
        )


        timePickerDialog.show()
    }

    // Função para validar se o horário de agendamento está dentro das regras
    fun isAppointmentTimeValid(selectedTime: String): Boolean {
        try {
            // Converter a string de hora selecionada em um objeto Date
            val dateFormat = SimpleDateFormat("HH:mm")
            val time = dateFormat.parse(selectedTime)

            // Definir as horas de início e término permitidas
            val startTime = dateFormat.parse("08:00")
            val endTime = dateFormat.parse("20:00")

            // Verificar se o horário está entre 8:00 e 20:00 e é de hora em hora
            return time in startTime..endTime && time.minutes == 0
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }



    private fun saveAppointment() {

        val selectedDate = dateTextView.text.toString()
        val selectedTime = timeTextView.text.toString()
        val selectedHairstylist = spinnerProfessional.selectedItem.toString()

        val appointment = hashMapOf(
            "date" to selectedDate,
            "time" to selectedTime,
            "hairstylist" to selectedHairstylist
        )

        appointmentsCollection.add(appointment)
            .addOnSuccessListener { documentReference ->
                // Sucesso ao salvar o agendamento
                println("Agendamento salvo com ID: ${documentReference.id}")

                findNavController().navigate(R.id.action_agendarFragment_to_appointmentsFragment)

            }
            .addOnFailureListener { e ->
                // Erro ao salvar o agendamento
                println("Erro ao salvar o agendamento: $e")
            }
    }


    private fun logoutApp(){

        auth.signOut()
        /*Desloga e vai para a página de Login*/
        findNavController().navigate(R.id.action_homeFragment_to_authentication)

    }


}