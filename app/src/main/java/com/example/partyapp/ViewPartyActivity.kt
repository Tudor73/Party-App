package com.example.partyapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ViewPartyActivity : AppCompatActivity() {
    var partyName_from_intent = ""
    var address_from_intent = ""
    var date_from_intent = ""
    var organizer_from_intent = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_party)
        val partyName : TextView = findViewById(R.id.partyName)
        val address : TextView = findViewById(R.id.address)
        val date : TextView = findViewById(R.id.date)
        val organizer : TextView = findViewById(R.id.organizer)
        var received_party_data = getIntent().getStringArrayListExtra("clickedparty")

        partyName_from_intent = received_party_data?.get(0) ?: ""
        address_from_intent = received_party_data?.get(1) ?: ""
        date_from_intent = received_party_data?.get(2) ?: ""
        organizer_from_intent = received_party_data?.get(3) ?: ""

        partyName.setText(partyName_from_intent)
        address.setText(address_from_intent)
        date.setText(date_from_intent)
        organizer.setText(organizer_from_intent)
        Toast.makeText(this,"hello view party", "hello view party".length).show()
    }
    fun viewOnMap(view : View) {
        Toast.makeText(this,"Viewed on map", "Viewed on map".length).show()
    }

     override fun onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}