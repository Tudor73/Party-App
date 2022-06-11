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

    fun getParties(partyCallback: PartyListCallback): ArrayList<Party>{
        var partiesList: ArrayList<Party> = ArrayList()
        reference.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val partyType = object:GenericTypeIndicator<HashMap<String, Party>>(){}
                val partyMap: HashMap<String, Party>? = snapshot.getValue(partyType)

                if (partyMap != null) {
                    partiesList = partyCallback.onCallback(partyMap.values as ArrayList<Party>)
                }
            }


            override fun onCancelled(dbError: DatabaseError) {
                Log.w(TAG, "loadParties:onCancelled", dbError.toException())
            }
        })
        return partiesList
    }

interface PartyListCallback{
    fun onCallback(parties: ArrayList<Party>): ArrayList<Party>
}

}
