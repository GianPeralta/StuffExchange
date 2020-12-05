package com.example.stuffexchange

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var registerEmail: EditText
    lateinit var registerPassword: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        auth = FirebaseAuth.getInstance()
        val cancelBtn = findViewById<Button>(R.id.cancelBtn)
        val continueBtn = findViewById<Button>(R.id.continueBtn)
        registerEmail = findViewById<EditText>(R.id.registerEmail)
        registerPassword = findViewById<EditText>(R.id.registerPassword)

        cancelBtn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        continueBtn.setOnClickListener{
            logIn()
        }
    }

    private fun logIn(){
        if(registerEmail.text.toString() == "" || registerPassword.text.toString() == ""){
            Toast.makeText(applicationContext, "Fill out all the fields", Toast.LENGTH_SHORT).show()
        }else if(!Patterns.EMAIL_ADDRESS.matcher(registerEmail.text.toString()).matches()){
            registerEmail.error = "Please enter valid email"
            registerEmail.requestFocus()
        }else if((registerPassword.text.toString().length) < 6){
            Toast.makeText(applicationContext, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
        }else {
            auth.createUserWithEmailAndPassword(
                    registerEmail.text.toString(),
                    registerPassword.text.toString()
            )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(applicationContext, "Registration complete", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(
                                    baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
        }
    }

}