package com.example.partyapp.repositories

import com.example.partyapp.data.User

class UserRepository: FirebaseSetup() {

    private val reference = this.database.getReference("users")

    fun insertUser(newUser: User){
        reference.child(newUser.username).setValue(newUser)
    }
}