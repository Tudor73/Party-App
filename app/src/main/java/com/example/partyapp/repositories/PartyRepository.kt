package com.example.partyapp.repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.example.partyapp.data.Party
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener

class PartyRepository: FirebaseSetup() {
    private val reference = this.database.getReference("parties")

    fun createParty(newParty: Party){
        reference.child(newParty.title).setValue(newParty)
    }

interface PartyListCallback{
    fun onCallback(parties: ArrayList<Party>): ArrayList<Party>
}

}
