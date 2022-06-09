package com.example.partyapp.repositories

import com.example.partyapp.data.Party

class PartyRepository: FirebaseSetup() {
    private val reference = this.database.getReference("parties")

    fun createParty(newParty: Party){
        reference.child(newParty.title).setValue(newParty)
    }
}