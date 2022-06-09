package com.example.partyapp.repositories

import com.google.firebase.database.FirebaseDatabase

open class FirebaseSetup(
    val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://partyapp-646ef-default-rtdb.europe-west1.firebasedatabase.app/")
)