package com.example.partyapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.partyapp.data.User
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class ViewPartyActivity : AppCompatActivity() {
    var partyName_from_intent = ""
    var address_from_intent = ""
    var date_from_intent = ""
    var organizer_from_intent = ""
    private lateinit var auth : FirebaseAuth
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
    }
    fun sendEmail(view: View){
        auth = FirebaseAuth.getInstance()
        val name = auth.currentUser?.displayName
        val email = auth.currentUser?.email

        val subject: String = "Party invite!"
        val message: String = "You have been invited to ${partyName_from_intent} by ${name} on ${date_from_intent} at ${address_from_intent}"
        val mIntent = Intent(Intent.ACTION_SEND)
        /*To send an email you need to specify mailto: as URI using setData() method
        and data type will be to text/plain using setType() method*/
        mIntent.data = Uri.parse("mailto:?subject=" + "subject text" + "&body=" + "body text" + "&to=" + "mireaoana555@gmail.com")
        mIntent.type = "text/plain"
        // put recipient email in intent
        /* recipient is put as array because you may wanna send email to multiple emails
           so enter comma(,) separated emails, it will be stored in array*/
        mIntent.putExtra(Intent.EXTRA_EMAIL, email)
        //put the Subject in the intent
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        //put the message in the intent
        mIntent.putExtra(Intent.EXTRA_TEXT, message)


        try {
            //start email intent
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        }
        catch (e: Exception){
            //if any thing goes wrong for example no email client application or any exception
            //get and show exception message
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }
    fun viewOnMap(view : View) {
        Toast.makeText(this,"Viewed on map", "Viewed on map".length).show()
        val gmmIntentUri = Uri.parse("geo:0,0?q=$address_from_intent")

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps")

        // Attempt to start an activity that can handle the Intent
        startActivity(mapIntent)

        mapIntent.resolveActivity(packageManager)?.let {
            startActivity(mapIntent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}