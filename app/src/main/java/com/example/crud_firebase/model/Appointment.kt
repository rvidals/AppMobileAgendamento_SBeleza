package com.example.crud_firebase.model

import com.google.firebase.firestore.DocumentId

data class Appointment(
    @DocumentId
    val id: String? = null,
    val date: String = "",
    val time: String = "",
    val hairstylist: String = ""
)