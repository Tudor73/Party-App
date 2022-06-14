package com.example.partyapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.partyapp.data.Party
import com.example.partyapp.data.User
import com.example.partyapp.repositories.PartyRepository
import com.example.partyapp.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class CreatePartyActivity : AppCompatActivity() {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy")
    var date : String = ""
    var partyTitle: String = ""
    var partyAddress: String = ""
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_party)

        auth = FirebaseAuth.getInstance()
    }

    fun showDatePicker(view: View) {
        val today = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener{view, year, monthOfYear, dayOfMonth ->
                val dateSelected = Calendar.getInstance()
                dateSelected.set(year, monthOfYear, dayOfMonth)

                val formattedDate : String = this.dateFormat.format(dateSelected.time)
                val dateTextView : TextView = findViewById(R.id.dateTextView)
                dateTextView.text = formattedDate
                date = formattedDate
            },

            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        )
            datePickerDialog.show()
    }
    fun submitParty(view : View) {

        lateinit var newParty: Party

        partyTitle = findViewById<TextView>(R.id.nameInput).text.toString()
        partyAddress = findViewById<TextView>(R.id.addressInput).text.toString()

        if(partyTitle == "") {
            Toast.makeText(this,"Enter party title", Toast.LENGTH_SHORT).show()

        }
        else if(partyAddress == "") {
            Toast.makeText(this,"Enter party address", Toast.LENGTH_SHORT).show()
        }
        else if(this.date == "") {
            Toast.makeText(this,"Select date", Toast.LENGTH_SHORT).show()
        }
        else {
            val name = auth.currentUser?.displayName
            val currentUser = name?.let { User(it, name) }
            if (currentUser != null ) {
                newParty= Party(partyTitle, partyAddress, this.date, currentUser)
                val pr = PartyRepository()
                pr.createParty(newParty)
                Toast.makeText(this, "Party created successfully!", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                Toast.makeText(this, "An error occurred !", Toast.LENGTH_LONG).show()

            }
        }


    }

     override fun onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}