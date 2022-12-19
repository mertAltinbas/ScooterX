package com.example.scooterx.core

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

sealed class Response

data class OnSuccess(val querySnapshot: QuerySnapshot?): Response()
data class OnError(val exception: FirebaseFirestoreException?): Response()

