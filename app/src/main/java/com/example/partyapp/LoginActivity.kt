package com.example.partyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.partyapp.data.User
import com.example.partyapp.repositories.UserRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var auth :FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient
    private var RC_SIGN_IN = 120
    private var userRepository = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso )
        auth = FirebaseAuth.getInstance()

        val signInButton =  findViewById<SignInButton>(R.id.signInButton)

        signInButton.setOnClickListener{
            signIn()
        }
    }

    private fun signIn(){
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent,RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                Log.d("SignInActivity", "firebaseAuthWithGoogle" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch(e :ApiException) {
                Log.w("SignInActivity", "Google sign-in failed")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    Log.d("LoginACtivity", "signIn with credentials successful")
                    val user = auth.currentUser
                    val name = user?.displayName
                    val newUser = name?.let { User(it, name) }

                    if (newUser != null) {
                        userRepository.insertUser(newUser)
                    }
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else {
                    Log.w("LoginActivity", "sign in failed", task.exception)
                }
            }
    }

}