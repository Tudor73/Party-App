package com.example.partyapp

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.partyapp.data.Party
import java.text.SimpleDateFormat
import java.util.*

class CreatePartyActivity : AppCompatActivity() {
    private val dateFormat = SimpleDateFormat("dd-MM-yyyy")
    var date : Date? = Date()
    var partyName : String = ""
    var partyAddress : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_party)
        println("activity loaded")
    }

    fun showDatePicker(view: View) {
        val today = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener{view, year, monthOfYear, dayOfMonth ->
                val dateSelected = Calendar.getInstance()
                dateSelected.set(year, monthOfYear, dayOfMonth)
                val formatedDate : String = this.dateFormat.format(dateSelected.time)
                val dateTextView : TextView = findViewById(R.id.dateTextView)
                dateTextView.text = formatedDate
                this.date = this.dateFormat.parse(formatedDate)
            },
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        )
            datePickerDialog.show()
    }
    fun submitParty(view : View) {

        this.partyName = findViewById<TextView>(R.id.nameInput).text.toString()
        this.partyAddress = findViewById<TextView>(R.id.addressInput).text.toString()

        if(this.partyName == "") {
            Toast.makeText(this,"Enter party name", Toast.LENGTH_SHORT).show()

        }
        else if(this.partyAddress == "") {
            Toast.makeText(this,"Enter party address", Toast.LENGTH_SHORT).show()
        }
        else if(this.date == Date()) {
            Toast.makeText(this,"Select date", Toast.LENGTH_SHORT).show()
        }
        else {
            var newParty = Party(this.partyName)
            print(newParty.id)
            newParty.addInvitation("asdasdas")
        }
    }

     override fun onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


}