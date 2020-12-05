package com.example.stuffexchange

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var email_text: EditText
    lateinit var password_text: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        email_text = findViewById(R.id.email_text)
        password_text = findViewById(R.id.password_text)


        val logInBtn = findViewById<Button>(R.id.logInBtn)
        logInBtn.setOnClickListener{
            if(email_text.text.toString() == "" || password_text.text.toString() == ""){
                Toast.makeText(applicationContext, "Fill out all the fields", Toast.LENGTH_SHORT).show()
            }else if(!Patterns.EMAIL_ADDRESS.matcher(email_text.text.toString()).matches()){
                email_text.error = "Please enter valid email"
                email_text.requestFocus()
            }else if((password_text.text.toString().length) < 6){
                Toast.makeText(applicationContext, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
            }else {
                auth.signInWithEmailAndPassword(email_text.text.toString(), password_text.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            updateUI(user)
                        } else {
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                            updateUI(null)
                            // ...
                        }

                        // ...
                    }
            }
        }

        val registerLink = findViewById<TextView>(R.id.register)
        registerLink.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
        }else{
            Toast.makeText(applicationContext, "Login failed", Toast.LENGTH_SHORT).show()
        }
    }
}