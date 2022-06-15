package com.example.partyapp.repositories

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.partyapp.data.Party
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.ProducerScope
import java.util.concurrent.Flow

class PartyRepository: FirebaseSetup() {
    private val reference = this.database.getReference("parties")
    fun createParty(newParty: Party){
        reference.child(newParty.title).setValue(newParty)
    }
    var parties : MutableLiveData<ArrayList<Party>> = MutableLiveData<ArrayList<Party>>()
    fun getFinalParties():LiveData<ArrayList<Party>>{
        return parties
    }
    fun getParties(){
        var partiesList: ArrayList<Party> = ArrayList()
        reference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                partiesList.clear()
                val partyType = object:GenericTypeIndicator<HashMap<String, Party>>(){}
                val partyMap: HashMap<String, Party>? = snapshot.getValue(partyType)
                if (partyMap != null) {
                    for(party : Party in partyMap.values){
                        Log.w("party",party.toString())
                    }
                }
                if (partyMap != null) {
                    Log.w("party", partyMap.size.toString())
                    for(party : Party in partyMap.values){
                        partiesList.add(party)
                        Log.w("party",party.toString())
                    }
                    Log.w("party", "Alt size " +partiesList.size)
                }
                parties.postValue(partiesList)
            }

            override fun onCancelled(dbError: DatabaseError) {
                Log.w(TAG, "loadParties:onCancelled", dbError.toException())
            }
        })
        Log.w("party","Parties size in repo: " + partiesList.size.toString())
    }


}